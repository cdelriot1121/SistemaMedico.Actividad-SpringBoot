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
import java.util.stream.Collectors;

@Controller
public class CitaController {

    private List<CitaModel> citas = new ArrayList<>();

    @RequestMapping("/Agendar-Cita")
    public String mostrarAgendarCita() {
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
            Model model) {

        
        CitaModel cita = new CitaModel(nu_id, nombre, edad, tipoCita, generoPac, fechaCita, motivo);

        
        citas.add(cita);

        model.addAttribute("cita", cita);

        return "redirect:/Aviso";
    }

    @GetMapping("/Consultar")
    @ResponseBody
    public List<CitaModel> consultarCitas() {
        return citas;
    }

    @GetMapping("/api/Consultar-Citas")
    @ResponseBody
    public List<CitaModel> consultarCitas(@RequestParam("nu-identificacion") int nuId) {
        return citas.stream()
                .filter(cita -> cita.getNo_id() == nuId) 
                .collect(Collectors.toList());
    }


}
