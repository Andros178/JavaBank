package com.example.bank.estado.controllers;

import com.example.bank.estado.Estado;
import com.example.bank.estado.dtos.EstadoCreateDTO;
import com.example.bank.estado.dtos.EstadoDTO;
import com.example.bank.estado.mappers.EstadoMapper;
import com.example.bank.estado.services.EstadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/estados")
@RequiredArgsConstructor
public class EstadoController {

    private final EstadoService estadoService;
    private final EstadoMapper estadoMapper;

    @GetMapping
    public ResponseEntity<List<EstadoDTO>> getAll() {
        List<Estado> estados = estadoService.getAllEstados();
        List<EstadoDTO> dtos = estados.stream()
                .map(estadoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EstadoDTO> getById(@PathVariable Long id) {
        try {
            Estado estado = estadoService.getEstadoById(id);
            return ResponseEntity.ok(estadoMapper.toDTO(estado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<EstadoDTO> getByNombre(@PathVariable String nombre) {
        try {
            Estado estado = estadoService.getEstadoByNombre(nombre);
            return ResponseEntity.ok(estadoMapper.toDTO(estado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody EstadoCreateDTO dto) {
        try {
            Estado estado = estadoService.createEstado(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(estadoMapper.toDTO(estado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody EstadoCreateDTO dto) {
        try {
            Estado estado = estadoService.updateEstado(id, dto);
            return ResponseEntity.ok(estadoMapper.toDTO(estado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            estadoService.deleteEstado(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
