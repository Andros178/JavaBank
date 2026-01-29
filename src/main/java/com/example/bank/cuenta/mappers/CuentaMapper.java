package com.example.bank.cuenta.mappers;

import com.example.bank.cuenta.Cuenta;
import com.example.bank.cuenta.dtos.CuentaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CuentaMapper {
    
    @Mapping(target = "tipoCuentaId", source = "tipoCuenta.id")
    @Mapping(target = "clienteId", source = "cliente.id")
    @Mapping(target = "estadoId", source = "estado.id")
    CuentaDTO toDTO(Cuenta cuenta);
    
    @Mapping(target = "tipoCuenta", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "estado", ignore = true)
    Cuenta toEntity(CuentaDTO dto);
}
