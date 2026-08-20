package com.example.clientes.exceptions;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Cliente no encontrado.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFound(ResourceNotFoundException ex,
            WebRequest request) {

        log.warn("Cliente no encontrado: {} - URL: {}", ex.getMessage(),
                request.getDescription(false));

        ErrorDetails error =
                new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Errores de validación de los datos recibidos.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {

        String errores = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(" | "));

        log.warn("Error de validación en la petición: {}", errores);

        ErrorDetails error = new ErrorDetails(new Date(), "Error de validación: " + errores,
                request.getDescription(false));

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Cliente duplicado.
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorDetails> handleDuplicateResource(DuplicateResourceException ex,
            WebRequest request) {

        log.warn("CONFLICTO: Intento de crear cliente duplicado - {}", ex.getMessage());

        ErrorDetails error =
                new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    /**
     * Error general no controlado.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {

        log.error("ERROR INTERNO DEL SISTEMA: {} - Detalle: {}", ex.getMessage(),
                request.getDescription(false));

        ErrorDetails error = new ErrorDetails(new Date(),
                "Ocurrió un error inesperado en el servidor", request.getDescription(false));

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
