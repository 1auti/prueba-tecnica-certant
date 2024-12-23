import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Cita } from '../models/class/cita';
import { Observable } from 'rxjs';
import { SeguimientoService } from './seguimiento.service';

@Injectable({
  providedIn: 'root'
})
export class NotificacionService {

  private baseUrl = 'http://localhost:8080/api/notificaciones';

  constructor(private http: HttpClient) { }

  crearNotificacionRecordatorio(cita: Cita): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/recordatorio`, cita);
  }

  enviarNotificacionEmail(notificacion: NotificacionService): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/email`, notificacion);
  }

  enviarRecordatoriosCitas(): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/enviar-recordatorios`, null);
  }

  notificarCambioEstado(seguimiento: SeguimientoService): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/cambio-estado`, seguimiento);
  }
}
