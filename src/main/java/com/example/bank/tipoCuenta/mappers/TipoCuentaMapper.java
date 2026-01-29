package com.example.bank.tipoCuenta.mappers;

import com.example.bank.tipoCuenta.TipoCuenta;
import com.example.bank.tipoCuenta.dtos.TipoCuentaDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface TipoCuentaMapper {
    
    TipoCuentaDTO toDTO(TipoCuenta tipoCuenta);
    TipoCuenta toEntity(TipoCuentaDTO dto);
}
