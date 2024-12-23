import { Estado } from "../enums/estado";
import { Cliente } from "./cliente";
import { HistorialServicio } from "./historialServicio";
import { Servicio } from "./servicio";
import { Vehiculo } from "./vehiculo";

export class Cita {
    id!: number;
    fecha!: Date;
    precioFinal!: number;
    proceso!: Estado;
    notas!: string;
    esServicioGratis: boolean = false;
    cliente!: Cliente;
    vehiculo!: Vehiculo;
    servicio!: Servicio;
    historialServicio?: HistorialServicio;
  
    constructor(data?: Partial<Cita>) {
      Object.assign(this, data);
    }
  
    calcularPrecioFinal(): void {
      if (this.esServicioGratis) {
        this.precioFinal = 0;
      } else if (this.cliente?.esPremuim) {
        this.precioFinal = this.servicio.precio * 0.85; // 15% descuento
      } else {
        this.precioFinal = this.servicio.precio;
      }
    }
  
    puedeSerCancelada(): boolean {
      return this.proceso === Estado.ESPERA && 
             new Date().getTime() < new Date(this.fecha).getTime() - (24 * 60 * 60 * 1000);
    }
  }