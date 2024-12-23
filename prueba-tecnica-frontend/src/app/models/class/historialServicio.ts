import { Cita } from "./cita";
import { Cliente } from "./cliente";
import { Repuesto } from "./repuesto";
import { RepuestoUsado } from "./repuestoUsado";
import { Tecnico } from "./tecnico";

export class HistorialServicio {
    id!: number;
    cita!: Cita;
    completadoALas!: Date;
    observaciones!: string;
    detallesTrabajo!: string;
    controlCalidadChequiado!: boolean;
    tecnico!: Tecnico;
    cliente!: Cliente;
    calificacionServicio!: number;
    repuestosUsados: RepuestoUsado[] = [];
  
    constructor(data?: Partial<HistorialServicio>) {
      Object.assign(this, data);
    }
  
    agregarRepuesto(repuesto: Repuesto, cantidad: number): void {
      if (repuesto.stock < cantidad) {
        throw new Error("Stock insuficiente");
      }
  
      const repuestoUsado = new RepuestoUsado({
        repuesto: repuesto,
        cantidad: cantidad,
        historialServicio: this,
        precioUnitario: repuesto.precio
      });
  
      this.repuestosUsados.push(repuestoUsado);
      repuesto.stock -= cantidad;
    }
  
    completarServicio(): void {
      this.completadoALas = new Date();
      this.controlCalidadChequiado = false;
    }
  
    calificarServicio(calificacion: number): void {
      if (calificacion < 0 || calificacion > 10) {
        throw new Error("La calificación debe estar entre 0 y 10");
      }
      this.calificacionServicio = calificacion;
    }
  }
  