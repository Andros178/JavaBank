package com.example.bank.cuenta.controllers;

import com.example.bank.cuenta.Cuenta;
import com.example.bank.cuenta.dtos.CuentaCreateDTO;
import com.example.bank.cuenta.dtos.CuentaDTO;
import com.example.bank.cuenta.mappers.CuentaMapper;
import com.example.bank.cuenta.services.CuentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;
    private final CuentaMapper cuentaMapper;

    @GetMapping
    public ResponseEntity<List<CuentaDTO>> getAll() {
        List<Cuenta> cuentas = cuentaService.getAllCuentas();
        List<CuentaDTO> dtos = cuentas.stream()
                .map(cuentaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaDTO> getById(@PathVariable Long id) {
        try {
            Cuenta cuenta = cuentaService.getCuentaById(id);
            return ResponseEntity.ok(cuentaMapper.toDTO(cuenta));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/numero/{numeroCuenta}")
    public ResponseEntity<CuentaDTO> getByNumeroCuenta(@PathVariable String numeroCuenta) {
        try {
            Cuenta cuenta = cuentaService.getCuentaByNumeroCuenta(numeroCuenta);
            return ResponseEntity.ok(cuentaMapper.toDTO(cuenta));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<CuentaDTO>> getCuentasByCliente(@PathVariable Long clienteId) {
        try {
            List<Cuenta> cuentas = cuentaService.getCuentasByClienteId(clienteId);
            List<CuentaDTO> dtos = cuentas.stream()
                    .map(cuentaMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CuentaCreateDTO dto) {
        try {
            Cuenta cuenta = cuentaService.createCuenta(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(cuentaMapper.toDTO(cuenta));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<?> activar(@PathVariable Long id) {
        try {
            Cuenta cuenta = cuentaService.activateCuenta(id);
            return ResponseEntity.ok(cuentaMapper.toDTO(cuenta));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/inactivar")
    public ResponseEntity<?> inactivar(@PathVariable Long id) {
        try {
            Cuenta cuenta = cuentaService.inactivateCuenta(id);
            return ResponseEntity.ok(cuentaMapper.toDTO(cuenta));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelar(@PathVariable Long id) {
        try {
            cuentaService.cancelarCuenta(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            cuentaService.deleteCuenta(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
