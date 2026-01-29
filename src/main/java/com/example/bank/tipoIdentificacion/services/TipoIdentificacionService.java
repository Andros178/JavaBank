package com.example.bank.tipoIdentificacion.services;

import com.example.bank.estado.Estado;
import com.example.bank.estado.services.EstadoService;
import com.example.bank.tipoIdentificacion.TipoIdentificacion;
import com.example.bank.tipoIdentificacion.dtos.TipoIdentificacionCreateDTO;
import com.example.bank.tipoIdentificacion.repositories.TipoIdentificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoIdentificacionService {

    private final TipoIdentificacionRepository tipoIdentificacionRepository;
    private final EstadoService estadoService;

    private static final String ESTADO_DEFAULT = "Activo";

    @Transactional
    public TipoIdentificacion createTipoIdentificacion(TipoIdentificacionCreateDTO dto) {
        // Validar que no exista otro tipo de identificación con el mismo nombre
        if (tipoIdentificacionRepository.existsByNombre(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe un tipo de identificación con este nombre");
        }

        // Obtener el estado por defecto "Activo"
        Estado estado = estadoService.getEstadoByNombre(ESTADO_DEFAULT);

        TipoIdentificacion tipoIdentificacion = TipoIdentificacion.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .estado(estado)
                .build();

        return tipoIdentificacionRepository.save(tipoIdentificacion);
    }

    
    public TipoIdentificacion getTipoIdentificacionById(Long id) {
        return tipoIdentificacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de identificación no encontrado con ID: " + id));
    }

    
    public List<TipoIdentificacion> getAllTiposIdentificacion() {
        return tipoIdentificacionRepository.findAll();
    }

    @Transactional
    public TipoIdentificacion updateTipoIdentificacion(Long id, TipoIdentificacionCreateDTO dto) {
        TipoIdentificacion tipoIdentificacion = getTipoIdentificacionById(id);

        // Validar que no exista otro tipo de identificación con el mismo nombre (excluyendo el actual)
        if (!tipoIdentificacion.getNombre().equals(dto.getNombre()) && 
            tipoIdentificacionRepository.existsByNombre(dto.getNombre())) {
            throw new IllegalArgumentException("Ya existe un tipo de identificación con este nombre");
        }

        tipoIdentificacion.setNombre(dto.getNombre());
        tipoIdentificacion.setDescripcion(dto.getDescripcion());

        return tipoIdentificacionRepository.save(tipoIdentificacion);
    }

    @Transactional
    public void deleteTipoIdentificacion(Long id) {
        TipoIdentificacion tipoIdentificacion = getTipoIdentificacionById(id);
        tipoIdentificacionRepository.delete(tipoIdentificacion);
    }

    public TipoIdentificacion getTipoIdentificacionByNombre(String nombre) {
        return tipoIdentificacionRepository.findByNombre(nombre)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de identificación no encontrado con nombre: " + nombre));
    }
}
