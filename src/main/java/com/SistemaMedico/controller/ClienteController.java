package com.SistemaMedico.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.SistemaMedico.model.ClienteModel;
import com.SistemaMedico.model.UsuarioModel;
import com.SistemaMedico.services.ClienteService;
import com.SistemaMedico.services.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(required = false) String redirect, Model model) {
        if (redirect != null) {
            model.addAttribute("redirect", redirect);
        }
        return "login";
    }
    
    @GetMapping("/register")
    public String mostrarRegistro() {
        return "register";
    }
    
    @PostMapping("/procesarLogin")
    public String procesarLogin(
            @RequestParam String username, 
            @RequestParam String password,
            @RequestParam(required = false) String redirect,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Optional<UsuarioModel> usuarioOpt = usuarioService.autenticarUsuario(username, password);
        
        if (usuarioOpt.isPresent()) {
            UsuarioModel usuario = usuarioOpt.get();
            session.setAttribute("usuarioId", usuario.getId());
            session.setAttribute("username", usuario.getUsername());
            session.setAttribute("nombre", usuario.getNombre());
            session.setAttribute("identificacion", usuario.getIdentificacion());
            
            if (redirect != null && !redirect.isEmpty()) {
                return "redirect:/" + redirect;
            }
            
            return "redirect:/";
        }
        
        redirectAttributes.addFlashAttribute("error", "Usuario o contraseña incorrectos");
        return "redirect:/login";
    }
    
    @PostMapping("/procesarRegistro")
    public String procesarRegistro(
            @RequestParam String nombre,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam int identificacion,
            @RequestParam String direccion,
            @RequestParam int edad,
            @RequestParam String telefono,
            @RequestParam String genero,
            RedirectAttributes redirectAttributes) {
        
        try {
            // Verificar si el usuario ya existe
            if (usuarioService.existeUsername(username)) {
                redirectAttributes.addFlashAttribute("error", "El nombre de usuario ya está en uso");
                return "redirect:/register";
            }
            
            if (usuarioService.existeEmail(email)) {
                redirectAttributes.addFlashAttribute("error", "El correo electrónico ya está registrado");
                return "redirect:/register";
            }
            
            // Verificar si ya existe un cliente con esa identificación
            if (clienteService.existeClientePorIdentificacion(identificacion)) {
                redirectAttributes.addFlashAttribute("error", "Ya existe un registro con esa identificación");
                return "redirect:/register";
            }
            
            // Crear usuario (esto automáticamente creará el cliente también)
            UsuarioModel usuario = new UsuarioModel(username, password, nombre, email, "CLIENTE", identificacion);
            usuarioService.registrarUsuario(usuario);
            
            // Actualizar información adicional del cliente
            Optional<ClienteModel> clienteOpt = clienteService.obtenerClientePorIdentificacion(identificacion);
            if (clienteOpt.isPresent()) {
                ClienteModel cliente = clienteOpt.get();
                cliente.setDireccionCliente(direccion);
                cliente.setEdadCliente(edad);
                cliente.setTelefonoCliente(telefono);
                cliente.setGeneroCliente(genero);
                clienteService.guardarCliente(cliente);
            }
            
            redirectAttributes.addFlashAttribute("success", "Registro exitoso, ahora puedes iniciar sesión");
            return "redirect:/login";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error en el registro: " + e.getMessage());
            return "redirect:/register";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
