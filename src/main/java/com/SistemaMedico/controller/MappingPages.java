package com.SistemaMedico.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;


@Controller
public class MappingPages {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/AgendarCita")
    public String AgendarCita(HttpSession session){
        // Verificar si el usuario está autenticado
        if (session.getAttribute("usuarioId") == null) {
            return "redirect:/login?redirect=AgendarCita";
        }
        return "Agendar-Cita";
    }

    @GetMapping("/Aviso")
    public String Aviso(){
        return "Aviso";
    }

    @GetMapping("/Consultar-Citas")
    public String ConsultarCitas(HttpSession session, org.springframework.ui.Model model){
        // Verificar si el usuario está autenticado
        if (session.getAttribute("usuarioId") == null) {
            return "redirect:/login?redirect=Consultar-Citas";
        }
        
        // Pasar la identificación del usuario a la vista
        if (session.getAttribute("identificacion") != null) {
            model.addAttribute("identificacion", session.getAttribute("identificacion"));
        }
        
        return "Consultar-cita";
    }
    
    // Eliminando el mapeo duplicado para login y register, ya están en ClienteController
}
