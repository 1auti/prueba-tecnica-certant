import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormComponent } from './form/form.component';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DataTableComponent } from './data-table/data-table.component';
import { CrudContainerComponent } from './crud-container/crud-container.component';



@NgModule({
  declarations: [
    FormComponent,
    DataTableComponent,
    CrudContainerComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports:[
    FormComponent,
    CrudContainerComponent
  ]
})
export class ComponentsModule { }
