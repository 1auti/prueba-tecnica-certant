import { Injectable } from '@angular/core';
import { Cliente } from '../models/class/cliente';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { ClienteDTO } from '../models/dto/clienteDto';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  private baseUrl = 'http://localhost:8080/api/clientes';


  constructor(private http: HttpClient) { }

  guardarCliente(clienteDTO: ClienteDTO): Observable<Cliente> {
    return this.http.post<Cliente>(this.baseUrl, clienteDTO);
  }

  eliminarCliente(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  obtenerClientePorId(id: number): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.baseUrl}/${id}`);
  }

  listarClientes(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(this.baseUrl);
  }

  actualizarCliente(id: number, clienteDTO: ClienteDTO): Observable<Cliente> {
    return this.http.put<Cliente>(`${this.baseUrl}/${id}`, clienteDTO);
  }

  incrementarContadorServicios(id: number): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/${id}/incrementar-servicios`, null);
  }

  buscarPorDNI(dni: string): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.baseUrl}/buscar`, {
      params: { dni }
    });
  }

  obtenerClientesPremium(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(`${this.baseUrl}/premium`);
  }
}
