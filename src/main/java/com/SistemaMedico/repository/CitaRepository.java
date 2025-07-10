package com.SistemaMedico.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.SistemaMedico.model.CitaModel;

@Repository
public interface CitaRepository extends MongoRepository<CitaModel, String> {
    List<CitaModel> findByNoId(int noId);
}
