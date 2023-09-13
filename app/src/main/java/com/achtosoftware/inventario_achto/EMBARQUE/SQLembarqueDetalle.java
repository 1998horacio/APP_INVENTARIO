package com.achtosoftware.inventario_achto.EMBARQUE;

import java.sql.Timestamp;

public class SQLembarqueDetalle {
    private String pedido, descripcion, observaciones;
    private int codigo, cantidadSolicitada, cantidadEmbarque, cantidadfisica, estatus;
    private Timestamp fecha;



    // ---------------------- pedido ----------------------------
    public String getPedido() {
        return pedido;
    }
    public void setPedido(String pedido) {
        this.pedido = pedido;
    }



    // ---------------------- codigo ----------------------------
    public int getCodigo() {return codigo;}
    public void setCodigo(int codigo) {this.codigo = codigo;}



    // ---------------------- descripcion ------------------------
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}



    // ------------------ cantidad solicitada ---------------------
    public int getCantidadSolicitada() {
        return cantidadSolicitada;
    }
    public void setCantidadSolicitada(int cantidadSolicitada) {this.cantidadSolicitada = cantidadSolicitada;}



    // ------------------ cantidad embarcada ---------------------
    public int getCantidadEmbarque() {
        return cantidadEmbarque;
    }
    public void setCantidadembarque(int cantidadembarque) {this.cantidadEmbarque = cantidadembarque;}



    // ----------------- cantidad fisica -------------------------
    public int getCantidadFisica(){ return cantidadfisica;}
    public void setCantidadfisica( int cantidadfisica) {this.cantidadfisica = cantidadfisica;}



    // ------------------------- estatus -------------------------
    public int getEstatus() {
        return estatus;
    }
    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }



    // -------------------------- Fecha  -------------------------
    public Timestamp getFecha() {
        return fecha;
    }
    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }



    // ---------------------- observaciones ------------------------
    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
