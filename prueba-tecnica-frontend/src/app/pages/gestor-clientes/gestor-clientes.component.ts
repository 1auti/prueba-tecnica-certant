import { Component, OnInit, ViewChild } from '@angular/core';
import { ClienteDTO } from '../../models/dto/clienteDto';
import { Cliente } from '../../models/class/cliente';
import { ClienteService } from '../../service/cliente.service';
import { Field } from '../../components/form/form.component';
import { CrudContainerComponent } from '../../components/crud-container/crud-container.component';

@Component({
  selector: 'app-gestor-clientes',
  templateUrl: './gestor-clientes.component.html',
  styleUrl: './gestor-clientes.component.css'
})
export class GestorClientesComponent implements OnInit {

  @ViewChild(CrudContainerComponent) crudContainer!: CrudContainerComponent;

  clienteFields: Field[] = [
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
      name: 'telefono',
      label: 'Teléfono',
      type: 'tel',
      required: true,
      validations: {
        pattern: '^[0-9]{10}$'
      }
    },
    {
      name: 'dni',
      label: 'DNI',
      type: 'text',
      required: true,
      validations: {
        pattern: '^[0-9]{8}$'
      }
    }
  ];

  clientes: Cliente[] = [];
  isSubmitting = false;
  dniBusqueda: string = '';

  constructor(private clienteService: ClienteService) {}

  ngOnInit() {
    this.cargarClientes();
  }

  cargarClientes() {
    this.clienteService.listarClientes().subscribe(
      (clientes) => {
        this.clientes = clientes;
      },
      error => console.error('Error al cargar clientes:', error)
    );
  }

  cargarClientesPremium() {
    this.clienteService.obtenerClientesPremium().subscribe(
      (clientes) => {
        this.clientes = clientes;
      },
      error => console.error('Error al cargar clientes premium:', error)
    );
  }

  buscarPorDNI() {
    if (this.dniBusqueda) {
      this.clienteService.buscarPorDNI(this.dniBusqueda).subscribe(
        (cliente) => {
          this.clientes = cliente ? [cliente] : [];
        },
        error => console.error('Error al buscar cliente:', error)
      );
    }
  }

  mostrarFormularioNuevo() {
    if (this.crudContainer) {
      this.crudContainer.showForm = true;
      this.crudContainer.selectedItem = null;
    }
  }

  crearCliente(data: any) {
    this.isSubmitting = true;
    // Convertir a ClienteDTO si es necesario
    const clienteDTO: ClienteDTO = this.convertToDTO(data);
    
    this.clienteService.guardarCliente(clienteDTO).subscribe(
      () => {
        this.isSubmitting = false;
        this.cargarClientes();
      },
      error => {
        this.isSubmitting = false;
        console.error('Error al crear cliente:', error);
      }
    );
  }

  actualizarCliente(data: any) {
    this.isSubmitting = true;
    const clienteDTO: ClienteDTO = this.convertToDTO(data);
    
    this.clienteService.actualizarCliente(data.id, clienteDTO).subscribe(
      () => {
        this.isSubmitting = false;
        this.cargarClientes();
      },
      error => {
        this.isSubmitting = false;
        console.error('Error al actualizar cliente:', error);
      }
    );
  }

  eliminarCliente(cliente: Cliente) {
    if (confirm('¿Está seguro de eliminar este cliente?')) {
      this.clienteService.eliminarCliente(cliente.id!).subscribe(
        () => this.cargarClientes(),
        error => console.error('Error al eliminar cliente:', error)
      );
    }
  }

  incrementarContadorServicios(clienteId: number) {
    this.clienteService.incrementarContadorServicios(clienteId).subscribe(
      () => {
        this.cargarClientes();
      },
      error => console.error('Error al incrementar contador:', error)
    );
  }

  private convertToDTO(data: any): ClienteDTO {
    // Implementa la conversión según la estructura de tu ClienteDTO
    const dto: ClienteDTO = {
      nombre: data.nombre,
      apellido: data.apellido,
      email: data.email,
      telefono: data.telefono,
      dni: data.dni
      // Agrega otros campos según tu DTO
    };
    return dto;
  }

}
