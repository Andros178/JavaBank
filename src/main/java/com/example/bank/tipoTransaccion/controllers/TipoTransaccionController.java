package com.example.bank.tipoTransaccion.controllers;

import com.example.bank.tipoTransaccion.TipoTransaccion;
import com.example.bank.tipoTransaccion.dtos.TipoTransaccionCreateDTO;
import com.example.bank.tipoTransaccion.dtos.TipoTransaccionDTO;
import com.example.bank.tipoTransaccion.mappers.TipoTransaccionMapper;
import com.example.bank.tipoTransaccion.services.TipoTransaccionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tipos-transaccion")
@RequiredArgsConstructor
public class TipoTransaccionController {

    private final TipoTransaccionService tipoTransaccionService;
    private final TipoTransaccionMapper tipoTransaccionMapper;

    @GetMapping
    public ResponseEntity<List<TipoTransaccionDTO>> getAll() {
        List<TipoTransaccion> tiposTransaccion = tipoTransaccionService.getAllTiposTransaccion();
        List<TipoTransaccionDTO> dtos = tiposTransaccion.stream()
                .map(tipoTransaccionMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoTransaccionDTO> getById(@PathVariable Long id) {
        try {
            TipoTransaccion tipoTransaccion = tipoTransaccionService.getTipoTransaccionById(id);
            return ResponseEntity.ok(tipoTransaccionMapper.toDTO(tipoTransaccion));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<TipoTransaccionDTO> getByNombre(@PathVariable String nombre) {
        try {
            TipoTransaccion tipoTransaccion = tipoTransaccionService.getTipoTransaccionByNombre(nombre);
            return ResponseEntity.ok(tipoTransaccionMapper.toDTO(tipoTransaccion));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TipoTransaccionCreateDTO dto) {
        try {
            TipoTransaccion tipoTransaccion = tipoTransaccionService.createTipoTransaccion(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(tipoTransaccionMapper.toDTO(tipoTransaccion));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody TipoTransaccionCreateDTO dto) {
        try {
            TipoTransaccion tipoTransaccion = tipoTransaccionService.updateTipoTransaccion(id, dto);
            return ResponseEntity.ok(tipoTransaccionMapper.toDTO(tipoTransaccion));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            tipoTransaccionService.deleteTipoTransaccion(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
