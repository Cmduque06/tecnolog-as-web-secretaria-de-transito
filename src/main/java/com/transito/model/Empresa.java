package com.transito.model;

/**
 * Empresa que hereda de Propietario.
 */
public class Empresa extends Propietario {
    private String razonSocial;

    public Empresa() {
        super();
    }

    public Empresa(Long id, String identificacion, String nombre, String direccion, String razonSocial) {
        super(id, identificacion, nombre, direccion);
        this.razonSocial = razonSocial;
    }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
}
