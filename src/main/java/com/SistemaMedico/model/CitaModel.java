package com.SistemaMedico.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "citas")
public class CitaModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "numero_identificacion", nullable = false)
    private Integer noId; // Número de identificación del paciente
    
    @Column(name = "nombre_paciente", nullable = false, length = 100)
    private String nombrePaciente;
    
    @Column(name = "edad_paciente", nullable = false)
    private Integer edadPaciente;
    
    @Column(name = "tipo_cita", nullable = false, length = 50)
    private String tipoCita;
    
    @Column(name = "genero_paciente", length = 10)
    private String generoPaciente;
    
    @Column(name = "fecha_cita", nullable = false)
    private String fechaCita; // Mantenemos como String por ahora para compatibilidad
    
    @Column(name = "motivo_cita", length = 500)
    private String motivoCita;
    
    @Column(name = "estado_cita", length = 20)
    private String estadoCita = "PROGRAMADA"; // PROGRAMADA, COMPLETADA, CANCELADA
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    // Relación con Cliente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private ClienteModel cliente;
    
    public CitaModel() {
        this.fechaCreacion = LocalDateTime.now();
        this.estadoCita = "PROGRAMADA";
    }
    
    public CitaModel(Integer noId, String nombrePaciente, Integer edadPaciente, 
                    String tipoCita, String generoPaciente, String fechaCita, String motivoCita) {
        this();
        this.noId = noId;
        this.nombrePaciente = nombrePaciente;
        this.edadPaciente = edadPaciente;
        this.tipoCita = tipoCita;
        this.generoPaciente = generoPaciente;
        this.fechaCita = fechaCita;
        this.motivoCita = motivoCita;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNoId() {
        return noId;
    }

    public void setNoId(Integer noId) {
        this.noId = noId;
    }
    
    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }
    
    // Métodos de compatibilidad
    public String getNombre_paciente() {
        return nombrePaciente;
    }
    
    public void setNombre_paciente(String nombre_paciente) {
        this.nombrePaciente = nombre_paciente;
    }
    
    public Integer getEdadPaciente() {
        return edadPaciente;
    }

    public void setEdadPaciente(Integer edadPaciente) {
        this.edadPaciente = edadPaciente;
    }
    
    public int getEdad_paciente() {
        return edadPaciente != null ? edadPaciente : 0;
    }
    
    public void setEdad_paciente(int edad_paciente) {
        this.edadPaciente = edad_paciente;
    }
    
    public String getTipoCita() {
        return tipoCita;
    }

    public void setTipoCita(String tipoCita) {
        this.tipoCita = tipoCita;
    }
    
    public String getTipo_cita() {
        return tipoCita;
    }
    
    public void setTipo_cita(String tipo_cita) {
        this.tipoCita = tipo_cita;
    }
    
    public String getGeneroPaciente() {
        return generoPaciente;
    }

    public void setGeneroPaciente(String generoPaciente) {
        this.generoPaciente = generoPaciente;
    }
    
    public String getGenero_pac() {
        return generoPaciente;
    }
    
    public void setGenero_pac(String genero_pac) {
        this.generoPaciente = genero_pac;
    }
    
    public String getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(String fechaCita) {
        this.fechaCita = fechaCita;
    }
    
    public String getFecha_cita() {
        return fechaCita;
    }
    
    public void setFecha_cita(String fecha_cita) {
        this.fechaCita = fecha_cita;
    }
    
    public String getMotivoCita() {
        return motivoCita;
    }

    public void setMotivoCita(String motivoCita) {
        this.motivoCita = motivoCita;
    }
    
    public String getMotivo_cita() {
        return motivoCita;
    }
    
    public void setMotivo_cita(String motivo_cita) {
        this.motivoCita = motivo_cita;
    }

    public String getEstadoCita() {
        return estadoCita;
    }

    public void setEstadoCita(String estadoCita) {
        this.estadoCita = estadoCita;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public ClienteModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteModel cliente) {
        this.cliente = cliente;
    }
}