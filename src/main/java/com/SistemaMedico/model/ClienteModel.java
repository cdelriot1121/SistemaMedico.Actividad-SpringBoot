package com.SistemaMedico.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class ClienteModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "numero_identificacion", unique = true, nullable = false)
    private Integer noId; // Número de identificación
    
    @Column(name = "nombre_cliente", nullable = false, length = 100)
    private String nombreCliente;
    
    @Column(name = "edad_cliente", nullable = false)
    private Integer edadCliente;
    
    @Column(name = "telefono_cliente", length = 20)
    private String telefonoCliente;
    
    @Column(name = "genero_cliente", length = 10)
    private String generoCliente;
    
    @Column(name = "direccion_cliente", length = 200)
    private String direccionCliente;
    
    // Relación con las citas (un cliente puede tener múltiples citas)
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CitaModel> citas;
    
    // Constructores
    public ClienteModel() {
    }
    
    public ClienteModel(Integer noId, String nombreCliente, Integer edadCliente, 
                       String telefonoCliente, String generoCliente, String direccionCliente) {
        this.noId = noId;
        this.nombreCliente = nombreCliente;
        this.edadCliente = edadCliente;
        this.telefonoCliente = telefonoCliente;
        this.generoCliente = generoCliente;
        this.direccionCliente = direccionCliente;
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

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    
    // Métodos de compatibilidad con el código existente
    public String getNombre_cliente() {
        return nombreCliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombreCliente = nombre_cliente;
    }

    public Integer getEdadCliente() {
        return edadCliente;
    }

    public void setEdadCliente(Integer edadCliente) {
        this.edadCliente = edadCliente;
    }
    
    // Método de compatibilidad
    public int getEdad_cliente() {
        return edadCliente != null ? edadCliente : 0;
    }

    public void setEdad_cliente(int edad_cliente) {
        this.edadCliente = edad_cliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }
    
    // Método de compatibilidad
    public String getTelefono_cliente() {
        return telefonoCliente;
    }

    public void setTelefono_cliente(String telefono_cliente) {
        this.telefonoCliente = telefono_cliente;
    }

    public String getGeneroCliente() {
        return generoCliente;
    }

    public void setGeneroCliente(String generoCliente) {
        this.generoCliente = generoCliente;
    }
    
    // Método de compatibilidad
    public String getGenero_cliente() {
        return generoCliente;
    }

    public void setGenero_cliente(String genero_cliente) {
        this.generoCliente = genero_cliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }
    
    // Método de compatibilidad
    public String getDireccion_cliente() {
        return direccionCliente;
    }

    public void setDireccion_cliente(String direccion_cliente) {
        this.direccionCliente = direccion_cliente;
    }

    public List<CitaModel> getCitas() {
        return citas;
    }

    public void setCitas(List<CitaModel> citas) {
        this.citas = citas;
    }
}
