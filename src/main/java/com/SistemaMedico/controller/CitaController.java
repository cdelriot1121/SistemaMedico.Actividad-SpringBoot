package com.SistemaMedico.controller;

import com.SistemaMedico.model.CitaModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CitaController {

    // Simulación de una lista para almacenar citas
    private List<CitaModel> citas = new ArrayList<>();

    @RequestMapping("/Agendar-Cita")
    public String mostrarAgendarCita() {
        return "Agendar-Cita";
    }

    @PostMapping("/procesarCita")
    public String procesarCita(
            @RequestParam("nombre") String nombre,
            @RequestParam("edad") int edad,
            @RequestParam("tipo-cita") String tipoCita,
            @RequestParam("genero-doctor") String generoPac,
            @RequestParam("fecha-cita") String fechaCita,
            @RequestParam("motivo") String motivo,
            Model model) {

        // Crear una nueva instancia de CitaModel
        CitaModel cita = new CitaModel(nombre, edad, tipoCita, generoPac, fechaCita, motivo);

        // Añadir la cita a la lista simulada
        citas.add(cita);

        // Añadir la cita al modelo para mostrarla en la vista de confirmación
        model.addAttribute("cita", cita);

        // Redirigir a una página de confirmación o éxito
        return "Aviso"; // Asegúrate de tener una plantilla Aviso.html
    }

    // Nuevo mapeo para consultar citas en formato JSON
    @GetMapping("/ConsultarCitas")
    @ResponseBody
    public List<CitaModel> consultarCitas() {
        // Retornar todas las citas en formato JSON
        return citas;
    }
}
