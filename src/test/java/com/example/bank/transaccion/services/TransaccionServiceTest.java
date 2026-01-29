package com.example.bank.transaccion.services;

import com.example.bank.cuenta.Cuenta;
import com.example.bank.cuenta.services.CuentaService;
import com.example.bank.tipoTransaccion.TipoTransaccion;
import com.example.bank.tipoTransaccion.services.TipoTransaccionService;
import com.example.bank.transaccion.Transaccion;
import com.example.bank.transaccion.dtos.TransaccionCreateDTO;
import com.example.bank.transaccion.repositories.TransaccionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransaccionServiceTest {

    @Mock
    private TransaccionRepository transaccionRepository;

    @Mock
    private CuentaService cuentaService;

    @Mock
    private TipoTransaccionService tipoTransaccionService;

    @InjectMocks
    private TransaccionService transaccionService;

    private Transaccion transaccion;
    private TransaccionCreateDTO transaccionCreateDTO;
    private Cuenta cuentaOrigen;
    private Cuenta cuentaDestino;
    private TipoTransaccion tipoTransaccion;

    @BeforeEach
    void setUp() {
        cuentaOrigen = Cuenta.builder()
                .id(1L)
                .numeroCuenta("3300000001")
                .saldo(BigDecimal.valueOf(5000))
                .build();

        cuentaDestino = Cuenta.builder()
                .id(2L)
                .numeroCuenta("3300000002")
                .saldo(BigDecimal.valueOf(1000))
                .build();

        tipoTransaccion = TipoTransaccion.builder()
                .id(1L)
                .nombre("Transferencia")
                .build();

        transaccion = Transaccion.builder()
                .id(1L)
                .cuentaOrigen(cuentaOrigen)
                .cuentaDestino(cuentaDestino)
                .tipoTransaccion(tipoTransaccion)
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
    void testCreateTransaccion_Transferencia_Success() {
        when(cuentaService.getCuentaById(1L)).thenReturn(cuentaOrigen);
        when(cuentaService.getCuentaById(2L)).thenReturn(cuentaDestino);
        when(tipoTransaccionService.getTipoTransaccionById(1L)).thenReturn(tipoTransaccion);
        when(cuentaService.updateSaldo(any(Long.class), any(BigDecimal.class))).thenReturn(null);
        when(transaccionRepository.save(any(Transaccion.class))).thenReturn(transaccion);

        Transaccion result = transaccionService.createTransaccion(transaccionCreateDTO);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(500), result.getMonto());
        verify(cuentaService, times(2)).updateSaldo(any(Long.class), any(BigDecimal.class));
    }

    @Test
    void testCreateTransaccion_Transferencia_SinCuentaDestino() {
        transaccionCreateDTO.setCuentaDestinoId(null);
        
        when(cuentaService.getCuentaById(1L)).thenReturn(cuentaOrigen);
        when(tipoTransaccionService.getTipoTransaccionById(1L)).thenReturn(tipoTransaccion);

        assertThrows(IllegalArgumentException.class, 
                () -> transaccionService.createTransaccion(transaccionCreateDTO));
    }

    @Test
    void testCreateTransaccion_Retiro_Success() {
        TipoTransaccion tipoRetiro = TipoTransaccion.builder().id(2L).nombre("Retiro").build();
        transaccionCreateDTO.setTipoTransaccionId(2L);
        transaccionCreateDTO.setCuentaDestinoId(null);

        when(cuentaService.getCuentaById(1L)).thenReturn(cuentaOrigen);
        when(tipoTransaccionService.getTipoTransaccionById(2L)).thenReturn(tipoRetiro);
        when(cuentaService.updateSaldo(any(Long.class), any(BigDecimal.class))).thenReturn(null);
        when(transaccionRepository.save(any(Transaccion.class))).thenReturn(transaccion);

        Transaccion result = transaccionService.createTransaccion(transaccionCreateDTO);

        assertNotNull(result);
        verify(cuentaService, times(1)).updateSaldo(any(Long.class), any(BigDecimal.class));
    }

    @Test
    void testCreateTransaccion_Consignacion_Success() {
        TipoTransaccion tipoConsignacion = TipoTransaccion.builder().id(3L).nombre("Consignacion").build();
        transaccionCreateDTO.setTipoTransaccionId(3L);
        transaccionCreateDTO.setCuentaDestinoId(null);

        when(cuentaService.getCuentaById(1L)).thenReturn(cuentaOrigen);
        when(tipoTransaccionService.getTipoTransaccionById(3L)).thenReturn(tipoConsignacion);
        when(cuentaService.updateSaldo(any(Long.class), any(BigDecimal.class))).thenReturn(null);
        when(transaccionRepository.save(any(Transaccion.class))).thenReturn(transaccion);

        Transaccion result = transaccionService.createTransaccion(transaccionCreateDTO);

        assertNotNull(result);
        verify(cuentaService, times(1)).updateSaldo(any(Long.class), any(BigDecimal.class));
    }

    @Test
    void testGetTransaccionById_Success() {
        when(transaccionRepository.findById(1L)).thenReturn(Optional.of(transaccion));

        Transaccion result = transaccionService.getTransaccionById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(BigDecimal.valueOf(500), result.getMonto());
    }

    @Test
    void testGetTransaccionById_NotFound() {
        when(transaccionRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, 
                () -> transaccionService.getTransaccionById(999L));
    }

    @Test
    void testGetAllTransacciones() {
        List<Transaccion> transacciones = List.of(transaccion);
        when(transaccionRepository.findAll()).thenReturn(transacciones);

        List<Transaccion> result = transaccionService.getAllTransacciones();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetTransaccionesByCuentaOrigen() {
        List<Transaccion> transacciones = List.of(transaccion);
        when(cuentaService.getCuentaById(1L)).thenReturn(cuentaOrigen);
        when(transaccionRepository.findByCuentaOrigenId(1L)).thenReturn(transacciones);

        List<Transaccion> result = transaccionService.getTransaccionesByCuentaOrigen(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetTransaccionesByCuentaDestino() {
        List<Transaccion> transacciones = List.of(transaccion);
        when(cuentaService.getCuentaById(2L)).thenReturn(cuentaDestino);
        when(transaccionRepository.findByCuentaDestinoId(2L)).thenReturn(transacciones);

        List<Transaccion> result = transaccionService.getTransaccionesByCuentaDestino(2L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetTransaccionesByTipo() {
        List<Transaccion> transacciones = List.of(transaccion);
        when(tipoTransaccionService.getTipoTransaccionById(1L)).thenReturn(tipoTransaccion);
        when(transaccionRepository.findByTipoTransaccionId(1L)).thenReturn(transacciones);

        List<Transaccion> result = transaccionService.getTransaccionesByTipo(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testDeleteTransaccion_Success() {
        when(transaccionRepository.findById(1L)).thenReturn(Optional.of(transaccion));
        doNothing().when(transaccionRepository).delete(any(Transaccion.class));

        transaccionService.deleteTransaccion(1L);

        verify(transaccionRepository, times(1)).delete(any(Transaccion.class));
    }
}
