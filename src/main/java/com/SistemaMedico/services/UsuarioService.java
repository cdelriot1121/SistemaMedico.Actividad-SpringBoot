package com.SistemaMedico.services;

import com.SistemaMedico.model.ClienteModel;
import com.SistemaMedico.model.UsuarioModel;
import com.SistemaMedico.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ClienteService clienteService;
    
    // Registrar usuario
    public UsuarioModel registrarUsuario(UsuarioModel usuario) {
        // Validaciones
        if (existeUsername(usuario.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }
        
        if (existeEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        
        if (existePorIdentificacion(usuario.getIdentificacion())) {
            throw new IllegalArgumentException("Ya existe un usuario con esa identificación");
        }
        
        // Establecer valores por defecto
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setActivo(true);
        
        // Si el rol no está definido, establecer como CLIENTE
        if (usuario.getRol() == null || usuario.getRol().trim().isEmpty()) {
            usuario.setRol("CLIENTE");
        }
        
        // En un entorno real, deberías encriptar la contraseña antes de guardarla
        // usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
        UsuarioModel usuarioGuardado = usuarioRepository.save(usuario);
        
        // Si es un cliente, crear también el registro en la tabla de clientes
        if ("CLIENTE".equalsIgnoreCase(usuario.getRol())) {
            crearClienteDesdeUsuario(usuarioGuardado);
        }
        
        return usuarioGuardado;
    }
    
    // Crear cliente desde usuario registrado
    private void crearClienteDesdeUsuario(UsuarioModel usuario) {
        if (!clienteService.existeClientePorIdentificacion(usuario.getIdentificacion())) {
            ClienteModel cliente = new ClienteModel();
            cliente.setNoId(usuario.getIdentificacion());
            cliente.setNombreCliente(usuario.getNombre());
            clienteService.guardarCliente(cliente);
            
            // Vincular el cliente con el usuario
            usuario.setCliente(cliente);
            usuarioRepository.save(usuario);
        }
    }
    
    // Autenticar usuario
    @Transactional(readOnly = true)
    public Optional<UsuarioModel> autenticarUsuario(String username, String password) {
        Optional<UsuarioModel> usuario = usuarioRepository.findActiveUserByUsername(username);
        if (usuario.isPresent() && usuario.get().getPassword().equals(password)) {
            return usuario;
        }
        return Optional.empty();
    }
    
    // Obtener todos los usuarios
    @Transactional(readOnly = true)
    public List<UsuarioModel> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }
    
    // Obtener usuarios activos
    @Transactional(readOnly = true)
    public List<UsuarioModel> obtenerUsuariosActivos() {
        return usuarioRepository.findByActivoTrue();
    }
    
    // Obtener usuario por ID
    @Transactional(readOnly = true)
    public Optional<UsuarioModel> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }
    
    // Obtener usuario por username
    @Transactional(readOnly = true)
    public Optional<UsuarioModel> obtenerUsuarioPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
    
    // Obtener usuario por identificación
    @Transactional(readOnly = true)
    public Optional<UsuarioModel> obtenerUsuarioPorIdentificacion(Integer identificacion) {
        return usuarioRepository.findByIdentificacion(identificacion);
    }
    
    // Método de compatibilidad
    @Transactional(readOnly = true)
    public Optional<UsuarioModel> obtenerUsuarioPorIdentificacion(int identificacion) {
        return usuarioRepository.findByIdentificacion(identificacion);
    }
    
    // Obtener usuarios por rol
    @Transactional(readOnly = true)
    public List<UsuarioModel> obtenerUsuariosPorRol(String rol) {
        return usuarioRepository.findByRolIgnoreCase(rol);
    }
    
    // Verificaciones de existencia
    @Transactional(readOnly = true)
    public boolean existeUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }
    
    @Transactional(readOnly = true)
    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
    
    @Transactional(readOnly = true)
    public boolean existePorIdentificacion(Integer identificacion) {
        return usuarioRepository.existsByIdentificacion(identificacion);
    }
    
    // Actualizar usuario
    public UsuarioModel actualizarUsuario(Long id, UsuarioModel usuarioActualizado) {
        return usuarioRepository.findById(id)
            .map(usuario -> {
                usuario.setNombre(usuarioActualizado.getNombre());
                usuario.setEmail(usuarioActualizado.getEmail());
                usuario.setRol(usuarioActualizado.getRol());
                // No actualizamos username ni identificación para mantener consistencia
                return usuarioRepository.save(usuario);
            })
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }
    
    // Cambiar contraseña
    public void cambiarContrasena(Long id, String nuevaContrasena) {
        UsuarioModel usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // En un entorno real, encriptarías la nueva contraseña
        usuario.setPassword(nuevaContrasena);
        usuarioRepository.save(usuario);
    }
    
    // Desactivar usuario
    public void desactivarUsuario(Long id) {
        UsuarioModel usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }
    
    // Activar usuario
    public void activarUsuario(Long id) {
        UsuarioModel usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        usuario.setActivo(true);
        usuarioRepository.save(usuario);
    }
}
