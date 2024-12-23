import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PagesRoutingModule } from './pages-routing.module';
import { GestorClientesComponent } from './gestor-clientes/gestor-clientes.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ComponentsModule } from '../components/components.module';
import { HttpClientModule } from '@angular/common/http';
import { GestorVehiculosComponent } from './gestor-vehiculos/gestor-vehiculos.component';

import { GestorCitasComponent } from './gestor-citas/gestor-citas.component';
import { GestorTecnicosComponent } from './gestor-tecnicos/gestor-tecnicos.component';
import { GestorServicioComponent } from './gestor-servicio/gestor-servicio.component';
import { GestorRepuestoComponent } from './gestor-repuesto/gestor-repuesto.component';
import { LoginComponent } from './security/login/login.component';
import { RegisterComponent } from './security/register/register.component';
import { ActivateAccountComponent } from './security/activate-account/activate-account.component';
import { CodeInputModule } from 'angular-code-input';


@NgModule({
  declarations: [
    GestorClientesComponent,
    GestorVehiculosComponent,
    GestorCitasComponent,
    GestorTecnicosComponent,
    GestorServicioComponent,
    GestorRepuestoComponent,
    LoginComponent,
    RegisterComponent,
    ActivateAccountComponent
  ],
  imports: [
    CommonModule,
    PagesRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    ComponentsModule,
    HttpClientModule,
    CodeInputModule
  ],
  exports: [GestorClientesComponent,

  ]
})
export class PagesModule { }
