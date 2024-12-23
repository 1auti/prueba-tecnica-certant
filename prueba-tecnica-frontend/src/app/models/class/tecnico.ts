import { DiasSemana } from "../enums/diaSemana";
import { Especialidad } from "../enums/especialidad";
import { HistorialServicio } from "./historialServicio";
import { JornadaLaboral } from "./jornadaLaboral";
import { Persona } from "./persona.abstract";

// Helper para mapear números de día a DiasSemana
const getDiaSemana = (dayNumber: number): DiasSemana => {
    switch (dayNumber) {
      case 0: return DiasSemana.DOMINGO;
      case 1: return DiasSemana.LUNES;
      case 2: return DiasSemana.MARTES;
      case 3: return DiasSemana.MIERCOLES;
      case 4: return DiasSemana.JUEVES;
      case 5: return DiasSemana.VIERNES;
      case 6: return DiasSemana.SABADO;
      default: throw new Error('Día inválido');
    }
  };
  
  export class Tecnico extends Persona {
      cardId!: number;
      especialidad!: Especialidad;
      estaActivo: boolean = true;
      jornadaLaboral: Record<DiasSemana, JornadaLaboral | undefined> = {
          [DiasSemana.LUNES]: undefined,
          [DiasSemana.MARTES]: undefined,
          [DiasSemana.MIERCOLES]: undefined,
          [DiasSemana.JUEVES]: undefined,
          [DiasSemana.VIERNES]: undefined,
          [DiasSemana.SABADO]: undefined,
          [DiasSemana.DOMINGO]: undefined
      };
      salario!: number;
      serviciosCompletados: HistorialServicio[] = [];
  
      constructor(data?: Partial<Tecnico>) {
          super(data);
          Object.assign(this, data);
      }
  
      tieneDisponibilidad(fecha: Date): boolean {
          const dia = getDiaSemana(fecha.getDay());
          const jornada = this.jornadaLaboral[dia];
          if (!jornada) return false;
          
          const hora = fecha.getHours() * 60 + fecha.getMinutes();
          const inicioMinutos = jornada.horaInicio.getHours() * 60 + jornada.horaInicio.getMinutes();
          const finMinutos = jornada.horaFin.getHours() * 60 + jornada.horaFin.getMinutes();
          
          return hora >= inicioMinutos && hora <= finMinutos;
      }
  
      actualizarJornadaLaboral(dia: DiasSemana, nuevaJornada: JornadaLaboral): void {
          this.jornadaLaboral[dia] = nuevaJornada;
      }
  }