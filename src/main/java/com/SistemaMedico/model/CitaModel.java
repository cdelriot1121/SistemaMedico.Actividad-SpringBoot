package com.SistemaMedico.model;

public class CitaModel {

    private String nombre_paciente;
    private int edad_paciente;
    private String tipo_cita;
    private String genero_pac;
    private String fecha_cita;
    private String motivo_cita;
    
    
    public CitaModel() {
    }
    public CitaModel(String nombre_paciente, int edad_paciente, String tipo_cita, String genero_pac, String fecha_cita,
            String motivo_cita) {
        this.nombre_paciente = nombre_paciente;
        this.edad_paciente = edad_paciente;
        this.tipo_cita = tipo_cita;
        this.genero_pac = genero_pac;
        this.fecha_cita = fecha_cita;
        this.motivo_cita = motivo_cita;
    }
    public String getNombre_paciente() {
        return nombre_paciente;
    }
    public void setNombre_paciente(String nombre_paciente) {
        this.nombre_paciente = nombre_paciente;
    }
    public int getEdad_paciente() {
        return edad_paciente;
    }
    public void setEdad_paciente(int edad_paciente) {
        this.edad_paciente = edad_paciente;
    }
    public String getTipo_cita() {
        return tipo_cita;
    }
    public void setTipo_cita(String tipo_cita) {
        this.tipo_cita = tipo_cita;
    }
    public String getGenero_pac() {
        return genero_pac;
    }
    public void setGenero_pac(String genero_pac) {
        this.genero_pac = genero_pac;
    }
    public String getFecha_cita() {
        return fecha_cita;
    }
    public void setFecha_cita(String fecha_cita) {
        this.fecha_cita = fecha_cita;
    }
    public String getMotivo_cita() {
        return motivo_cita;
    }
    public void setMotivo_cita(String motivo_cita) {
        this.motivo_cita = motivo_cita;
    }
    
    

    
}