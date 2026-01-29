package com.example.bank.tipoIdentificacion.mappers;

import com.example.bank.tipoIdentificacion.TipoIdentificacion;
import com.example.bank.tipoIdentificacion.dtos.TipoIdentificacionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TipoIdentificacionMapper {
    
    @Mapping(target = "estadoId", source = "estado.id")
    TipoIdentificacionDTO toDTO(TipoIdentificacion tipoIdentificacion);
    
    @Mapping(target = "estado", ignore = true)
    TipoIdentificacion toEntity(TipoIdentificacionDTO dto);
}