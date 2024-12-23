package com.lautaro_cenizo.exception;

// Excepción personalizada para manejar errores de lógica de negocio
public class BusinessException extends RuntimeException {

    // Constructor con un mensaje personalizado
    public BusinessException(String message) {
        super(message);
    }

    // Constructor con mensaje y causa
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}