import { Injectable } from '@angular/core';
import { TecnicoDTO } from '../models/dto/tecnicoDto';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tecnico } from '../models/class/tecnico';
import { Especialidad } from '../models/enums/especialidad';
import { HistorialServicio } from '../models/class/historialServicio';
import { DiasSemana } from '../models/enums/diaSemana';
import { JornadaLaboralDTO } from '../models/dto/jornadaLaboralDto';

@Injectable({
  providedIn: 'root'
})
export class TecnicoService {

  private baseUrl = 'http://localhost:8080/api/tecnicos';

  constructor(private http: HttpClient) { }

  crear(tecnicoDTO: TecnicoDTO): Observable<Tecnico> {
    return this.http.post<Tecnico>(this.baseUrl, tecnicoDTO);
  }

  actualizar(id: number, tecnicoDTO: TecnicoDTO): Observable<Tecnico> {
    return this.http.put<Tecnico>(`${this.baseUrl}/${id}`, tecnicoDTO);
  }

  obtenerPorId(id: number): Observable<Tecnico> {
    return this.http.get<Tecnico>(`${this.baseUrl}/${id}`);
  }

  obtenerPorCardId(cardId: number): Observable<Tecnico> {
    return this.http.get<Tecnico>(`${this.baseUrl}/card/${cardId}`);
  }

  listarTodos(): Observable<Tecnico[]> {
    return this.http.get<Tecnico[]>(this.baseUrl);
  }

  listarActivos(): Observable<Tecnico[]> {
    return this.http.get<Tecnico[]>(`${this.baseUrl}/activos`);
  }

  listarPorEspecialidad(especialidad: Especialidad): Observable<Tecnico[]> {
    return this.http.get<Tecnico[]>(`${this.baseUrl}/especialidad/${especialidad}`);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  registrarServicio(tecnicoId: number, servicio: HistorialServicio): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/${tecnicoId}/registrar-servicio`, servicio);
  }

  verificarDisponibilidad(tecnicoId: number, fecha: Date): Observable<boolean> {
    return this.http.get<boolean>(
      `${this.baseUrl}/${tecnicoId}/disponibilidad`,
      { params: { fecha: fecha.toISOString() } }
    );
  }

  actualizarJornadaLaboral(
    tecnicoId: number,
    jornadaLaboral: Record<DiasSemana, JornadaLaboralDTO>
  ): Observable<void> {
    return this.http.put<void>(
      `${this.baseUrl}/${tecnicoId}/jornada-laboral`,
      jornadaLaboral
    );
  }
}
