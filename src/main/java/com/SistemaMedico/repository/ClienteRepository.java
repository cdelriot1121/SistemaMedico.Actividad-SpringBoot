package com.SistemaMedico.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SistemaMedico.model.ClienteModel;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {
    
    // Buscar por número de identificación
    Optional<ClienteModel> findByNoId(Integer noId);
    
    // Buscar por nombre (búsqueda parcial)
    List<ClienteModel> findByNombreClienteContainingIgnoreCase(String nombre);
    
    // Buscar por género
    List<ClienteModel> findByGeneroClienteIgnoreCase(String genero);
    
    // Verificar si existe un cliente con ese número de identificación
    boolean existsByNoId(Integer noId);
    
    // Consulta personalizada para buscar clientes por rango de edad
    @Query("SELECT c FROM ClienteModel c WHERE c.edadCliente BETWEEN :edadMin AND :edadMax")
    List<ClienteModel> findByEdadBetween(@Param("edadMin") Integer edadMin, @Param("edadMax") Integer edadMax);
    
    // Buscar clientes activos (que tengan citas programadas)
    @Query("SELECT DISTINCT c FROM ClienteModel c JOIN c.citas cita WHERE cita.estadoCita = 'PROGRAMADA'")
    List<ClienteModel> findClientesConCitasProgramadas();
}
