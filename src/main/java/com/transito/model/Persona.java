package com.transito.model;

/**
 * Persona natural que hereda de Propietario.
 */
public class Persona extends Propietario {
    private String apellido;

    public Persona() {
        super();
    }

    public Persona(Long id, String identificacion, String nombre, String direccion, String apellido) {
        super(id, identificacion, nombre, direccion);
        this.apellido = apellido;
    }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
}
