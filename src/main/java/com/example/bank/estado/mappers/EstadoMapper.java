package com.example.bank.estado.mappers;

import com.example.bank.estado.Estado;
import com.example.bank.estado.dtos.EstadoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EstadoMapper {
    EstadoDTO toDTO(Estado estado);
    Estado toEntity(EstadoDTO dto);
}
