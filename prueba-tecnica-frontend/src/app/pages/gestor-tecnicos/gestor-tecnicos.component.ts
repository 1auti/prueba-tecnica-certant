import { Component, OnInit, ViewChild } from '@angular/core';
import { Tecnico } from '../../models/class/tecnico';
import { TecnicoDTO } from '../../models/dto/tecnicoDto';
import { CrudContainerComponent } from '../../components/crud-container/crud-container.component';
import { Field } from '../../components/form/form.component';
import { Especialidad } from '../../models/enums/especialidad';
import { TecnicoService } from '../../service/tecnico.service';

@Component({
  selector: 'app-gestor-tecnicos',
  templateUrl: './gestor-tecnicos.component.html',
  styleUrl: './gestor-tecnicos.component.css'
})
export class GestorTecnicosComponent implements OnInit {

  @ViewChild(CrudContainerComponent) crudContainer!: CrudContainerComponent;

  tecnicoFields: Field[] = [
    {
      name: 'nombre',
      label: 'Nombre',
      type: 'text',
      required: true,
      validations: {
        minLength: 2,
        maxLength: 50
      }
    },
    {
      name: 'apellido',
      label: 'Apellido',
      type: 'text',
      required: true,
      validations: {
        minLength: 2,
        maxLength: 50
      }
    },
    {
      name: 'email',
      label: 'Correo Electrónico',
      type: 'email',
      required: true,
      validations: {
        pattern: '^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$'
      }
    },
    {
      name: 'cardId',
      label: 'Número de Tarjeta',
      type: 'number',
      required: true
    },
    {
      name: 'especialidad',
      label: 'Especialidad',
      type: 'select',
      required: true,
      options: Object.values(Especialidad)
    }
  ];

  tecnicos: Tecnico[] = [];
  isSubmitting = false;
  cardIdBusqueda: string = '';

  constructor(private tecnicoService: TecnicoService) {}

  ngOnInit() {
    this.cargarTecnicos();
  }

  cargarTecnicos() {
    this.tecnicoService.listarTodos().subscribe(
      (tecnicos) => {
        this.tecnicos = tecnicos;
      },
      error => console.error('Error al cargar técnicos:', error)
    );
  }

  cargarTecnicosActivos() {
    this.tecnicoService.listarActivos().subscribe(
      (tecnicos) => {
        this.tecnicos = tecnicos;
      },
      error => console.error('Error al cargar técnicos activos:', error)
    );
  }

  buscarPorCardId() {
    if (this.cardIdBusqueda) {
      this.tecnicoService.obtenerPorCardId(parseInt(this.cardIdBusqueda)).subscribe(
        (tecnico) => {
          this.tecnicos = tecnico ? [tecnico] : [];
        },
        error => console.error('Error al buscar técnico:', error)
      );
    }
  }

  mostrarFormularioNuevo() {
    if (this.crudContainer) {
      this.crudContainer.showForm = true;
      this.crudContainer.selectedItem = null;
    }
  }

  crearTecnico(data: any) {
    this.isSubmitting = true;
    const tecnicoDTO: TecnicoDTO = this.convertToDTO(data);
    
    this.tecnicoService.crear(tecnicoDTO).subscribe(
      () => {
        this.isSubmitting = false;
        this.cargarTecnicos();
        if (this.crudContainer) {
          this.crudContainer.showForm = false;
        }
      },
      error => {
        this.isSubmitting = false;
        console.error('Error al crear técnico:', error);
      }
    );
  }

  actualizarTecnico(data: any) {
    this.isSubmitting = true;
    const tecnicoDTO: TecnicoDTO = this.convertToDTO(data);
    
    this.tecnicoService.actualizar(data.id, tecnicoDTO).subscribe(
      () => {
        this.isSubmitting = false;
        this.cargarTecnicos();
        if (this.crudContainer) {
          this.crudContainer.showForm = false;
        }
      },
      error => {
        this.isSubmitting = false;
        console.error('Error al actualizar técnico:', error);
      }
    );
  }

  eliminarTecnico(tecnico: Tecnico) {
    if (confirm('¿Está seguro de eliminar este técnico?')) {
      this.tecnicoService.eliminar(tecnico.id!).subscribe(
        () => this.cargarTecnicos(),
        error => console.error('Error al eliminar técnico:', error)
      );
    }
  }

  private convertToDTO(data: any): TecnicoDTO {
    const dto: TecnicoDTO = {
      nombre: data.nombre,
      apellido: data.apellido,
      email: data.email,
      cardId: data.cardId,
      especialidad: data.especialidad,
      dni: data.dni || '', // Proporciona un valor predeterminado si falta
      telefono: data.telefono || '', // Valor predeterminado
      salario: data.salario || 0, // Valor predeterminado para salario
      jornadaLaboral: data.jornadaLaboral || {} // Valor predeterminado o estructura vacía
    };
    return dto;
  }
  
}
