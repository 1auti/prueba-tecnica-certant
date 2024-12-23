import { Component, OnInit, ViewChild } from '@angular/core';
import { VehiculoDTO } from '../../models/dto/vehiculoDto';
import { Vehiculo } from '../../models/class/vehiculo';
import { VehiculoService } from '../../service/vehiculo.service';
import { CrudContainerComponent } from '../../components/crud-container/crud-container.component';
import { Field } from '../../components/form/form.component';

@Component({
  selector: 'app-gestor-vehiculos',
  templateUrl: './gestor-vehiculos.component.html',
  styleUrl: './gestor-vehiculos.component.css'
})
export class GestorVehiculosComponent implements OnInit {

  @ViewChild(CrudContainerComponent) crudContainer!: CrudContainerComponent;
  clienteId: number = 0;

  vehiculoFields: Field[] = [
    {
      name: 'patente',
      label: 'Patente',
      type: 'text',
      required: true,
      validations: { pattern: '^[A-Z0-9]{6,7}$' }
    },
    {
      name: 'marca',
      label: 'Marca',
      type: 'text',
      required: true,
      validations: { minLength: 2, maxLength: 50 }
    },
    {
      name: 'modelo',
      label: 'Modelo',
      type: 'text',
      required: true,
      validations: { minLength: 2, maxLength: 50 }
    },
    {
      name: 'anio',
      label: 'Año',
      type: 'number',
      required: true,
      validations: { min: 1900, max: new Date().getFullYear() }
    },
    {
      name: 'clienteId',
      label: 'ID Cliente',
      type: 'number',
      required: true
    }
  ];

  vehiculos: Vehiculo[] = [];
  isSubmitting = false;
  patenteSearch: string = '';

  constructor(private vehiculoService: VehiculoService) {}

  ngOnInit() {
    this.cargarVehiculos();
  }

  cargarVehiculos() {
    this.vehiculoService.listarTodos().subscribe(
      (vehiculos) => {
        this.vehiculos = vehiculos;
      },
      error => console.error('Error al cargar vehículos:', error)
    );
  }

  cargarVehiculosPorCliente(clienteId: number) {
    if (clienteId) {
      this.vehiculoService.listarPorCliente(clienteId).subscribe(
        (vehiculos) => {
          this.vehiculos = vehiculos;
        },
        error => console.error('Error al cargar vehículos del cliente:', error)
      );
    }
  }

  buscarPorPatente() {
    if (this.patenteSearch) {
      this.vehiculoService.obtenerPorPatente(this.patenteSearch).subscribe(
        (vehiculo) => {
          this.vehiculos = vehiculo ? [vehiculo] : [];
        },
        error => console.error('Error al buscar vehículo:', error)
      );
    }
  }

  mostrarFormularioNuevo() {
    if (this.crudContainer) {
      this.crudContainer.showForm = true;
      this.crudContainer.selectedItem = null;
    }
  }

  crearVehiculo(data: any) {
    this.isSubmitting = true;
    const vehiculoDTO: VehiculoDTO = this.convertToDTO(data);
    this.vehiculoService.crear(vehiculoDTO).subscribe(
      () => {
        this.isSubmitting = false;
        this.cargarVehiculos();
      },
      error => {
        this.isSubmitting = false;
        console.error('Error al crear vehículo:', error);
      }
    );
  }

  actualizarVehiculo(data: any) {
    this.isSubmitting = true;
    const vehiculoDTO: VehiculoDTO = this.convertToDTO(data);
    this.vehiculoService.actualizar(data.id, vehiculoDTO).subscribe(
      () => {
        this.isSubmitting = false;
        this.cargarVehiculos();
      },
      error => {
        this.isSubmitting = false;
        console.error('Error al actualizar vehículo:', error);
      }
    );
  }

  eliminarVehiculo(vehiculo: Vehiculo) {
    if (confirm('¿Está seguro de eliminar este vehículo?')) {
      this.vehiculoService.eliminar(vehiculo.id!).subscribe(
        () => this.cargarVehiculos(),
        error => console.error('Error al eliminar vehículo:', error)
      );
    }
  }

  cambiarCliente(vehiculoId: number, nuevoClienteId: number) {
    this.vehiculoService.cambiarCliente(vehiculoId, nuevoClienteId).subscribe(
      () => {
        this.cargarVehiculos();
      },
      error => console.error('Error al cambiar cliente:', error)
    );
  }

  private convertToDTO(data: any): VehiculoDTO {
    const dto: VehiculoDTO = {
      patente: data.patente,
      marca: data.marca,
      modelo: data.modelo,
      anio: data.anio,
      clienteId: data.clienteId,
      color: data.color,
      tiposVehiculo: data.tiposVehiculo
    };
    return dto;
  }
  
}
