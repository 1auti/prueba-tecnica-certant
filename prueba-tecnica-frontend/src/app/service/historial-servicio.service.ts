import { Injectable } from '@angular/core';
import { HistorialServicio } from '../models/class/historialServicio';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { HistorialServicioDTO } from '../models/dto/historialServicioDto';

@Injectable({
  providedIn: 'root'
})
export class HistorialServicioService {

  private baseUrl = 'http://localhost:8080/api/historial-servicios';

  constructor(private http: HttpClient) { }

  crear(historialDTO: HistorialServicioDTO): Observable<HistorialServicio> {
    return this.http.post<HistorialServicio>(this.baseUrl, historialDTO);
  }

  actualizar(id: number, historialDTO: HistorialServicioDTO): Observable<HistorialServicio> {
    return this.http.put<HistorialServicio>(`${this.baseUrl}/${id}`, historialDTO);
  }

  obtenerPorId(id: number): Observable<HistorialServicio> {
    return this.http.get<HistorialServicio>(`${this.baseUrl}/${id}`);
  }

  listarTodos(): Observable<HistorialServicio[]> {
    return this.http.get<HistorialServicio[]>(this.baseUrl);
  }

  listarPorCliente(clienteId: number): Observable<HistorialServicio[]> {
    return this.http.get<HistorialServicio[]>(`${this.baseUrl}/cliente/${clienteId}`);
  }

  listarPorTecnico(tecnicoId: number): Observable<HistorialServicio[]> {
    return this.http.get<HistorialServicio[]>(`${this.baseUrl}/tecnico/${tecnicoId}`);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  calificarServicio(id: number, calificacion: number): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/${id}/calificar`, null, {
      params: { calificacion: calificacion.toString() }
    });
  }

  marcarControlCalidad(id: number, aprobado: boolean): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/${id}/control-calidad`, null, {
      params: { aprobado: aprobado.toString() }
    });
  }

}
