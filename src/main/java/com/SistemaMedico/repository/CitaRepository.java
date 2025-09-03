package com.SistemaMedico.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SistemaMedico.model.CitaModel;

@Repository
public interface CitaRepository extends JpaRepository<CitaModel, Long> {
    
    // Buscar citas por número de identificación del paciente
    List<CitaModel> findByNoId(Integer noId);
    
    // Buscar citas por estado
    List<CitaModel> findByEstadoCitaIgnoreCase(String estado);
    
    // Buscar citas por fecha
    List<CitaModel> findByFechaCita(String fechaCita);
    
    // Buscar citas por tipo
    List<CitaModel> findByTipoCitaIgnoreCase(String tipoCita);
    
    // Buscar citas por cliente
    List<CitaModel> findByClienteId(Long clienteId);
    
    // Buscar citas programadas por identificación
    @Query("SELECT c FROM CitaModel c WHERE c.noId = :noId AND c.estadoCita = 'PROGRAMADA' ORDER BY c.fechaCita")
    List<CitaModel> findCitasProgramadasByNoId(@Param("noId") Integer noId);
    
    // Buscar citas por rango de fechas
    @Query("SELECT c FROM CitaModel c WHERE c.fechaCreacion BETWEEN :fechaInicio AND :fechaFin")
    List<CitaModel> findCitasByFechaCreacionBetween(@Param("fechaInicio") LocalDateTime fechaInicio, 
                                                   @Param("fechaFin") LocalDateTime fechaFin);
    
    // Contar citas por estado
    long countByEstadoCitaIgnoreCase(String estado);
    
    // Buscar citas recientes (últimos 30 días)
    @Query("SELECT c FROM CitaModel c WHERE c.fechaCreacion >= :fechaLimite ORDER BY c.fechaCreacion DESC")
    List<CitaModel> findCitasRecientes(@Param("fechaLimite") LocalDateTime fechaLimite);
    
    // Buscar todas las citas de un paciente ordenadas por fecha
    @Query("SELECT c FROM CitaModel c WHERE c.noId = :noId ORDER BY c.fechaCita DESC")
    List<CitaModel> findAllCitasByNoIdOrderByFecha(@Param("noId") Integer noId);
}
