import { Component, ViewChild } from '@angular/core';
import { ServicioDTO } from '../../models/dto/servicioDto';
import { Servicio } from '../../models/class/servicio';
import { ServicioService } from '../../service/servicio.service';
import { TipoServicio } from '../../models/enums/tipoServicio';
import { CrudContainerComponent } from '../../components/crud-container/crud-container.component';
import { Field } from '../../components/form/form.component';

@Component({
  selector: 'app-gestor-servicio',
  templateUrl: './gestor-servicio.component.html',
  styleUrl: './gestor-servicio.component.css'
})
export class GestorServicioComponent {
  @ViewChild(CrudContainerComponent) crudContainer!: CrudContainerComponent;

  servicioFields: Field[] = [
    {
      name: 'nombre',
      label: 'Nombre del Servicio',
      type: 'text',
      required: true,
      validations: {
        minLength: 3,
        maxLength: 100
      }
    },
    {
      name: 'descripcion',
      label: 'Descripción',
      type: 'textarea',
      required: true,
      validations: {
        minLength: 10,
        maxLength: 500
      }
    },
    {
      name: 'tipo',
      label: 'Tipo de Servicio',
      type: 'select',
      required: true,
      options: Object.values(TipoServicio)
    },
    {
      name: 'precio',
      label: 'Precio',
      type: 'number',
      required: true,
      validations: {
        min: 0
      }
    },
    {
      name: 'duracionEstimada',
      label: 'Duración Estimada (minutos)',
      type: 'number',
      required: true,
      validations: {
        min: 15,
        max: 480
      }
    }
  ];

  servicios: Servicio[] = [];
  isSubmitting = false;
  tipoServicioFiltro: TipoServicio | null = null;

  constructor(private servicioService: ServicioService) {}

  ngOnInit() {
    this.cargarServicios();
  }

  cargarServicios() {
    this.servicioService.listarTodos().subscribe(
      (servicios) => {
        this.servicios = servicios;
      },
      error => console.error('Error al cargar servicios:', error)
    );
  }

  cargarServiciosActivos() {
    this.servicioService.listarActivos().subscribe(
      (servicios) => {
        this.servicios = servicios;
      },
      error => console.error('Error al cargar servicios activos:', error)
    );
  }

  filtrarPorTipo(tipo: TipoServicio) {
    this.servicioService.listarPorTipo(tipo).subscribe(
      (servicios) => {
        this.servicios = servicios;
      },
      error => console.error('Error al filtrar servicios:', error)
    );
  }

  mostrarFormularioNuevo() {
    if (this.crudContainer) {
      this.crudContainer.showForm = true;
      this.crudContainer.selectedItem = null;
    }
  }

  crearServicio(data: any) {
    this.isSubmitting = true;
    const servicioDTO: ServicioDTO = this.convertToDTO(data);
    
    this.servicioService.crear(servicioDTO).subscribe(
      () => {
        this.isSubmitting = false;
        this.cargarServicios();
        if (this.crudContainer) {
          this.crudContainer.showForm = false;
        }
      },
      error => {
        this.isSubmitting = false;
        console.error('Error al crear servicio:', error);
      }
    );
  }

  actualizarServicio(data: any) {
    this.isSubmitting = true;
    const servicioDTO: ServicioDTO = this.convertToDTO(data);
    
    this.servicioService.actualizar(data.id, servicioDTO).subscribe(
      () => {
        this.isSubmitting = false;
        this.cargarServicios();
        if (this.crudContainer) {
          this.crudContainer.showForm = false;
        }
      },
      error => {
        this.isSubmitting = false;
        console.error('Error al actualizar servicio:', error);
      }
    );
  }

  eliminarServicio(servicio: Servicio) {
    if (confirm('¿Está seguro de eliminar este servicio?')) {
      this.servicioService.eliminar(servicio.id!).subscribe(
        () => this.cargarServicios(),
        error => console.error('Error al eliminar servicio:', error)
      );
    }
  }

  activarServicio(servicio: Servicio) {
    this.servicioService.activar(servicio.id!).subscribe(
      () => this.cargarServicios(),
      error => console.error('Error al activar servicio:', error)
    );
  }

  desactivarServicio(servicio: Servicio) {
    this.servicioService.desactivar(servicio.id!).subscribe(
      () => this.cargarServicios(),
      error => console.error('Error al desactivar servicio:', error)
    );
  }

  private convertToDTO(data: any): ServicioDTO {
    const dto: ServicioDTO = {
      nombre: data.nombre,
      descripcion: data.descripcion,
      tipoServicio: data.tipoServicio,
      estaActivo: data.estaActivo
    };
    return dto;
  }
  
  
}
