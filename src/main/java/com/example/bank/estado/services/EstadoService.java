package com.example.bank.estado.services;

import com.example.bank.estado.Estado;
import com.example.bank.estado.dtos.EstadoCreateDTO;
import com.example.bank.estado.repositories.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadoService {

    private final EstadoRepository estadoRepository;

    @Transactional
    public Estado createEstado(EstadoCreateDTO dto) {
        if (estadoRepository.existsByNombre(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe un estado con este nombre");
        }

        Estado estado = Estado.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .build();

        return estadoRepository.save(estado);
    }

    public Estado getEstadoById(Long id) {
        return estadoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estado no encontrado con ID: " + id));
    }

    public List<Estado> getAllEstados() {
        return estadoRepository.findAll();
    }

    @Transactional
    public Estado updateEstado(Long id, EstadoCreateDTO dto) {
        Estado estado = getEstadoById(id);

        if (!estado.getNombre().equals(dto.getNombre()) && estadoRepository.existsByNombre(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe un estado con este nombre");
        }

        estado.setNombre(dto.getNombre());
        estado.setDescripcion(dto.getDescripcion());

        return estadoRepository.save(estado);
    }

    @Transactional
    public void deleteEstado(Long id) {
        Estado estado = getEstadoById(id);
        estadoRepository.delete(estado);
    }

    public Estado getEstadoByNombre(String nombre) {
        return estadoRepository.findByNombre(nombre)
                .orElseThrow(() -> new IllegalArgumentException("Estado no encontrado con nombre: " + nombre));
    }
}
