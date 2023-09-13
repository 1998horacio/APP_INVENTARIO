package com.achtosoftware.inventario_achto.RECIBO;

import java.sql.Timestamp;

public class SQLreciboDetalle {

    private String pedido, descripcion, observaciones;
    private int codigo, cantidadSolicitada, cantidadFisica, estatus;
    private Timestamp fecha;



    public String getPedido() {
        return pedido;
    }
    public void setPedido(String pedido) {
        this.pedido = pedido;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }



    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }



    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }


    public int getCantidadSolicitada() {
        return cantidadSolicitada;
    }
    public void setCantidadSolicitada(int cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }



    public int getCantidadFisica() {
        return cantidadFisica;
    }
    public void setCantidadFisica(int cantidadFisica) {
        this.cantidadFisica = cantidadFisica;
    }



    public int getEstatus() {
        return estatus;
    }
    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }



    public Timestamp getFecha() {
        return fecha;
    }
    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }


}
