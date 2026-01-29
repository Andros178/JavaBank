package com.example.bank.tipoCuenta.mappers;

import com.example.bank.tipoCuenta.TipoCuenta;
import com.example.bank.tipoCuenta.dtos.TipoCuentaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TipoCuentaMapper {
    
    @Mapping(target = "estadoId", source = "estado.id")
    TipoCuentaDTO toDTO(TipoCuenta tipoCuenta);
    
    @Mapping(target = "estado", ignore = true)
    TipoCuenta toEntity(TipoCuentaDTO dto);
}
