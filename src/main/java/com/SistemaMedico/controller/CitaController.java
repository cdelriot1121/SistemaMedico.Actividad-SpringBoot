package com.SistemaMedico.controller;

import com.SistemaMedico.model.CitaModel;
import com.SistemaMedico.services.CitaService;
import com.SistemaMedico.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CitaController {

    @Autowired
    private CitaService citaService;
    
    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping("/Agendar-Cita")
    public String mostrarAgendarCita(HttpSession session, Model model) {
        // Verificar si el usuario está autenticado
        if (session.getAttribute("usuarioId") == null) {
            return "redirect:/login?redirect=AgendarCita";
        }
        
        Integer identificacion = (Integer) session.getAttribute("identificacion");
        if (identificacion != null) {
            model.addAttribute("identificacion", identificacion);
        }
        
        return "Agendar-Cita";
    }

    @PostMapping("/procesarCita")
    public String procesarCita(
            @RequestParam("nu-identificacion") int nu_id,
            @RequestParam("nombre") String nombre,
            @RequestParam("edad") int edad,
            @RequestParam("tipo-cita") String tipoCita,
            @RequestParam("genero-doctor") String generoPac,
            @RequestParam("fecha-cita") String fechaCita,
            @RequestParam("motivo") String motivo,
            HttpSession session,
            Model model) {

        // Verificar si el usuario está autenticado
        if (session.getAttribute("usuarioId") == null) {
            return "redirect:/login";
        }
        
        try {
            CitaModel cita = new CitaModel(nu_id, nombre, edad, tipoCita, generoPac, fechaCita, motivo);
            CitaModel citaGuardada = citaService.guardarCita(cita);

            model.addAttribute("cita", citaGuardada);
            model.addAttribute("mensaje", "Cita agendada exitosamente");

            return "redirect:/Aviso";
        } catch (Exception e) {
            model.addAttribute("error", "Error al agendar la cita: " + e.getMessage());
            return "Agendar-Cita";
        }
    }

    @GetMapping("/api/Consultar-Citas")
    @ResponseBody
    public List<CitaModel> consultarCitas(@RequestParam("nu-identificacion") int nuId) {
        return citaService.obtenerCitasPorIdentificacion(nuId);
    }
}
