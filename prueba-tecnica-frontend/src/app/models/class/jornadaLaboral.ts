export class JornadaLaboral {
    horaInicio!: Date;
    horaFin!: Date;

    constructor(data?: Partial<JornadaLaboral>) {
        if (data) {
            // Asegurarse de que las fechas sean objetos Date
            if (data.horaInicio) {
                this.horaInicio = new Date(data.horaInicio);
            }
            if (data.horaFin) {
                this.horaFin = new Date(data.horaFin);
            }
        }
    }

    incluyeHorario(horario: Date): boolean {
        const horarioMinutos = horario.getHours() * 60 + horario.getMinutes();
        const inicioMinutos = this.horaInicio.getHours() * 60 + this.horaInicio.getMinutes();
        const finMinutos = this.horaFin.getHours() * 60 + this.horaFin.getMinutes();

        return horarioMinutos >= inicioMinutos && horarioMinutos <= finMinutos;
    }

    // Método helper para crear una jornada desde strings de hora
    static fromTimeStrings(horaInicio: string, horaFin: string): JornadaLaboral {
        const today = new Date();
        const inicio = new Date(today);
        const fin = new Date(today);

        const [horasInicio, minutosInicio] = horaInicio.split(':').map(Number);
        const [horasFin, minutosFin] = horaFin.split(':').map(Number);

        inicio.setHours(horasInicio, minutosInicio, 0);
        fin.setHours(horasFin, minutosFin, 0);

        return new JornadaLaboral({
            horaInicio: inicio,
            horaFin: fin
        });
    }
}