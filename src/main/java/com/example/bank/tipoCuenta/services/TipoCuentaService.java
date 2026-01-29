package com.example.bank.tipoCuenta.services;

import com.example.bank.estado.Estado;
import com.example.bank.estado.services.EstadoService;
import com.example.bank.tipoCuenta.TipoCuenta;
import com.example.bank.tipoCuenta.dtos.TipoCuentaCreateDTO;
import com.example.bank.tipoCuenta.repositories.TipoCuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoCuentaService {

    private final TipoCuentaRepository tipoCuentaRepository;
    private final EstadoService estadoService;

    private static final String ESTADO_DEFAULT = "Activo";

    @Transactional
    public TipoCuenta createTipoCuenta(TipoCuentaCreateDTO dto) {
        if (tipoCuentaRepository.existsByNombre(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe un tipo de cuenta con este nombre");
        }

        Estado estado = estadoService.getEstadoByNombre(ESTADO_DEFAULT);

        TipoCuenta tipoCuenta = TipoCuenta.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .estado(estado)
                .build();

        return tipoCuentaRepository.save(tipoCuenta);
    }

    public TipoCuenta getTipoCuentaById(Long id) {
        return tipoCuentaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de cuenta no encontrado con ID: " + id));
    }

    public List<TipoCuenta> getAllTiposCuenta() {
        return tipoCuentaRepository.findAll();
    }

    @Transactional
    public TipoCuenta updateTipoCuenta(Long id, TipoCuentaCreateDTO dto) {
        TipoCuenta tipoCuenta = getTipoCuentaById(id);

        if (!tipoCuenta.getNombre().equals(dto.getNombre()) && tipoCuentaRepository.existsByNombre(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe un tipo de cuenta con este nombre");
        }

        tipoCuenta.setNombre(dto.getNombre());
        tipoCuenta.setDescripcion(dto.getDescripcion());

        return tipoCuentaRepository.save(tipoCuenta);
    }

    @Transactional
    public void deleteTipoCuenta(Long id) {
        TipoCuenta tipoCuenta = getTipoCuentaById(id);
        tipoCuentaRepository.delete(tipoCuenta);
    }

    public TipoCuenta getTipoCuentaByNombre(String nombre) {
        return tipoCuentaRepository.findByNombre(nombre)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de cuenta no encontrado con nombre: " + nombre));
    }
}
