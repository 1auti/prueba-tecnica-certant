import { Component, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { JwtDecoderService } from '../../service/jwt-decoder.service';
import { SidebarService } from '../../service/sidebar.service';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css'],
})
export class SidebarComponent {
  isSidebarOpen = false;
  isAdmin = false;

  constructor(
    private sidebarService: SidebarService,
    private jwtDecoderService: JwtDecoderService,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: any
  ) {}

  ngOnInit(): void {
    // Suscribirse al estado del sidebar
    this.sidebarService.sidebarOpen$.subscribe({
      next: (isOpen) => {
        this.isSidebarOpen = isOpen;
      },
      error: (error) => {
        console.error('Error al suscribirse al observable sidebarOpen$: ', error);
      },
    });

    if (isPlatformBrowser(this.platformId)) {
      // Manejar el token inicial
      this.handleTokenChange(localStorage.getItem('token'));

      // Listener para cambios en el localStorage
      window.addEventListener('storage', this.onStorageChange.bind(this));
    }

    // Cerrar el sidebar si no hay token al navegar
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        const token = localStorage.getItem('token');
        if (!token) {
          this.isSidebarOpen = false;
        }
      }
    });
  }

  ngOnDestroy(): void {
    // Eliminar el listener del evento storage
    if (isPlatformBrowser(this.platformId)) {
      window.removeEventListener('storage', this.onStorageChange.bind(this));
    }
  }

  private onStorageChange(event: StorageEvent): void {
    if (event.key === 'token') {
      this.handleTokenChange(event.newValue);
    }
  }

  private handleTokenChange(token: string | null): void {
    if (token) {
      try {
        const roles = this.jwtDecoderService.getUserRoles(token);
        this.isAdmin = (roles ?? []).includes('ADMIN');
        this.isSidebarOpen = this.isAdmin;
      } catch (error) {
        console.error('Error al decodificar el token: ', error);
        this.isAdmin = false;
        this.isSidebarOpen = false;
      }
    } else {
      this.isAdmin = false;
      this.isSidebarOpen = false;
    }
  }

  toggleSidebar(): void {
    this.isSidebarOpen = !this.isSidebarOpen;
  }
}
