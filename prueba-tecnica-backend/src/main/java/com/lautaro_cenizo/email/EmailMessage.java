package com.lautaro_cenizo.email;

import com.lautaro_cenizo.entity.notificacion.Notificacion;
import lombok.Data;

@Data
public class EmailMessage {
    private String to;
    private String subject;
    private Notificacion notificacion;
}
