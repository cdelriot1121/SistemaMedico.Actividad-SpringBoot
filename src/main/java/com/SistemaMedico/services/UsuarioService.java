package com.SistemaMedico.services;

import com.SistemaMedico.model.UsuarioModel;
import com.SistemaMedico.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public UsuarioModel registrarUsuario(UsuarioModel usuario) {
        // En un entorno real, deberías encriptar la contraseña antes de guardarla
        return usuarioRepository.save(usuario);
    }
    
    public Optional<UsuarioModel> autenticarUsuario(String username, String password) {
        Optional<UsuarioModel> usuario = usuarioRepository.findByUsername(username);
        if (usuario.isPresent() && usuario.get().getPassword().equals(password)) {
            return usuario;
        }
        return Optional.empty();
    }
    
    public boolean existeUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }
    
    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
    
    public Optional<UsuarioModel> obtenerUsuarioPorIdentificacion(int identificacion) {
        return usuarioRepository.findByIdentificacion(identificacion);
    }
}
