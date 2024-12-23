import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RepuestoService {

  private baseUrl = 'http://localhost:8080/api/repuestos';

  constructor(private http: HttpClient) { }

  alertaStockBajo(): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/alerta-stock-bajo`, null);
  }
}
