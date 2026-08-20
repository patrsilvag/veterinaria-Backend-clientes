package com.example.clientes.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.clientes.dto.ClienteRequest;
import com.example.clientes.dto.ClienteResponse;
import com.example.clientes.services.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(
        origins = {
                "http://mi-app-docker",
                "http://localhost",
                "http://localhost:4200"
        },
        allowedHeaders = "*",
        methods = {
                RequestMethod.POST,
                RequestMethod.GET,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS
        })
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /**
     * POST /api/clientes
     */
    @PostMapping({"", "/"})
    public ResponseEntity<ClienteResponse> crearCliente(
            @Valid @RequestBody ClienteRequest request) {

        ClienteResponse response =
                clienteService.guardar(request);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }

    /**
     * GET /api/clientes
     */
    @GetMapping({"", "/"})
    public ResponseEntity<List<ClienteResponse>> obtenerClientes() {

        return ResponseEntity.ok(
                clienteService.listarTodos());
    }

    /**
     * GET /api/clientes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> obtenerPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                clienteService.buscarPorId(id));
    }

    /**
     * PUT /api/clientes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> actualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequest request) {

        return ResponseEntity.ok(
                clienteService.actualizar(id, request));
    }

    /**
     * DELETE /api/clientes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(
            @PathVariable Long id) {

        clienteService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}