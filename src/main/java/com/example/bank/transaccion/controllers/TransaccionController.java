package com.example.bank.transaccion.controllers;

import com.example.bank.transaccion.Transaccion;
import com.example.bank.transaccion.dtos.TransaccionCreateDTO;
import com.example.bank.transaccion.dtos.TransaccionDTO;
import com.example.bank.transaccion.mappers.TransaccionMapper;
import com.example.bank.transaccion.services.TransaccionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transacciones")
@RequiredArgsConstructor
public class TransaccionController {

    private final TransaccionService transaccionService;
    private final TransaccionMapper transaccionMapper;

    @GetMapping
    public ResponseEntity<List<TransaccionDTO>> getAll() {
        List<Transaccion> transacciones = transaccionService.getAllTransacciones();
        List<TransaccionDTO> dtos = transacciones.stream()
                .map(transaccionMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransaccionDTO> getById(@PathVariable Long id) {
        try {
            Transaccion transaccion = transaccionService.getTransaccionById(id);
            return ResponseEntity.ok(transaccionMapper.toDTO(transaccion));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cuenta-origen/{cuentaOrigenId}")
    public ResponseEntity<List<TransaccionDTO>> getByCuentaOrigen(@PathVariable Long cuentaOrigenId) {
        try {
            List<Transaccion> transacciones = transaccionService.getTransaccionesByCuentaOrigen(cuentaOrigenId);
            List<TransaccionDTO> dtos = transacciones.stream()
                    .map(transaccionMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cuenta-destino/{cuentaDestinoId}")
    public ResponseEntity<List<TransaccionDTO>> getByCuentaDestino(@PathVariable Long cuentaDestinoId) {
        try {
            List<Transaccion> transacciones = transaccionService.getTransaccionesByCuentaDestino(cuentaDestinoId);
            List<TransaccionDTO> dtos = transacciones.stream()
                    .map(transaccionMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tipo/{tipoTransaccionId}")
    public ResponseEntity<List<TransaccionDTO>> getByTipo(@PathVariable Long tipoTransaccionId) {
        try {
            List<Transaccion> transacciones = transaccionService.getTransaccionesByTipo(tipoTransaccionId);
            List<TransaccionDTO> dtos = transacciones.stream()
                    .map(transaccionMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TransaccionCreateDTO dto) {
        try {
            Transaccion transaccion = transaccionService.createTransaccion(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(transaccionMapper.toDTO(transaccion));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            transaccionService.deleteTransaccion(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
