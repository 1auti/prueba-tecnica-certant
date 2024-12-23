import { DiasSemana } from "../enums/diaSemana";
import { Especialidad } from "../enums/especialidad";
import { JornadaLaboralDTO } from "./jornadaLaboralDto";

export interface TecnicoDTO {
    id?: number;
    nombre: string;
    apellido: string;
    email: string;
    dni: string;
    telefono: string;
    cardId: number;
    especialidad: Especialidad;
    estaActivo?: boolean;
    salario: number;
    jornadaLaboral: Record<DiasSemana, JornadaLaboralDTO>;
}