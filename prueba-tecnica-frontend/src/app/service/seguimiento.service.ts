import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EstadoDetallado } from '../models/enums/estadoDetallado';
import { Tecnico } from '../models/class/tecnico';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SeguimientoService {

  private baseUrl = 'http://localhost:8080/api/seguimientos';

  constructor(private http: HttpClient) { }

  actualizarEstado(
    seguimientoId: number, 
    nuevoEstado: EstadoDetallado, 
    motivo: string, 
    tecnico: Tecnico
  ): Observable<void> {
    return this.http.put<void>(
      `${this.baseUrl}/${seguimientoId}/actualizar-estado`,
      tecnico,
      { params: { nuevoEstado: nuevoEstado.toString(), motivo } }
    );
  }
}
