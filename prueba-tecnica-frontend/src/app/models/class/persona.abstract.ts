export abstract class Persona {
    id!: number;
    nombre!: string;
    apellido!: string;
    email!: string;
    dni!: string;
    telefono!: string;
  
    constructor(data?: Partial<Persona>) {
      Object.assign(this, data);
    }
  }
  
  