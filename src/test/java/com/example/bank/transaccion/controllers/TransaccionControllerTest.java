package com.example.bank.transaccion.controllers;

import com.example.bank.cuenta.Cuenta;
import com.example.bank.tipoTransaccion.TipoTransaccion;
import com.example.bank.transaccion.Transaccion;
import com.example.bank.transaccion.dtos.TransaccionCreateDTO;
import com.example.bank.transaccion.dtos.TransaccionDTO;
import com.example.bank.transaccion.mappers.TransaccionMapper;
import com.example.bank.transaccion.services.TransaccionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransaccionControllerTest {

    @Mock
    private TransaccionService transaccionService;

    @Mock
    private TransaccionMapper transaccionMapper;

    @InjectMocks
    private TransaccionController transaccionController;

    private Transaccion transaccion;
    private TransaccionDTO transaccionDTO;
    private TransaccionCreateDTO transaccionCreateDTO;

    @BeforeEach
    void setUp() {
        Cuenta cuentaOrigen = Cuenta.builder().id(1L).numeroCuenta("3300000001").build();
        Cuenta cuentaDestino = Cuenta.builder().id(2L).numeroCuenta("3300000002").build();
        TipoTransaccion tipoTransaccion = TipoTransaccion.builder().id(1L).nombre("Transferencia").build();

        transaccion = Transaccion.builder()
                .id(1L)
                .cuentaOrigen(cuentaOrigen)
                .cuentaDestino(cuentaDestino)
                .tipoTransaccion(tipoTransaccion)
                .monto(BigDecimal.valueOf(500))
                .descripcion("Transferencia de prueba")
                .build();

        transaccionDTO = TransaccionDTO.builder()
                .id(1L)
                .monto(BigDecimal.valueOf(500))
                .descripcion("Transferencia de prueba")
                .build();

        transaccionCreateDTO = TransaccionCreateDTO.builder()
                .cuentaOrigenId(1L)
                .cuentaDestinoId(2L)
                .tipoTransaccionId(1L)
                .monto(BigDecimal.valueOf(500))
                .descripcion("Transferencia de prueba")
                .build();
    }

    @Test
    void testGetAll() {
        List<Transaccion> transacciones = List.of(transaccion);
        when(transaccionService.getAllTransacciones()).thenReturn(transacciones);
        when(transaccionMapper.toDTO(transaccion)).thenReturn(transaccionDTO);

        ResponseEntity<List<TransaccionDTO>> response = transaccionController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetById_Success() {
        when(transaccionService.getTransaccionById(1L)).thenReturn(transaccion);
        when(transaccionMapper.toDTO(transaccion)).thenReturn(transaccionDTO);

        ResponseEntity<TransaccionDTO> response = transaccionController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(BigDecimal.valueOf(500), response.getBody().getMonto());
    }  
   

    @Test
    void testGetByTipo() {
        List<Transaccion> transacciones = List.of(transaccion);
        when(transaccionService.getTransaccionesByTipo(1L)).thenReturn(transacciones);
        when(transaccionMapper.toDTO(transaccion)).thenReturn(transaccionDTO);

        ResponseEntity<List<TransaccionDTO>> response = transaccionController.getByTipo(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testCreate_Success() {
        when(transaccionService.createTransaccion(any(TransaccionCreateDTO.class))).thenReturn(transaccion);
        when(transaccionMapper.toDTO(transaccion)).thenReturn(transaccionDTO);

        ResponseEntity<?> response = transaccionController.create(transaccionCreateDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(transaccionService, times(1)).createTransaccion(any(TransaccionCreateDTO.class));
    }

    

    @Test
    void testDelete_Success() {
        doNothing().when(transaccionService).deleteTransaccion(1L);

        ResponseEntity<?> response = transaccionController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(transaccionService, times(1)).deleteTransaccion(1L);
    }

  
}
