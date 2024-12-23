package com.lautaro_cenizo.service.impl;

import com.lautaro_cenizo.crud.dto.ClienteDTO;
import com.lautaro_cenizo.entity.notificacion.Notificacion;
import com.lautaro_cenizo.entity.notificacion.TipoNotificacion;
import com.lautaro_cenizo.exception.BusinessException;
import com.lautaro_cenizo.exception.ResourceNotFoundException;
import com.lautaro_cenizo.entity.cliente.Cliente;
import com.lautaro_cenizo.entity.cliente.ClienteRepository;
import com.lautaro_cenizo.service.ClienteService;
import com.lautaro_cenizo.service.NotificacionServicio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final  ClienteRepository clienteRepository;
    private final NotificacionServicio notificacionServicio;

    public ClienteServiceImpl(ClienteRepository clienteRepository, NotificacionServicio notificacionServicio) {
        this.clienteRepository = clienteRepository;
        this.notificacionServicio = notificacionServicio;
    }


    @Override
    public Cliente guardarCliente(ClienteDTO clienteDTO) {
        if (clienteRepository.existsByDni(clienteDTO.getDni())) {
            throw new BusinessException("Ya existe un cliente con ese DNI");
        }
        if (clienteRepository.existsByEmail(clienteDTO.getEmail())) {
            throw new BusinessException("Ya existe un cliente con ese email");
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setDni(clienteDTO.getDni());
        cliente.setTelefono(clienteDTO.getTelefono());

        return clienteRepository.save(cliente);
    }

    @Override
    public void eliminarCliente(Integer id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente no encontrado");
        }
        clienteRepository.deleteById(id);
    }

    @Override
    public Optional<Cliente> obtenerClientePorId(Integer id) {
        return clienteRepository.findById(id);
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente actualizar(Integer id, ClienteDTO clienteDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        // Verificar si el nuevo email está en uso por otro cliente
        if (!cliente.getEmail().equals(clienteDTO.getEmail()) &&
                clienteRepository.existsByEmail(clienteDTO.getEmail())) {
            throw new BusinessException("El email ya está en uso");
        }

        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefono(clienteDTO.getTelefono());

        return clienteRepository.save(cliente);
    }
    @Override
    public void incrementarContadorServicios(Integer clienteId) {
        log.info("Incrementando contador de servicios para cliente con ID: {}", clienteId);

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        cliente.incrementarContadorServicios();

        // Verificar estado premium y enviar notificaciones
        if (cliente.getContadorServicios() >= 5) {
            if (!cliente.getEsPremuim()) {
                cliente.setEsPremuim(true);
                enviarNotificacionPremium(cliente);
                log.info("Cliente ID: {} alcanzó estado premium", clienteId);
            }
        }

        // Verificar servicio gratis cada 10 servicios
        if (cliente.getContadorServicios() % 10 == 0) {
            enviarNotificacionServicioGratis(cliente);
            log.info("Cliente ID: {} obtuvo servicio gratis", clienteId);
        }

        clienteRepository.save(cliente);
    }

    private void enviarNotificacionPremium(Cliente cliente) {
        Notificacion notificacion = new Notificacion();
        notificacion.setTipo(TipoNotificacion.CLIENTE_PREMIUM);
        notificacion.setCliente(cliente);
        notificacion.setMensaje(
                String.format("¡Felicitaciones %s! Has alcanzado el estado Premium. " +
                                "Beneficios:\n" +
                                "- Descuentos exclusivos\n" +
                                "- Prioridad en turnos\n" +
                                "- Lavado gratis en cada servicio",
                        cliente.getNombre())
        );
        notificacionServicio.enviarNotificacionEmail(notificacion);
    }

    private void enviarNotificacionServicioGratis(Cliente cliente) {
        Notificacion notificacion = new Notificacion();
        notificacion.setTipo(TipoNotificacion.SERVICIO_GRATIS_DISPONIBLE);
        notificacion.setCliente(cliente);
        notificacion.setMensaje(
                String.format("¡Felicitaciones %s! Has ganado un servicio gratis. " +
                                "Puedes canjearlo en tu próxima visita.",
                        cliente.getNombre())
        );
        notificacionServicio.enviarNotificacionEmail(notificacion);
    }
    @Override
    public Cliente buscarPorDNI(String dni) {
        return clienteRepository.findByDni(dni)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
    }

    @Override
    public List<Cliente> obtenerClientesPremium() {
        return clienteRepository.findByEsPremuim(true);
    }
}
