import { HistorialServicio } from "./historialServicio";
import { Repuesto } from "./repuesto";

export class RepuestoUsado {
    id!: number;
    repuesto!: Repuesto;
    historialServicio!: HistorialServicio;
    cantidad!: number;
    precioUnitario!: number;
  
    constructor(data?: Partial<RepuestoUsado>) {
      Object.assign(this, data);
    }
  }