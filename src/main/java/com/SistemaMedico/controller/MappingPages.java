package com.SistemaMedico.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MappingPages {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/AgendarCita")
    public String AgendarCita(){
        return "Agendar-Cita";
    }

    @GetMapping("/Aviso")
    public String Aviso(){
        return "Aviso";
    }
    
}
