package com.example.clientes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {

    private Long idCliente;
    private String nombreCliente;
    private String email;
    private String telefono;
    private String estado;
}