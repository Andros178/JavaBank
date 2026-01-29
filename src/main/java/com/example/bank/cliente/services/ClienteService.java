package com.example.bank.cliente.services;

import com.example.bank.cliente.Cliente;
import com.example.bank.cliente.dtos.ClienteCreateDTO;
import com.example.bank.cliente.repositories.ClienteRepository;
import com.example.bank.cuenta.repositories.CuentaRepository;
import com.example.bank.tipoIdentificacion.TipoIdentificacion;
import com.example.bank.tipoIdentificacion.services.TipoIdentificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final TipoIdentificacionService tipoIdentificacionService;
    private final CuentaRepository cuentaRepository;

    @Transactional
    public Cliente createCliente(ClienteCreateDTO dto) {
      
        if (isMenorDeEdad(dto.getFechaNacimiento())) {
            throw new IllegalArgumentException("No se pueden registrar menores de edad");
        }

     
        if (clienteRepository.existsByNumeroIdentificacion(dto.getNumeroIdentificacion())) {
            throw new IllegalArgumentException("Ya existe un cliente con este número de identificación");
        }

        if (clienteRepository.existsByCorreo(dto.getCorreo())) {
            throw new IllegalArgumentException("Ya existe un cliente con este correo");
        }

       
        TipoIdentificacion tipoIdentificacion = tipoIdentificacionService.getTipoIdentificacionById(dto.getTipoIdentificacionId());

        Cliente cliente = Cliente.builder()
                .numeroIdentificacion(dto.getNumeroIdentificacion())
                .primerNombre(dto.getPrimerNombre())
                .segundoNombre(dto.getSegundoNombre())
                .primerApellido(dto.getPrimerApellido())
                .segundoApellido(dto.getSegundoApellido())
                .correo(dto.getCorreo())
                .fechaNacimiento(dto.getFechaNacimiento())
                .tipoIdentificacion(tipoIdentificacion)
                .build();

        return clienteRepository.save(cliente);
    }

  
    public Cliente getClienteById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + id));
    }

   
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

   
    @Transactional
    public Cliente updateCliente(Long id, ClienteCreateDTO dto) {
        Cliente cliente = getClienteById(id);

      
        if (!cliente.getNumeroIdentificacion().equals(dto.getNumeroIdentificacion()) && 
            clienteRepository.existsByNumeroIdentificacion(dto.getNumeroIdentificacion())) {
            throw new IllegalArgumentException("Ya existe un cliente con este número de identificación");
        }

       
        if (!cliente.getCorreo().equals(dto.getCorreo()) && 
            clienteRepository.existsByCorreo(dto.getCorreo())) {
            throw new IllegalArgumentException("Ya existe un cliente con este correo");
        }

        cliente.setNumeroIdentificacion(dto.getNumeroIdentificacion());
        cliente.setPrimerNombre(dto.getPrimerNombre());
        cliente.setSegundoNombre(dto.getSegundoNombre());
        cliente.setPrimerApellido(dto.getPrimerApellido());
        cliente.setSegundoApellido(dto.getSegundoApellido());
        cliente.setCorreo(dto.getCorreo());
        cliente.setFechaNacimiento(dto.getFechaNacimiento());

      
        if (!cliente.getTipoIdentificacion().getId().equals(dto.getTipoIdentificacionId())) {
            TipoIdentificacion tipoIdentificacion = tipoIdentificacionService.getTipoIdentificacionById(dto.getTipoIdentificacionId());
            cliente.setTipoIdentificacion(tipoIdentificacion);
        }

        return clienteRepository.save(cliente);
    }

   
    @Transactional
    public void deleteCliente(Long id) {
        Cliente cliente = getClienteById(id);
        
        if (!cuentaRepository.findByClienteId(id).isEmpty()) {
            throw new IllegalArgumentException("No se puede eliminar un cliente con productos asociados");
        }
        
        clienteRepository.delete(cliente);
    }

    public Cliente getClienteByNumeroIdentificacion(String numeroIdentificacion) {
        return clienteRepository.findByNumeroIdentificacion(numeroIdentificacion)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con número de identificación: " + numeroIdentificacion));
    }

  
    public Cliente getClienteByCorreo(String correo) {
        return clienteRepository.findByCorreo(correo)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con correo: " + correo));
    }

    
    private boolean isMenorDeEdad(LocalDate fechaNacimiento) {
        LocalDate hoy = LocalDate.now();
        return fechaNacimiento.plusYears(18).isAfter(hoy);
    }
}
