package com.SistemaMedico.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.SistemaMedico.model.ClienteModel;

@Repository
public interface ClienteRepository extends MongoRepository<ClienteModel, String> {
    Optional<ClienteModel> findByNoId(int noId);
}
