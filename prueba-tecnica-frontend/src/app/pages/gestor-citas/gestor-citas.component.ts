import { Component, OnInit, ViewChild } from '@angular/core';
import { CitaDTO } from '../../models/dto/citaDto';
import { Cita } from '../../models/class/cita';
import { Field } from '../../components/form/form.component';
import { CrudContainerComponent } from '../../components/crud-container/crud-container.component';
import { CitasService } from '../../service/citas.service';
import { HistorialServicio } from '../../models/class/historialServicio';
import { HistorialServicioDTO } from '../../models/dto/historialServicioDto';
import { HistorialServicioService } from '../../service/historial-servicio.service';
import { ClienteService } from '../../service/cliente.service';
import { VehiculoService } from '../../service/vehiculo.service';

@Component({
  selector: 'app-gestor-citas',
  templateUrl: './gestor-citas.component.html',
  styleUrls: ['./gestor-citas.component.css']
})
export class GestorCitasComponent implements OnInit {
  @ViewChild(CrudContainerComponent) crudContainer!: CrudContainerComponent;
  dni: string = "";
  cliente: any = null;
  vehiculos: any[] = [];

  citaFields: Field[] = [
    {
      name: 'fecha',
      label: 'Fecha y Hora',
      type: 'datetime-local',
      required: true
    },
    {
      name: 'clienteId',
      label: 'ID Cliente',
      type: 'number',
      required: true
    },
    {
      name: 'vehiculoId',
      label: 'ID Vehículo',
      type: 'number',
      required: true
    },
    {
      name: 'descripcion',
      label: 'Descripción',
      type: 'textarea',
      required: true,
      validations: { minLength: 10, maxLength: 500 }
    }
  ];

  citas: Cita[] = [];
  isSubmitting = false;
  clienteId: number = 0;
  fechaSeleccionada: Date = new Date();
  showFormTerminarCita = false;
  fieldsTerminarCita: Field[] = [
    {
      name: 'observaciones',
      label: 'Observaciones',
      type: 'textarea',
      required: true,
      validations: { minLength: 10, maxLength: 500 }
    },
    {
      name: 'detallesTrabajo',
      label: 'Detalles de trabajo',
      type: 'textarea',
      required: true,
      validations: { minLength: 10, maxLength: 500 }
    }
  ];
  initialDataTerminarCita: any = {};
  isSubmittingTerminarCita = false;

  constructor(
    private citasService: CitasService,
    private historialServicioService: HistorialServicioService,
    private clienteService: ClienteService,
    private vehiculoService: VehiculoService
  ) {}

  ngOnInit() {
    this.cargarCitasFuturas();
  }

  mostrarFormularioTerminarCita() {
    this.showFormTerminarCita = true;
  }

  onSubmitTerminarCita(data: any) {
    this.isSubmittingTerminarCita = true;
    const cita = this.crudContainer.selectedItem;
    const historialServicioDTO: HistorialServicioDTO = {
      citaId: cita.id,
      tecnicoId: 1,
      clienteId: cita.clienteId,
      completadoALas: new Date(),
      observaciones: data.observaciones,
      detallesTrabajo: data.detallesTrabajo,
      controlCalidadChequiado: false
    };

    this.historialServicioService.crear(historialServicioDTO).subscribe(
      () => {
        this.isSubmittingTerminarCita = false;
        this.showFormTerminarCita = false;
        console.log('Cita terminada con éxito');
      },
      (error) => {
        this.isSubmittingTerminarCita = false;
        console.error('Error al terminar cita:', error);
      }
    );
  }

  onCancelTerminarCita() {
    this.showFormTerminarCita = false;
  }

  cargarCitasFuturas() {
    this.citasService.obtenerCitasFuturas().subscribe(
      (citas) => {
        this.citas = citas;
      },
      error => console.error('Error al cargar citas futuras:', error)
    );
  }

  cargarCitasPorCliente() {
    if (this.clienteId) {
      this.citasService.obtenerCitasPorCliente(this.clienteId).subscribe(
        (citas) => {
          this.citas = citas;
        },
        error => console.error('Error al cargar citas del cliente:', error)
      );
    }
  }

  terminarCita(item: any) {
    this.mostrarFormularioTerminarCita();
    this.crudContainer.selectedItem = item;
  }

  verificarDisponibilidad() {
    if (this.fechaSeleccionada) {
      this.citasService.verificarDisponibilidad(this.fechaSeleccionada).subscribe(
        (disponible) => {
          if (disponible) {
            alert('Horario disponible');
          } else {
            alert('Horario no disponible');
          }
        },
        error => console.error('Error al verificar disponibilidad:', error)
      );
    }
  }

  mostrarFormularioNuevo() {
    if (this.crudContainer) {
      this.crudContainer.showForm = true;
      this.crudContainer.selectedItem = null;
    }
  }

  crearCita(data: any) {
    this.isSubmitting = true;
    const citaDTO: CitaDTO = this.convertToDTO(data);
    
    this.citasService.crearCita(citaDTO).subscribe(
      () => {
        this.isSubmitting = false;
        this.cargarCitasFuturas();
      },
      error => {
        this.isSubmitting = false;
        console.error('Error al crear cita:', error);
      }
    );
  }

  cancelarCita(cita: Cita) {
    if (confirm('¿Está seguro de cancelar esta cita?')) {
      this.citasService.cancelarCita(cita.id!).subscribe(
        () => this.cargarCitasFuturas(),
        error => console.error('Error al cancelar cita:', error)
      );
    }
  }

  private convertToDTO(data: any): CitaDTO {
    const dto: CitaDTO = {
      fecha: new Date(data.fecha),
      clienteId: data.clienteId,
      vehiculoId: data.vehiculoId ? data.vehiculoId : null,
      servicioId: data.servicioId,
      notas: data.notas ?? '' // Asigna una cadena vacía si `notas` es opcional y no está presente
    };
    return dto;
  }
  
  buscarClientePorDNI() {
    if (this.dni) {
      this.clienteService.buscarPorDNI(this.dni).subscribe(
        (cliente) => {
          this.cliente = cliente;
          this.clienteId = cliente.id;
          if (this.cliente) {
            this.cargarVehiculos();
          }
        },
        (error) => {
          console.error('Error al buscar cliente por DNI:', error);
        }
      );
    }
  }

  cargarVehiculos() {
    if (this.clienteId) {
      this.vehiculoService.listarPorCliente(this.clienteId).subscribe(
        (vehiculos) => {
          this.vehiculos = vehiculos;
        },
        (error) => {
          console.error('Error al cargar vehículos:', error);
        }
      );
    }
  }
}
