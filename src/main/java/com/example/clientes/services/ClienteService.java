package com.example.clientes.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.clientes.dto.ClienteRequest;
import com.example.clientes.dto.ClienteResponse;
import com.example.clientes.exceptions.DuplicateResourceException;
import com.example.clientes.exceptions.ResourceNotFoundException;
import com.example.clientes.models.Cliente;
import com.example.clientes.repositories.ClienteRepository;

import jakarta.validation.Valid;

@Service
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * Obtener cliente por ID.
     */
    public ClienteResponse buscarPorId(Long id) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cliente no encontrado con ID: " + id));

        return convertirResponse(cliente);
    }

    /**
     * Obtener todos los clientes.
     */
    public List<ClienteResponse> listarTodos() {

        return clienteRepository.findAll()
                .stream()
                .map(this::convertirResponse)
                .collect(Collectors.toList());
    }

    /**
     * Crear cliente.
     */
    public ClienteResponse guardar(@Valid ClienteRequest request) {

        if (clienteRepository.existsByEmail(request.getEmail())) {

            throw new DuplicateResourceException(
                    "El correo '" + request.getEmail() + "' ya existe.");
        }

        Cliente cliente = new Cliente();

        cliente.setNombreCliente(request.getNombreCliente());
        cliente.setEmail(request.getEmail());
        cliente.setTelefono(request.getTelefono());
        cliente.setEstado(request.getEstado());

        Cliente guardado = clienteRepository.save(cliente);

        return convertirResponse(guardado);
    }

    /**
     * Actualizar cliente.
     */
    public ClienteResponse actualizar(
            Long id,
            @Valid ClienteRequest request) {

        Cliente existente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cliente no encontrado con ID: " + id));

        existente.setNombreCliente(request.getNombreCliente());
        existente.setEmail(request.getEmail());
        existente.setTelefono(request.getTelefono());
        existente.setEstado(request.getEstado());

        Cliente actualizado =
                clienteRepository.save(existente);

        return convertirResponse(actualizado);
    }

    /**
     * Eliminar cliente.
     */
    public void eliminar(Long id) {

        if (!clienteRepository.existsById(id)) {

            throw new ResourceNotFoundException(
                    "Cliente no encontrado con ID: " + id);
        }

        clienteRepository.deleteById(id);
    }

    /**
     * Convierte entidad a DTO de respuesta.
     */
    private ClienteResponse convertirResponse(Cliente cliente) {

        return new ClienteResponse(
                cliente.getId(),
                cliente.getNombreCliente(),
                cliente.getEmail(),
                cliente.getTelefono(),
                cliente.getEstado());
    }
}