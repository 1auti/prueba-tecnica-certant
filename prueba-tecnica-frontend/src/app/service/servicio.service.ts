import { Injectable } from '@angular/core';
import { Servicio } from '../models/class/servicio';
import { Observable } from 'rxjs';
import { TipoServicio } from '../models/enums/tipoServicio';
import { HttpClient } from '@angular/common/http';
import { ServicioDTO } from '../models/dto/servicioDto';

@Injectable({
  providedIn: 'root'
})
export class ServicioService {

  private baseUrl = 'http://localhost:8080/api/servicios';

  constructor(private http: HttpClient) { }

  crear(servicioDTO: ServicioDTO): Observable<Servicio> {
    return this.http.post<Servicio>(this.baseUrl, servicioDTO);
  }

  actualizar(id: number, servicioDTO: ServicioDTO): Observable<Servicio> {
    return this.http.put<Servicio>(`${this.baseUrl}/${id}`, servicioDTO);
  }

  obtenerPorId(id: number): Observable<Servicio> {
    return this.http.get<Servicio>(`${this.baseUrl}/${id}`);
  }

  listarTodos(): Observable<Servicio[]> {
    return this.http.get<Servicio[]>(this.baseUrl);
  }

  listarActivos(): Observable<Servicio[]> {
    return this.http.get<Servicio[]>(`${this.baseUrl}/activos`);
  }

  listarPorTipo(tipo: TipoServicio): Observable<Servicio[]> {
    return this.http.get<Servicio[]>(`${this.baseUrl}/tipo/${tipo}`);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  activar(id: number): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/${id}/activar`, null);
  }

  desactivar(id: number): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/${id}/desactivar`, null);
  }
}
