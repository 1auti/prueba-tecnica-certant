import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

export interface Field {
  name: string;
  label: string;
  type: string;
  required?: boolean;
  options?: any[];
  disabled?: boolean;
  validations?: {
    pattern?: string;
    min?: number;
    max?: number;
    minLength?: number;
    maxLength?: number;
  };
}

interface InitialData {
  [key: string]: any;  // Cambiar a 'any' para aceptar cualquier tipo de dato, no solo arrays de strings.
}

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']  // Corregí 'styleUrl' a 'styleUrls' que es la propiedad correcta.
})
export class FormComponent implements OnInit {
  @Input() formTitle: string = 'Formulario';
  @Input() fields: Field[] = [];
  @Input() initialData: InitialData = {};
  @Input() isSubmitting = false;

  @Output() formSubmit = new EventEmitter<any>();
  @Output() onCancel = new EventEmitter<void>();

  form: FormGroup;
  vehicles: string[] = [];

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({});
  }

  ngOnInit() {
    this.initForm();
  }

  private initForm() {
    const group: any = {};

    this.fields.forEach(field => {
      const validators = [];
      if (field.required) {
        validators.push(Validators.required);
      }
      if (field.validations?.pattern) {
        validators.push(Validators.pattern(field.validations.pattern));
      }
      if (field.validations?.minLength) {
        validators.push(Validators.minLength(field.validations.minLength));
      }
      if (field.validations?.maxLength) {
        validators.push(Validators.maxLength(field.validations.maxLength));
      }

      group[field.name] = [
        this.initialData[field.name] || '',
        validators
      ];
    });

    // Agregar el campo DNI al formulario
    group['dni'] = ['', Validators.required];
    this.form = this.fb.group(group);
  }

  // Función para buscar cliente por DNI
  searchClientByDni() {
    const dni = this.form.get('dni')?.value;
    if (dni) {
      // Simulando la búsqueda de un cliente
      this.vehicles = this.getVehiclesForClient(dni);
    }
  }

  // Lógica simulada para obtener vehículos (aquí puedes reemplazarlo por una llamada API)
  // Lógica simulada para obtener vehículos (aquí puedes reemplazarlo por una llamada API)
getVehiclesForClient(dni: string): string[] {
  const clientVehicles: { [key: string]: string[] } = {  // Usamos una firma de índice { [key: string]: string[] }
    '12345678': ['Vehículo A', 'Vehículo B'],
    '23456789': ['Vehículo C', 'Vehículo D'],
  };
  return clientVehicles[dni] || [];
}


  // Limpiar vehículos cuando se cambia o borra el DNI
  onDniInput() {
    // Si se borra el DNI, también se limpian los vehículos asociados
    if (!this.form.get('dni')?.value) {
      this.vehicles = [];
    }
  }

  // Enviar el formulario
  onSubmit() {
    if (this.form.valid) {
      this.formSubmit.emit(this.form.value);
    }
  }
}
