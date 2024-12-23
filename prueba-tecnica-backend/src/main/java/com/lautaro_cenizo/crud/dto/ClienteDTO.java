package com.lautaro_cenizo.crud.dto;

import lombok.Data;
import java.util.List;

@Data
public class ClienteDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String dni;
    private String telefono;
}

