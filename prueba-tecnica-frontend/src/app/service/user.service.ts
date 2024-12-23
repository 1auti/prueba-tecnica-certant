import { BehaviorSubject } from 'rxjs';
import { User } from '../models/interface/user';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private userSubject = new BehaviorSubject<User | null>(null);
  user$ = this.userSubject.asObservable();

  private user: User | null = null;

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {
    if (isPlatformBrowser(this.platformId)) {
      this.loadUserFromStorage();
    }
  }

  private loadUserFromStorage(): void {
    const userStr = localStorage.getItem('user');
    if (userStr) {
      try {
        this.user = JSON.parse(userStr) as User;
        this.userSubject.next(this.user); // Notificar el cambio
      } catch (e) {
        console.error('Error parsing user from localStorage:', e);
        this.user = null;
      }
    }
  }

  getUser(): User | null {
    return this.user;
  }

  setUser(user: User): void {
    if (isPlatformBrowser(this.platformId)) {
      this.user = user;
      localStorage.setItem('user', JSON.stringify(user));
      this.userSubject.next(user); // Emitir el nuevo estado
    }
  }

  logout(): void {
    if (isPlatformBrowser(this.platformId)) {
      this.user = null;
      localStorage.removeItem('user');
      localStorage.removeItem('token');
      this.userSubject.next(null); // Emitir el cambio
    }
  }
}
