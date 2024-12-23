import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GestorClientesComponent } from './gestor-clientes/gestor-clientes.component';
import { GestorVehiculosComponent } from './gestor-vehiculos/gestor-vehiculos.component';
import { GestorCitasComponent } from './gestor-citas/gestor-citas.component';
import { GestorTecnicosComponent } from './gestor-tecnicos/gestor-tecnicos.component';
import { GestorServicioComponent } from './gestor-servicio/gestor-servicio.component';
import { GestorRepuestoComponent } from './gestor-repuesto/gestor-repuesto.component';
import { HomeComponent } from '../layout/home/home.component';

const routes: Routes = [
  { path: 'gestor-clientes', component: GestorClientesComponent },
  { path: 'gestor-vehiculos', component: GestorVehiculosComponent },
  { path: 'gestor-citas', component: GestorCitasComponent },
  { path: 'gestor-tecnicos', component: GestorTecnicosComponent },
  { path: 'gestor-servicio', component: GestorServicioComponent },
  { path: 'gestor-repuesto', component: GestorRepuestoComponent },
  { path: 'home', component: HomeComponent, pathMatch: 'full' },
  { path: '', component: HomeComponent, pathMatch: 'full' },
  
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PagesRoutingModule {}
