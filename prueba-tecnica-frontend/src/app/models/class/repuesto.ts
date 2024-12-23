import { CategoriaRepuesto } from "../enums/categoriaRepuesto";

export class Repuesto {
    id!: number;
    codigo!: string;
    nombre!: string;
    descripcion!: string;
    precio!: number;
    stock!: number;
    stockMinimo!: number;
    categoria!: CategoriaRepuesto;
  
    constructor(data?: Partial<Repuesto>) {
      Object.assign(this, data);
    }
  }