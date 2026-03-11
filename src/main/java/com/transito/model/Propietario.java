package com.transito.model;

import java.io.Serializable;

/**
 * Clase base de propietarios.
 */
public class Propietario implements Serializable {
    private Long id;
    private String identificacion;
    private String nombre;
    private String direccion;
    private int puntosLicencia = 20;

    public Propietario() {
    }

    public Propietario(Long id, String identificacion, String nombre, String direccion) {
        this.id = id;
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.direccion = direccion;
        this.puntosLicencia = 20;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public int getPuntosLicencia() { return puntosLicencia; }
    public void setPuntosLicencia(int puntosLicencia) { this.puntosLicencia = puntosLicencia; }
}
