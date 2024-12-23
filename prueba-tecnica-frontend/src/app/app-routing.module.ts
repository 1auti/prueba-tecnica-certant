import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/security/login/login.component';
import { RegisterComponent } from './pages/security/register/register.component';
import { ActivateAccountComponent } from './pages/security/activate-account/activate-account.component';
import { SidebarComponent } from './layout/sidebar/sidebar.component';


// Elimina la ruta /admin
const routes: Routes = [
  // Rutas de seguridad
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'activate-account', component: ActivateAccountComponent },

  // Rutas cargadas de manera diferida
  {
    path: '',
    loadChildren: () =>
      import('./pages/pages.module').then((m) => m.PagesModule),
  },

  // Ruta comodín (404)
  { path: '**', redirectTo: '', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}