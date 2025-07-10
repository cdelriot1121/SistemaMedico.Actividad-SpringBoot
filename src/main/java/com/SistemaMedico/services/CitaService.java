package com.SistemaMedico.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SistemaMedico.model.CitaModel;
import com.SistemaMedico.repository.CitaRepository;

@Service
public class CitaService {
    
    @Autowired
    private CitaRepository citaRepository;
    
    public List<CitaModel> obtenerTodasLasCitas() {
        return citaRepository.findAll();
    }
    
    public List<CitaModel> obtenerCitasPorIdentificacion(int noId) {
        return citaRepository.findByNoId(noId);
    }
    
    public CitaModel guardarCita(CitaModel cita) {
        return citaRepository.save(cita);
    }
}
