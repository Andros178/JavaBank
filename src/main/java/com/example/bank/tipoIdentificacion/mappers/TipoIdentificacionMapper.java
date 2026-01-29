package com.example.bank.tipoIdentificacion.mappers;

import com.example.bank.tipoIdentificacion.TipoIdentificacion;
import com.example.bank.tipoIdentificacion.dtos.TipoIdentificacionDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface TipoIdentificacionMapper {
    
    TipoIdentificacionDTO toDTO(TipoIdentificacion tipoIdentificacion);
    TipoIdentificacion toEntity(TipoIdentificacionDTO dto);
}