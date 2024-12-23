import { Component, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { JwtDecoderService } from './service/jwt-decoder.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'prueba-tecnica-frontend';

  // Propiedad para controlar el estado del sidebar
  isSidebarOpen = false;

  // Método para alternar el estado del sidebar
  toggleSidebar() {
    this.isSidebarOpen = !this.isSidebarOpen;
  }

  roles: string[] | null = null;

  constructor(
    private jwtDecoderService: JwtDecoderService,
    @Inject(PLATFORM_ID) private platformId: any
  ) {}

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      const token = localStorage.getItem('token');
      if (token) {
        this.roles = this.jwtDecoderService.getUserRoles(token);
        console.log('Roles del usuario:', this.roles);
      }
    }
  }
}