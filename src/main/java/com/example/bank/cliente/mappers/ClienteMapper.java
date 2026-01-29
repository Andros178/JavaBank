package com.example.bank.cliente.mappers;

import com.example.bank.cliente.Cliente;
import com.example.bank.cliente.dtos.ClienteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    
    @Mapping(target = "tipoIdentificacionId", source = "tipoIdentificacion.id")
    ClienteDTO toDTO(Cliente cliente);
    
    @Mapping(target = "tipoIdentificacion", ignore = true)
    Cliente toEntity(ClienteDTO dto);
}
