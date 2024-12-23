import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class JwtDecoderService {
  
  decodeToken(token: string): any {
    try {
      const base64Url = token.split('.')[1];
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      return JSON.parse(window.atob(base64));
    } catch (error) {
      console.error('Error decoding token:', error);
      return null;
    }
  }

  getUserInfoFromToken(token: string): { name: string; email: string } | null {
    const decodedToken = this.decodeToken(token);
    if (!decodedToken?.sub) {
      return null;
    }

    const email = decodedToken.sub;
    return {
      name: email.split('@')[0], // Usando la parte local del email como nombre
      email: email
    };
  }
  
  getUserRoles(token: string): string[] | null {
    const decodedToken = this.decodeToken(token);
    return decodedToken?.authorities || null; // "authorities" es la claim que contiene los roles
  }
}