package com.example.bank.tipoCuenta.controllers;

import com.example.bank.tipoCuenta.TipoCuenta;
import com.example.bank.tipoCuenta.dtos.TipoCuentaCreateDTO;
import com.example.bank.tipoCuenta.dtos.TipoCuentaDTO;
import com.example.bank.tipoCuenta.mappers.TipoCuentaMapper;
import com.example.bank.tipoCuenta.services.TipoCuentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tipos-cuenta")
@RequiredArgsConstructor
public class TipoCuentaController {

    private final TipoCuentaService tipoCuentaService;
    private final TipoCuentaMapper tipoCuentaMapper;

    @GetMapping
    public ResponseEntity<List<TipoCuentaDTO>> getAll() {
        List<TipoCuenta> tiposCuenta = tipoCuentaService.getAllTiposCuenta();
        List<TipoCuentaDTO> dtos = tiposCuenta.stream()
                .map(tipoCuentaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoCuentaDTO> getById(@PathVariable Long id) {
        try {
            TipoCuenta tipoCuenta = tipoCuentaService.getTipoCuentaById(id);
            return ResponseEntity.ok(tipoCuentaMapper.toDTO(tipoCuenta));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<TipoCuentaDTO> getByNombre(@PathVariable String nombre) {
        try {
            TipoCuenta tipoCuenta = tipoCuentaService.getTipoCuentaByNombre(nombre);
            return ResponseEntity.ok(tipoCuentaMapper.toDTO(tipoCuenta));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TipoCuentaCreateDTO dto) {
        try {
            TipoCuenta tipoCuenta = tipoCuentaService.createTipoCuenta(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(tipoCuentaMapper.toDTO(tipoCuenta));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody TipoCuentaCreateDTO dto) {
        try {
            TipoCuenta tipoCuenta = tipoCuentaService.updateTipoCuenta(id, dto);
            return ResponseEntity.ok(tipoCuentaMapper.toDTO(tipoCuenta));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            tipoCuentaService.deleteTipoCuenta(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
