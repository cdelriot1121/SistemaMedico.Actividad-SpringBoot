package com.SistemaMedico.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SistemaMedico.model.ClienteModel;
import com.SistemaMedico.repository.ClienteRepository;

@Service
@Transactional
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    // Obtener todos los clientes
    @Transactional(readOnly = true)
    public List<ClienteModel> obtenerTodosLosClientes() {
        return clienteRepository.findAll();
    }
    
    // Obtener cliente por ID
    @Transactional(readOnly = true)
    public Optional<ClienteModel> obtenerClientePorId(Long id) {
        return clienteRepository.findById(id);
    }
    
    // Obtener cliente por número de identificación
    @Transactional(readOnly = true)
    public Optional<ClienteModel> obtenerClientePorIdentificacion(Integer noId) {
        return clienteRepository.findByNoId(noId);
    }
    
    // Método de compatibilidad
    @Transactional(readOnly = true)
    public Optional<ClienteModel> obtenerClientePorIdentificacion(int noId) {
        return clienteRepository.findByNoId(noId);
    }
    
    // Guardar cliente
    public ClienteModel guardarCliente(ClienteModel cliente) {
        // Validaciones básicas
        if (cliente.getNoId() == null || cliente.getNombreCliente() == null || cliente.getNombreCliente().trim().isEmpty()) {
            throw new IllegalArgumentException("Los datos del cliente son obligatorios");
        }
        
        // Verificar si ya existe un cliente con esa identificación (para nuevos registros)
        if (cliente.getId() == null && existeClientePorIdentificacion(cliente.getNoId())) {
            throw new IllegalArgumentException("Ya existe un cliente con esa identificación");
        }
        
        return clienteRepository.save(cliente);
    }
    
    // Actualizar cliente
    public ClienteModel actualizarCliente(Long id, ClienteModel clienteActualizado) {
        return clienteRepository.findById(id)
            .map(cliente -> {
                cliente.setNombreCliente(clienteActualizado.getNombreCliente());
                cliente.setEdadCliente(clienteActualizado.getEdadCliente());
                cliente.setTelefonoCliente(clienteActualizado.getTelefonoCliente());
                cliente.setGeneroCliente(clienteActualizado.getGeneroCliente());
                cliente.setDireccionCliente(clienteActualizado.getDireccionCliente());
                return clienteRepository.save(cliente);
            })
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
    }
    
    // Eliminar cliente
    public void eliminarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado con ID: " + id);
        }
        clienteRepository.deleteById(id);
    }
    
    // Verificar si existe cliente por identificación
    @Transactional(readOnly = true)
    public boolean existeClientePorIdentificacion(Integer noId) {
        return clienteRepository.existsByNoId(noId);
    }
    
    // Método de compatibilidad
    @Transactional(readOnly = true)
    public boolean existeClientePorIdentificacion(int noId) {
        return clienteRepository.existsByNoId(noId);
    }
    
    // Buscar clientes por nombre
    @Transactional(readOnly = true)
    public List<ClienteModel> buscarClientesPorNombre(String nombre) {
        return clienteRepository.findByNombreClienteContainingIgnoreCase(nombre);
    }
    
    // Buscar clientes por género
    @Transactional(readOnly = true)
    public List<ClienteModel> buscarClientesPorGenero(String genero) {
        return clienteRepository.findByGeneroClienteIgnoreCase(genero);
    }
    
    // Buscar clientes por rango de edad
    @Transactional(readOnly = true)
    public List<ClienteModel> buscarClientesPorRangoEdad(Integer edadMin, Integer edadMax) {
        return clienteRepository.findByEdadBetween(edadMin, edadMax);
    }
    
    // Obtener clientes con citas programadas
    @Transactional(readOnly = true)
    public List<ClienteModel> obtenerClientesConCitasProgramadas() {
        return clienteRepository.findClientesConCitasProgramadas();
    }
}
