package com.example.clientes.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "CLIENTES")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CLIENTE")
    private Long id;

    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    @Column(name = "NOMBRE_CLIENTE", nullable = false)
    private String nombreCliente;

    @Email(message = "Debe ser un formato de correo válido")
    @NotBlank(message = "El email es obligatorio")
    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Size(max = 20, message = "El teléfono no puede superar los 20 caracteres")
    @Column(name = "TELEFONO")
    private String telefono;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 10, message = "El estado no puede superar los 10 caracteres")
    @Column(name = "ESTADO", nullable = false)
    private String estado;
}