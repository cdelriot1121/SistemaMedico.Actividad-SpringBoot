package com.SistemaMedico.repository;

import com.SistemaMedico.model.UsuarioModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<UsuarioModel, String> {
    Optional<UsuarioModel> findByUsername(String username);
    Optional<UsuarioModel> findByIdentificacion(int identificacion);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
