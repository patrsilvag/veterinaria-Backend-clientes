package com.example.clientes.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {

    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Size(max = 100)
    private String nombreCliente;

    @Email(message = "Formato de email inválido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @Size(max = 20)
    private String telefono;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 10)
    private String estado;
}