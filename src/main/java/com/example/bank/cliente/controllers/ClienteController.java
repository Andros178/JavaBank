package com.example.bank.cliente.controllers;

import com.example.bank.cliente.Cliente;
import com.example.bank.cliente.dtos.ClienteCreateDTO;
import com.example.bank.cliente.dtos.ClienteDTO;
import com.example.bank.cliente.mappers.ClienteMapper;
import com.example.bank.cliente.services.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;

 
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> getAll() {
        List<Cliente> clientes = clienteService.getAllClientes();
        List<ClienteDTO> dtos = clientes.stream()
                .map(clienteMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getById(@PathVariable Long id) {
        try {
            Cliente cliente = clienteService.getClienteById(id);
            return ResponseEntity.ok(clienteMapper.toDTO(cliente));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    
    @GetMapping("/numero-identificacion/{numeroIdentificacion}")
    public ResponseEntity<ClienteDTO> getByNumeroIdentificacion(@PathVariable String numeroIdentificacion) {
        try {
            Cliente cliente = clienteService.getClienteByNumeroIdentificacion(numeroIdentificacion);
            return ResponseEntity.ok(clienteMapper.toDTO(cliente));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

   
    @GetMapping("/correo/{correo}")
    public ResponseEntity<ClienteDTO> getByCorreo(@PathVariable String correo) {
        try {
            Cliente cliente = clienteService.getClienteByCorreo(correo);
            return ResponseEntity.ok(clienteMapper.toDTO(cliente));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

   
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ClienteCreateDTO dto) {
        try {
            Cliente cliente = clienteService.createCliente(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(clienteMapper.toDTO(cliente));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ClienteCreateDTO dto) {
        try {
            Cliente cliente = clienteService.updateCliente(id, dto);
            return ResponseEntity.ok(clienteMapper.toDTO(cliente));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

   
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            clienteService.deleteCliente(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
