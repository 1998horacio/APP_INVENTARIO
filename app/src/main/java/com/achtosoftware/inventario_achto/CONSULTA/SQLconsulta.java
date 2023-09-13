package com.achtosoftware.inventario_achto.CONSULTA;

public class SQLconsulta {

    private int Codigo, Cantidad;
    private String descripcion;

    public int getCodigoconsulta(){return Codigo;}
    public void setCodigoconsulta( int Codigo){this.Codigo = Codigo;}


    public int getCantidadconsulta(){return Cantidad;}
    public void setCantidadconsulta(int Cantidadconsulta){this.Cantidad = Cantidadconsulta;}


    public String getDescripcionconsulta(){return descripcion;}
    public void setDescripcionconsulta(String descripcionconsulta){this.descripcion = descripcionconsulta;}
}
