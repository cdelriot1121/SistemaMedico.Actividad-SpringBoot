package com.SistemaMedico.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clientes")
public class ClienteModel {
    @Id
    private String id;
    private int noId; // Número de identificación
    private String nombre_cliente;
    private int edad_cliente;
    private String telefono_cliente; // Cambiado de int a String para manejar números de teléfono largos
    private String genero_cliente;
    private String direccion_cliente;



    public ClienteModel() {
    }
    public ClienteModel(int noId, String nombre_cliente, int edad_cliente, String telefono_cliente, String genero_cliente,
            String direccion_cliente) {
        this.noId = noId;
        this.nombre_cliente = nombre_cliente;
        this.edad_cliente = edad_cliente;
        this.telefono_cliente = telefono_cliente;
        this.genero_cliente = genero_cliente;
        this.direccion_cliente = direccion_cliente;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNoId() {
        return noId;
    }

    public void setNoId(int noId) {
        this.noId = noId;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public int getEdad_cliente() {
        return edad_cliente;
    }

    public void setEdad_cliente(int edad_cliente) {
        this.edad_cliente = edad_cliente;
    }

    public String getTelefono_cliente() {
        return telefono_cliente;
    }

    public void setTelefono_cliente(String telefono_cliente) {
        this.telefono_cliente = telefono_cliente;
    }

    public String getGenero_cliente() {
        return genero_cliente;
    }

    public void setGenero_cliente(String genero_cliente) {
        this.genero_cliente = genero_cliente;
    }

    public String getDireccion_cliente() {
        return direccion_cliente;
    }

    public void setDireccion_cliente(String direccion_cliente) {
        this.direccion_cliente = direccion_cliente;
    }
}
