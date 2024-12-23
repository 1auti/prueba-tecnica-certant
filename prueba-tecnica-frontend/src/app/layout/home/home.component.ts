import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  services = [
    { name: 'Lavado', description: 'Básico, completo o premium' },
    { name: 'Alineación y Balanceo', description: 'Con o sin cambio de cubiertas' },
    { name: 'Cambio de Aceite y Filtros', description: 'Básico o alto rendimiento, para motores diesel o nafteros' }
  ];
}
