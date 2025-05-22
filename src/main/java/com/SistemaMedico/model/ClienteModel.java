package com.SistemaMedico.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clientes")
public class ClienteModel {
    private int no_id;
    private String nombre_cliente;
    private int edad_cliente;
    private int telefono_cliente;
    private String genero_cliente;
    private String direccion_cliente;



    public ClienteModel() {
    }
    public ClienteModel(int no_id, String nombre_cliente, int edad_cliente, int telefono_cliente, String genero_cliente,
            String direccion_cliente) {
        this.no_id = no_id;
        this.nombre_cliente = nombre_cliente;
        this.edad_cliente = edad_cliente;
        this.telefono_cliente = telefono_cliente;
        this.genero_cliente = genero_cliente;
        this.direccion_cliente = direccion_cliente;
    }

    public int getNo_id() {
        return no_id;
    }

    public void setNo_id(int no_id) {
        this.no_id = no_id;
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

    public int getTelefono_cliente() {
        return telefono_cliente;
    }

    public void setTelefono_cliente(int telefono_cliente) {
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
