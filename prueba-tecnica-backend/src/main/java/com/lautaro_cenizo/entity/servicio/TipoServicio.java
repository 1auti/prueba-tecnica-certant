package com.lautaro_cenizo.entity.servicio;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum TipoServicio {
    // Lavado
    LAVADO_BASICO("Lavado Básico", new BigDecimal("1500")),
    LAVADO_COMPLETO("Lavado Completo", new BigDecimal("2500")),
    LAVADO_PREMIUM("Lavado Premium", new BigDecimal("3500")),

    // Alineación y Balanceo
    ALINEACION_BALANCEO("Alineación y Balanceo", new BigDecimal("4000")),
    ALINEACION_BALANCEO_CUBIERTAS("Alineación, Balanceo y Cambio de Cubiertas", new BigDecimal("8000")),

    // Cambio de Aceite
    CAMBIO_ACEITE_BASICO_NAFTA("Cambio de Aceite Básico - Nafta", new BigDecimal("3000")),
    CAMBIO_ACEITE_BASICO_DIESEL("Cambio de Aceite Básico - Diesel", new BigDecimal("3500")),
    CAMBIO_ACEITE_ALTO_RENDIMIENTO_NAFTA("Cambio de Aceite Alto Rendimiento - Nafta", new BigDecimal("5000")),
    CAMBIO_ACEITE_ALTO_RENDIMIENTO_DIESEL("Cambio de Aceite Alto Rendimiento - Diesel", new BigDecimal("5500"));

    private final String descripcion;
    private final BigDecimal precioBase;

    TipoServicio(String descripcion, BigDecimal precioBase) {
        this.descripcion = descripcion;
        this.precioBase = precioBase;
    }

}
