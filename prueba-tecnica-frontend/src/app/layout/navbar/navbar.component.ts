import { Component, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { JwtDecoderService } from '../../service/jwt-decoder.service';
import { SidebarService } from '../../service/sidebar.service';
import { UserService } from '../../service/user.service';
import { User } from '../../models/interface/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent {
  isAdmin: boolean = false;
  isLoggedIn: boolean = false;
  user: User | null = null;

  
constructor(
  private sidebarService: SidebarService,
  private userService: UserService,
  private jwtDecoderService: JwtDecoderService,
  private router: Router,
  @Inject(PLATFORM_ID) private platformId: any
) {}

ngOnInit(): void {
  if (isPlatformBrowser(this.platformId)) {
    const token = localStorage.getItem('token');
    if (token) {
      const roles = this.jwtDecoderService.getUserRoles(token);
      this.isAdmin = Array.isArray(roles) && roles.includes('ADMIN');
    }

    // Suscribirse a los cambios en el usuario
    this.userService.user$.subscribe((user) => {
      this.user = user;
      this.isLoggedIn = !!user;

      // Mostrar/ocultar sidebar basado en el rol
      if (this.isAdmin) {
        this.sidebarService.showSidebar();
      } else {
        this.sidebarService.hideSidebar();
      }
    });
  }
}


  toggleSidebar() {
    this.sidebarService.toggleSidebar();
  }

  logout(): void {
    this.userService.logout();
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem('token');
    }
    this.user = null;
    this.isLoggedIn = false;
  
    // Navegar en lugar de recargar
    this.userService.logout();
this.sidebarService.hideSidebar();
    this.router.navigate(['/login']);
  }
  
  
  openAdminPanel(): void {
    this.sidebarService.showSidebar();
  }
}