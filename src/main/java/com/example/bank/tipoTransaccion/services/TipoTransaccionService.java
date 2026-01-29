package com.example.bank.tipoTransaccion.services;

import com.example.bank.tipoTransaccion.TipoTransaccion;
import com.example.bank.tipoTransaccion.dtos.TipoTransaccionCreateDTO;
import com.example.bank.tipoTransaccion.repositories.TipoTransaccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoTransaccionService {

    private final TipoTransaccionRepository tipoTransaccionRepository;

    @Transactional
    public TipoTransaccion createTipoTransaccion(TipoTransaccionCreateDTO dto) {
        if (tipoTransaccionRepository.existsByNombre(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe un tipo de transacción con este nombre");
        }

        TipoTransaccion tipoTransaccion = TipoTransaccion.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .build();

        return tipoTransaccionRepository.save(tipoTransaccion);
    }

    public TipoTransaccion getTipoTransaccionById(Long id) {
        return tipoTransaccionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de transacción no encontrado con ID: " + id));
    }

    public List<TipoTransaccion> getAllTiposTransaccion() {
        return tipoTransaccionRepository.findAll();
    }

    @Transactional
    public TipoTransaccion updateTipoTransaccion(Long id, TipoTransaccionCreateDTO dto) {
        TipoTransaccion tipoTransaccion = getTipoTransaccionById(id);

        if (!tipoTransaccion.getNombre().equals(dto.getNombre()) && tipoTransaccionRepository.existsByNombre(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe un tipo de transacción con este nombre");
        }

        tipoTransaccion.setNombre(dto.getNombre());
        tipoTransaccion.setDescripcion(dto.getDescripcion());

        return tipoTransaccionRepository.save(tipoTransaccion);
    }

    @Transactional
    public void deleteTipoTransaccion(Long id) {
        TipoTransaccion tipoTransaccion = getTipoTransaccionById(id);
        tipoTransaccionRepository.delete(tipoTransaccion);
    }

    public TipoTransaccion getTipoTransaccionByNombre(String nombre) {
        return tipoTransaccionRepository.findByNombre(nombre)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de transacción no encontrado con nombre: " + nombre));
    }
}
