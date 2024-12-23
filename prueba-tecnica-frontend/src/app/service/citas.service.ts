import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Cita } from '../models/class/cita';
import { Observable } from 'rxjs';
import { CitaDTO } from '../models/dto/citaDto';

@Injectable({
  providedIn: 'root'
})
export class CitasService {

  private baseUrl = 'http://localhost:8080/api/citas';

  constructor(private http: HttpClient) { }

  crearCita(citaDTO: CitaDTO): Observable<Cita> {
    return this.http.post<Cita>(this.baseUrl, citaDTO);
  }

  cancelarCita(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  obtenerCitasPorCliente(clienteId: number): Observable<Cita[]> {
    return this.http.get<Cita[]>(`${this.baseUrl}/cliente/${clienteId}`);
  }

  obtenerCitasFuturas(): Observable<Cita[]> {
    return this.http.get<Cita[]>(`${this.baseUrl}/futuras`);
  }

  verificarDisponibilidad(fecha: Date): Observable<boolean> {
    return this.http.get<boolean>(`${this.baseUrl}/disponibilidad`, {
      params: { fecha: fecha.toISOString() }
    });
  }
}
