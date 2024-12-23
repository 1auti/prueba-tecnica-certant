import { TiposVehiculo } from "../enums/tiposVehiculo";

export interface VehiculoDTO {
    id?: number;
    patente: string;
    anio: number;
    modelo: string;
    marca: string;
    clienteId: number;
    color: string;
    tiposVehiculo:TiposVehiculo;
}