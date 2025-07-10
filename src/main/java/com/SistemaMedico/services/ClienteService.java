package com.SistemaMedico.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SistemaMedico.model.ClienteModel;
import com.SistemaMedico.repository.ClienteRepository;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    public List<ClienteModel> obtenerTodosLosClientes() {
        return clienteRepository.findAll();
    }
    
    public Optional<ClienteModel> obtenerClientePorIdentificacion(int noId) {
        return clienteRepository.findByNoId(noId);
    }
    
    public ClienteModel guardarCliente(ClienteModel cliente) {
        return clienteRepository.save(cliente);
    }
    
    public boolean existeClientePorIdentificacion(int noId) {
        return clienteRepository.findByNoId(noId).isPresent();
    }
}
