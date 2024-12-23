package com.lautaro_cenizo.service;

import com.lautaro_cenizo.entity.cita.Cita;
import com.lautaro_cenizo.entity.notificacion.Notificacion;
import com.lautaro_cenizo.entity.seguimiento.SeguimientoServicio;

public interface NotificacionServicio {
    void crearNotificacionRecordatorio(Cita cita);
    void enviarNotificacionEmail(Notificacion notificacion);
    void enviarRecordatoriosCitas();
    void notificarCambioEstado(SeguimientoServicio seguimiento);


}
