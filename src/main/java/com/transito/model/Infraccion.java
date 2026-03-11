package com.transito.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Infraccion implements Serializable {
    private Long id;
    private LocalDate fecha;
    private String descripcion;
    private double valor;
    private Long vehiculoId;
    private TipoDeteccion tipoDeteccion;
    private TipoSeveridad severidad;
    private boolean pagada;

    public Infraccion() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
    public Long getVehiculoId() { return vehiculoId; }
    public void setVehiculoId(Long vehiculoId) { this.vehiculoId = vehiculoId; }
    public TipoDeteccion getTipoDeteccion() { return tipoDeteccion; }
    public void setTipoDeteccion(TipoDeteccion tipoDeteccion) { this.tipoDeteccion = tipoDeteccion; }
    public TipoSeveridad getSeveridad() { return severidad; }
    public void setSeveridad(TipoSeveridad severidad) { this.severidad = severidad; }
    public boolean isPagada() { return pagada; }
    public void setPagada(boolean pagada) { this.pagada = pagada; }
}
