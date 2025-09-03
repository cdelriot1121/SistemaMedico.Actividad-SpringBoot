package com.SistemaMedico.repository;

import com.SistemaMedico.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
    
    // Buscar por username
    Optional<UsuarioModel> findByUsername(String username);
    
    // Buscar por identificación
    Optional<UsuarioModel> findByIdentificacion(Integer identificacion);
    
    // Verificar existencia
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByIdentificacion(Integer identificacion);
    
    // Buscar usuarios por rol
    List<UsuarioModel> findByRolIgnoreCase(String rol);
    
    // Buscar usuarios activos
    List<UsuarioModel> findByActivoTrue();
    
    // Buscar usuarios por email
    Optional<UsuarioModel> findByEmail(String email);
    
    // Consulta personalizada para autenticación
    @Query("SELECT u FROM UsuarioModel u WHERE u.username = :username AND u.activo = true")
    Optional<UsuarioModel> findActiveUserByUsername(@Param("username") String username);
    
    // Buscar usuario por identificación y que esté activo
    @Query("SELECT u FROM UsuarioModel u WHERE u.identificacion = :identificacion AND u.activo = true")
    Optional<UsuarioModel> findActiveUserByIdentificacion(@Param("identificacion") Integer identificacion);
}
