package com.lautaro_cenizo.entity.tecnico;

import lombok.Getter;

@Getter
public enum Especialidad {
    WASHING("Lavado"),
    MECHANICS("Mecánica"),
    ALIGNMENT("Alineación y Balanceo"),
    OIL_CHANGE("Cambio de Aceite");

    private final String description;

    Especialidad(String description) {
        this.description = description;
    }


}
