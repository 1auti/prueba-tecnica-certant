import { TiposVehiculo } from "../enums/tiposVehiculo";
import { Cliente } from "./cliente";

export class Vehiculo {
    id!: number;
    patente!: string;
    anio!: number;
    modelo!: string;
    marca!: string;
    color!: string;
    tiposVehiculo!: TiposVehiculo;
    cliente!: Cliente;
  
    constructor(data?: Partial<Vehiculo>) {
      Object.assign(this, data);
    }
  
    validarPatente(): boolean {
      const patenteRegex = /^(([A-Z]{3}\d{3})|([A-Z]{2}\d{3}[A-Z]{2}))$/;
      return patenteRegex.test(this.patente);
    }
  }