import { Component, OnInit, ViewChild } from '@angular/core';
import { RepuestoService } from '../../service/repuesto.service';
import { Field } from '../../components/form/form.component';
import { CrudContainerComponent } from '../../components/crud-container/crud-container.component';

@Component({
  selector: 'app-gestor-repuesto',
  templateUrl: './gestor-repuesto.component.html',
  styleUrl: './gestor-repuesto.component.css'
})
export class GestorRepuestoComponent implements OnInit {
  @ViewChild(CrudContainerComponent) crudContainer!: CrudContainerComponent;

  // Propiedades faltantes
  repuestos: any[] = [];
  isSubmitting = false;

  repuestoFields: Field[] = [
    {
      name: 'codigo',
      label: 'Código',
      type: 'text',
      required: true,
      validations: { pattern: '^[A-Z0-9]{4,10}$' }
    },
    {
      name: 'nombre',
      label: 'Nombre',
      type: 'text',
      required: true,
      validations: { minLength: 3, maxLength: 100 }
    },
    {
      name: 'descripcion',
      label: 'Descripción',
      type: 'textarea',
      required: true,
      validations: { minLength: 10, maxLength: 500 }
    },
    {
      name: 'stock',
      label: 'Stock',
      type: 'number',
      required: true,
      validations: { min: 0 }
    },
    {
      name: 'precio',
      label: 'Precio',
      type: 'number',
      required: true,
      validations: { min: 0 }
    }
  ];

  constructor(private repuestoService: RepuestoService) {}

  ngOnInit() {
    // Aquí podrías cargar la lista inicial de repuestos si tuvieras un método en el servicio
  }

  // Métodos faltantes
  mostrarFormularioNuevo() {
    if (this.crudContainer) {
      this.crudContainer.showForm = true;
      this.crudContainer.selectedItem = null;
    }
  }

  crearRepuesto(data: any) {
    this.isSubmitting = true;
    // Aquí iría la lógica para crear un repuesto usando el servicio
    // Por ahora solo simulamos la finalización
    setTimeout(() => {
      this.isSubmitting = false;
      // Aquí podrías recargar la lista de repuestos
    }, 1000);
  }

  actualizarRepuesto(data: any) {
    this.isSubmitting = true;
    // Aquí iría la lógica para actualizar un repuesto usando el servicio
    setTimeout(() => {
      this.isSubmitting = false;
      // Aquí podrías recargar la lista de repuestos
    }, 1000);
  }

  eliminarRepuesto(repuesto: any) {
    if (confirm('¿Está seguro de eliminar este repuesto?')) {
      // Aquí iría la lógica para eliminar un repuesto usando el servicio
    }
  }

  alertaStockBajo() {
    this.repuestoService.alertaStockBajo().subscribe(
      () => {
        alert('Alerta de stock bajo enviada correctamente');
      },
      error => console.error('Error al enviar alerta de stock bajo:', error)
    );
  }
}