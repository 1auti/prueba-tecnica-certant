export interface HistorialServicioDTO {
    id?: number;
    citaId: number;
    completadoALas: Date;
    observaciones: string;
    detallesTrabajo: string;
    controlCalidadChequiado: boolean;
    tecnicoId: number;
    clienteId: number;
    calificacionServicio?: number;
}