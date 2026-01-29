package com.example.bank.transaccion.mappers;

import com.example.bank.transaccion.Transaccion;
import com.example.bank.transaccion.dtos.TransaccionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransaccionMapper {
    
    @Mapping(target = "cuentaOrigenId", source = "cuentaOrigen.id")
    @Mapping(target = "cuentaDestinoId", source = "cuentaDestino.id")
    @Mapping(target = "tipoTransaccionId", source = "tipoTransaccion.id")
    TransaccionDTO toDTO(Transaccion transaccion);
    
    @Mapping(target = "cuentaOrigen", ignore = true)
    @Mapping(target = "cuentaDestino", ignore = true)
    @Mapping(target = "tipoTransaccion", ignore = true)
    Transaccion toEntity(TransaccionDTO dto);
}
