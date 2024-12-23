import { TipoServicio } from "../enums/tipoServicio";

export class Servicio {
    id!: number;
    descripcion!: string;
    tipoServicio!: TipoServicio;
    precio!: number;
    estaActivo: boolean = false;
  
    constructor(data?: Partial<Servicio>) {
      Object.assign(this, data);
    }
  }