package com.lautaro_cenizo.exception;


// Excepción personalizada para manejar recursos no encontrados
public class ResourceNotFoundException extends RuntimeException {

    // Constructor con un mensaje personalizado
    public ResourceNotFoundException(String message) {
        super(message);
    }

    // Constructor con mensaje y causa
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}