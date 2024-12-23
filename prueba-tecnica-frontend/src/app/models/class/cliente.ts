import { Cita } from "./cita";
import { HistorialServicio } from "./historialServicio";
import { Persona } from "./persona.abstract";
import { Vehiculo } from "./vehiculo";

export class Cliente extends Persona {
    esPremuim: boolean = false;
    contadorServicios: number = 0;
    servicioGratisDisponible: boolean = false;
    fechasUltimosServiciosGratis!: Date;
    citas: Cita[] = [];
    vehiculos: Vehiculo[] = [];
    historialServicios: HistorialServicio[] = [];
  
    constructor(data?: Partial<Cliente>) {
      super(data);
      Object.assign(this, data);
    }
  
    incrementarContadorServicios(): void {
      this.contadorServicios++;
      if (this.contadorServicios >= 5 && !this.esPremuim) {
        this.esPremuim = true;
        this.servicioGratisDisponible = true;
      }
    }
  
    puedeUsarServicioGratis(): boolean {
      return this.esPremuim && this.servicioGratisDisponible;
    }
  
    usarServicioGratis(): void {
      if (!this.puedeUsarServicioGratis()) {
        throw new Error("No hay servicio gratis disponible");
      }
      this.servicioGratisDisponible = false;
      this.fechasUltimosServiciosGratis = new Date();
    }
  }
  