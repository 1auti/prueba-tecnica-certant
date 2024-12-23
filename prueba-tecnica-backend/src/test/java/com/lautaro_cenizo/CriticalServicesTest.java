package com.lautaro_cenizo;


import com.lautaro_cenizo.crud.dto.CitaDTO;
import com.lautaro_cenizo.crud.dto.ClienteDTO;
import com.lautaro_cenizo.crud.dto.TecnicoDTO;
import com.lautaro_cenizo.entity.cita.CitaRepository;
import com.lautaro_cenizo.entity.cliente.ClienteRepository;
import com.lautaro_cenizo.entity.servicio.ServicioRepository;
import com.lautaro_cenizo.entity.tecnico.TecnicoRepository;
import com.lautaro_cenizo.entity.vehiculo.VehiculoRepository;
import com.lautaro_cenizo.exception.BusinessException;
import com.lautaro_cenizo.entity.cita.Cita;
import com.lautaro_cenizo.entity.cliente.Cliente;
import com.lautaro_cenizo.entity.servicio.Servicio;
import com.lautaro_cenizo.entity.tecnico.Tecnico;
import com.lautaro_cenizo.entity.vehiculo.Vehiculo;
import com.lautaro_cenizo.service.impl.CitasServiceImpl;
import com.lautaro_cenizo.service.impl.ClienteServiceImpl;
import com.lautaro_cenizo.service.impl.TecnicoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CriticalServicesTest {

    @Mock
    private CitaRepository citaRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private VehiculoRepository vehiculoRepository;
    @Mock
    private ServicioRepository servicioRepository;
    @Mock
    private TecnicoRepository tecnicoRepository;

    @InjectMocks
    private CitasServiceImpl citasService;
    @InjectMocks
    private ClienteServiceImpl clienteService;
    @InjectMocks
    private TecnicoServiceImpl tecnicoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test 1: Crear cita exitosamente cuando hay disponibilidad")
    void crearCitaExitosa() {
        // Arrange
        CitaDTO citaDTO = new CitaDTO();
        citaDTO.setClienteId(1);
        citaDTO.setVehiculoId(1);
        citaDTO.setServicioId(1);
        citaDTO.setFecha(LocalDateTime.now().plusDays(1));
        citaDTO.setNotas("Cambio de aceite");

        Cliente cliente = new Cliente();
        cliente.setId(1);
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1);
        Servicio servicio = new Servicio();
        servicio.setId(1);

        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(vehiculoRepository.findById(1)).thenReturn(Optional.of(vehiculo));
        when(servicioRepository.findById(1)).thenReturn(Optional.of(servicio));
        when(citaRepository.countCitasByFecha(any())).thenReturn(0L);
        when(citaRepository.save(any())).thenReturn(new Cita());

        // Act
        Cita resultado = citasService.crearCita(citaDTO);

        // Assert
        assertNotNull(resultado);
        verify(citaRepository).save(any());
        verify(clienteRepository).findById(1);
        verify(vehiculoRepository).findById(1);
        verify(servicioRepository).findById(1);
    }

    @Test
    @DisplayName("Test 2: Registrar cliente nuevo con validación de email duplicado")
    void registrarClienteNuevoConValidacionEmail() {
        // Arrange
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNombre("Juan");
        clienteDTO.setApellido("Pérez");
        clienteDTO.setEmail("juan@test.com");
        clienteDTO.setDni("12345678");
        clienteDTO.setTelefono("1234567890");

        when(clienteRepository.existsByEmail("juan@test.com")).thenReturn(true);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
                () -> clienteService.guardarCliente(clienteDTO));
        assertEquals("Ya existe un cliente con ese email", exception.getMessage());
        verify(clienteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test 3: Incrementar contador de servicios y actualizar estado premium")
    void incrementarContadorServiciosYActualizarEstadoPremium() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setContadorServicios(9);
        cliente.setEsPremuim(false);

        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any())).thenReturn(cliente);

        // Act
        clienteService.incrementarContadorServicios(1);

        // Assert
        assertTrue(cliente.getEsPremuim());
        assertEquals(10, cliente.getContadorServicios());
        verify(clienteRepository).save(cliente);
    }

    @Test
    @DisplayName("Test 4: Validar registro de técnico con Card ID duplicado")
    void validarRegistroTecnicoCardIdDuplicado() {
        // Arrange
        TecnicoDTO tecnicoDTO = new TecnicoDTO();
        tecnicoDTO.setNombre("Pedro");
        tecnicoDTO.setApellido("Gómez");
        tecnicoDTO.setCardId(12345L);

        when(tecnicoRepository.findByCardId(12345L)).thenReturn(Optional.of(new Tecnico()));

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
                () -> tecnicoService.crear(tecnicoDTO));
        assertEquals("Ya existe un técnico con ese Card ID", exception.getMessage());
        verify(tecnicoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test 5: Verificar disponibilidad de citas en horario específico")
    void verificarDisponibilidadCitasHorarioEspecifico() {
        // Arrange
        LocalDateTime fecha = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0);
        when(citaRepository.countCitasByFecha(fecha)).thenReturn(3L);

        // Act
        boolean hayDisponibilidad = citasService.verificarDisponibilidad(fecha);

        // Assert
        assertFalse(hayDisponibilidad, "No debería haber disponibilidad cuando ya hay 3 citas");
        verify(citaRepository).countCitasByFecha(fecha);
    }
}