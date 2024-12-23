import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationRequest } from '../../../models/interface/authentication-request';
import { AuthenticationService } from '../../../service/authentication.service';
import { TokenService } from '../../../service/token.service';
import { UserService } from '../../../service/user.service';
import { JwtDecoderService } from '../../../service/jwt-decoder.service';
import { User } from '../../../models/interface/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  authRequest: AuthenticationRequest = { email: '', password: '' };
  errorMsg: Array<string> = [];

  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private tokenService: TokenService,
    private userService: UserService,
    private jwtDecoder: JwtDecoderService
  ) { }

  login() {
    this.errorMsg = [];
    this.authService.authenticate({
      body: this.authRequest
    }).subscribe({
      next: (res) => {
        if (res.token && typeof res.token === 'string') {
          const token = res.token;
          this.tokenService.token = token;

          try {
            // Obtener información del usuario del token
            const userInfo = this.jwtDecoder.getUserInfoFromToken(token);
            if (userInfo) {
              const user: User = {
                id: 0, // Asigna un valor predeterminado para id
                name: userInfo.name,
                email: userInfo.email,
                roles: [] // Asigna un valor predeterminado para roles
              };
              this.userService.setUser(user);
            } else {
              this.errorMsg.push('Información del usuario inválida');
            }
          } catch (error) {
            console.log(error);
            this.errorMsg.push('Error al obtener información del usuario');
          }

          this.router.navigate(['home']);
        } else {
          this.errorMsg.push('Token inválido');
        }
      },
      error: (err) => {
        console.log(err);
        if (err.error.validationErrors) {
          this.errorMsg = err.error.validationErrors;
        } else {
          this.errorMsg.push(err.error.errorMsg);
        }
      }
    });
  }

  register() {
    this.router.navigate(['register']);
  }

  private isValidUserInfo(userInfo: any): boolean {
    // Agrega aquí la lógica para validar la información del usuario
    return true; // Devuelve true si la información del usuario es válida
  }
}