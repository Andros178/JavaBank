package com.example.bank.tipoTransaccion.mappers;

import com.example.bank.tipoTransaccion.TipoTransaccion;
import com.example.bank.tipoTransaccion.dtos.TipoTransaccionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TipoTransaccionMapper {
    TipoTransaccionDTO toDTO(TipoTransaccion tipoTransaccion);
    TipoTransaccion toEntity(TipoTransaccionDTO dto);
}
