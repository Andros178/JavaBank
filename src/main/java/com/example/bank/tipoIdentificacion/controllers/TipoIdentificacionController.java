package com.example.bank.tipoIdentificacion.controllers;

import com.example.bank.tipoIdentificacion.TipoIdentificacion;
import com.example.bank.tipoIdentificacion.dtos.TipoIdentificacionCreateDTO;
import com.example.bank.tipoIdentificacion.dtos.TipoIdentificacionDTO;
import com.example.bank.tipoIdentificacion.mappers.TipoIdentificacionMapper;
import com.example.bank.tipoIdentificacion.services.TipoIdentificacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tipos-identificacion")
@RequiredArgsConstructor
public class TipoIdentificacionController {

    private final TipoIdentificacionService tipoIdentificacionService;
    private final TipoIdentificacionMapper tipoIdentificacionMapper;

    
    @GetMapping
    public ResponseEntity<List<TipoIdentificacionDTO>> getAll() {
        List<TipoIdentificacion> tiposIdentificacion = tipoIdentificacionService.getAllTiposIdentificacion();
        List<TipoIdentificacionDTO> dtos = tiposIdentificacion.stream()
                .map(tipoIdentificacionMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoIdentificacionDTO> getById(@PathVariable Long id) {
        try {
            TipoIdentificacion tipoIdentificacion = tipoIdentificacionService.getTipoIdentificacionById(id);
            return ResponseEntity.ok(tipoIdentificacionMapper.toDTO(tipoIdentificacion));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<TipoIdentificacionDTO> getByNombre(@PathVariable String nombre) {
        try {
            TipoIdentificacion tipoIdentificacion = tipoIdentificacionService.getTipoIdentificacionByNombre(nombre);
            return ResponseEntity.ok(tipoIdentificacionMapper.toDTO(tipoIdentificacion));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TipoIdentificacionCreateDTO dto) {
        try {
            TipoIdentificacion tipoIdentificacion = tipoIdentificacionService.createTipoIdentificacion(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(tipoIdentificacionMapper.toDTO(tipoIdentificacion));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody TipoIdentificacionCreateDTO dto) {
        try {
            TipoIdentificacion tipoIdentificacion = tipoIdentificacionService.updateTipoIdentificacion(id, dto);
            return ResponseEntity.ok(tipoIdentificacionMapper.toDTO(tipoIdentificacion));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            tipoIdentificacionService.deleteTipoIdentificacion(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
