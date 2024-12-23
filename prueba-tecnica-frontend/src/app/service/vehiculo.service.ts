import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Vehiculo } from '../models/class/vehiculo';
import { VehiculoDTO } from '../models/dto/vehiculoDto';

@Injectable({
  providedIn: 'root'
})
export class VehiculoService {

  private baseUrl = 'http://localhost:8080/api/vehiculos';

  constructor(private http: HttpClient) { }

  crear(vehiculoDTO: VehiculoDTO): Observable<Vehiculo> {
    return this.http.post<Vehiculo>(this.baseUrl, vehiculoDTO);
  }

  actualizar(id: number, vehiculoDTO: VehiculoDTO): Observable<Vehiculo> {
    return this.http.put<Vehiculo>(`${this.baseUrl}/${id}`, vehiculoDTO);
  }

  obtenerPorId(id: number): Observable<Vehiculo> {
    return this.http.get<Vehiculo>(`${this.baseUrl}/${id}`);
  }

  obtenerPorPatente(patente: string): Observable<Vehiculo> {
    return this.http.get<Vehiculo>(`${this.baseUrl}/patente/${patente}`);
  }

  listarTodos(): Observable<Vehiculo[]> {
    return this.http.get<Vehiculo[]>(this.baseUrl);
  }

  listarPorCliente(clienteId: number): Observable<Vehiculo[]> {
    return this.http.get<Vehiculo[]>(`${this.baseUrl}/cliente/${clienteId}`);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  cambiarCliente(vehiculoId: number, nuevoClienteId: number): Observable<void> {
    return this.http.put<void>(
      `${this.baseUrl}/${vehiculoId}/cambiar-cliente/${nuevoClienteId}`, 
      null
    );
  }
}
