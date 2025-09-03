package com.SistemaMedico.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SistemaMedico.model.CitaModel;
import com.SistemaMedico.model.ClienteModel;
import com.SistemaMedico.repository.CitaRepository;

@Service
@Transactional
public class CitaService {
    
    @Autowired
    private CitaRepository citaRepository;
    
    @Autowired
    private ClienteService clienteService;
    
    // Obtener todas las citas
    @Transactional(readOnly = true)
    public List<CitaModel> obtenerTodasLasCitas() {
        return citaRepository.findAll();
    }
    
    // Obtener cita por ID
    @Transactional(readOnly = true)
    public Optional<CitaModel> obtenerCitaPorId(Long id) {
        return citaRepository.findById(id);
    }
    
    // Obtener citas por identificación del paciente
    @Transactional(readOnly = true)
    public List<CitaModel> obtenerCitasPorIdentificacion(Integer noId) {
        return citaRepository.findAllCitasByNoIdOrderByFecha(noId);
    }
    
    // Método de compatibilidad
    @Transactional(readOnly = true)
    public List<CitaModel> obtenerCitasPorIdentificacion(int noId) {
        return citaRepository.findAllCitasByNoIdOrderByFecha(noId);
    }
    
    // Obtener citas programadas por identificación
    @Transactional(readOnly = true)
    public List<CitaModel> obtenerCitasProgramadasPorIdentificacion(Integer noId) {
        return citaRepository.findCitasProgramadasByNoId(noId);
    }
    
    // Guardar cita
    public CitaModel guardarCita(CitaModel cita) {
        // Validaciones básicas
        if (cita.getNoId() == null || cita.getNombrePaciente() == null || cita.getNombrePaciente().trim().isEmpty()) {
            throw new IllegalArgumentException("Los datos básicos de la cita son obligatorios");
        }
        
        if (cita.getFechaCita() == null || cita.getFechaCita().trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha de la cita es obligatoria");
        }
        
        // Establecer valores por defecto
        if (cita.getEstadoCita() == null || cita.getEstadoCita().trim().isEmpty()) {
            cita.setEstadoCita("PROGRAMADA");
        }
        
        if (cita.getFechaCreacion() == null) {
            cita.setFechaCreacion(LocalDateTime.now());
        }
        
        // Verificar si existe el cliente y vincularlo
        Optional<ClienteModel> clienteOpt = clienteService.obtenerClientePorIdentificacion(cita.getNoId());
        if (clienteOpt.isPresent()) {
            cita.setCliente(clienteOpt.get());
        } else {
            // Si no existe el cliente, crearlo automáticamente con la información de la cita
            ClienteModel nuevoCliente = new ClienteModel();
            nuevoCliente.setNoId(cita.getNoId());
            nuevoCliente.setNombreCliente(cita.getNombrePaciente());
            nuevoCliente.setEdadCliente(cita.getEdadPaciente());
            nuevoCliente.setGeneroCliente(cita.getGeneroPaciente());
            
            ClienteModel clienteGuardado = clienteService.guardarCliente(nuevoCliente);
            cita.setCliente(clienteGuardado);
        }
        
        return citaRepository.save(cita);
    }
    
    // Actualizar cita
    public CitaModel actualizarCita(Long id, CitaModel citaActualizada) {
        return citaRepository.findById(id)
            .map(cita -> {
                cita.setNombrePaciente(citaActualizada.getNombrePaciente());
                cita.setEdadPaciente(citaActualizada.getEdadPaciente());
                cita.setTipoCita(citaActualizada.getTipoCita());
                cita.setGeneroPaciente(citaActualizada.getGeneroPaciente());
                cita.setFechaCita(citaActualizada.getFechaCita());
                cita.setMotivoCita(citaActualizada.getMotivoCita());
                cita.setEstadoCita(citaActualizada.getEstadoCita());
                return citaRepository.save(cita);
            })
            .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));
    }
    
    // Cambiar estado de la cita
    public CitaModel cambiarEstadoCita(Long id, String nuevoEstado) {
        return citaRepository.findById(id)
            .map(cita -> {
                cita.setEstadoCita(nuevoEstado.toUpperCase());
                return citaRepository.save(cita);
            })
            .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));
    }
    
    // Cancelar cita
    public CitaModel cancelarCita(Long id) {
        return cambiarEstadoCita(id, "CANCELADA");
    }
    
    // Completar cita
    public CitaModel completarCita(Long id) {
        return cambiarEstadoCita(id, "COMPLETADA");
    }
    
    // Eliminar cita
    public void eliminarCita(Long id) {
        if (!citaRepository.existsById(id)) {
            throw new RuntimeException("Cita no encontrada con ID: " + id);
        }
        citaRepository.deleteById(id);
    }
    
    // Obtener citas por estado
    @Transactional(readOnly = true)
    public List<CitaModel> obtenerCitasPorEstado(String estado) {
        return citaRepository.findByEstadoCitaIgnoreCase(estado);
    }
    
    // Obtener citas por fecha
    @Transactional(readOnly = true)
    public List<CitaModel> obtenerCitasPorFecha(String fecha) {
        return citaRepository.findByFechaCita(fecha);
    }
    
    // Obtener citas por tipo
    @Transactional(readOnly = true)
    public List<CitaModel> obtenerCitasPorTipo(String tipo) {
        return citaRepository.findByTipoCitaIgnoreCase(tipo);
    }
    
    // Obtener citas recientes (últimos 30 días)
    @Transactional(readOnly = true)
    public List<CitaModel> obtenerCitasRecientes() {
        LocalDateTime fechaLimite = LocalDateTime.now().minusDays(30);
        return citaRepository.findCitasRecientes(fechaLimite);
    }
    
    // Contar citas por estado
    @Transactional(readOnly = true)
    public long contarCitasPorEstado(String estado) {
        return citaRepository.countByEstadoCitaIgnoreCase(estado);
    }
    
    // Obtener estadísticas básicas
    @Transactional(readOnly = true)
    public CitaEstadisticas obtenerEstadisticas() {
        long totalCitas = citaRepository.count();
        long citasProgramadas = contarCitasPorEstado("PROGRAMADA");
        long citasCompletadas = contarCitasPorEstado("COMPLETADA");
        long citasCanceladas = contarCitasPorEstado("CANCELADA");
        
        return new CitaEstadisticas(totalCitas, citasProgramadas, citasCompletadas, citasCanceladas);
    }
    
    // Clase auxiliar para estadísticas
    public static class CitaEstadisticas {
        private long totalCitas;
        private long citasProgramadas;
        private long citasCompletadas;
        private long citasCanceladas;
        
        public CitaEstadisticas(long totalCitas, long citasProgramadas, long citasCompletadas, long citasCanceladas) {
            this.totalCitas = totalCitas;
            this.citasProgramadas = citasProgramadas;
            this.citasCompletadas = citasCompletadas;
            this.citasCanceladas = citasCanceladas;
        }
        
        // Getters
        public long getTotalCitas() { return totalCitas; }
        public long getCitasProgramadas() { return citasProgramadas; }
        public long getCitasCompletadas() { return citasCompletadas; }
        public long getCitasCanceladas() { return citasCanceladas; }
    }
}
