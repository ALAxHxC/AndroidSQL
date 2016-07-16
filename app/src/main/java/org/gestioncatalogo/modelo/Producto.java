package org.gestioncatalogo.modelo;

/**
 * Created by Daniel on 11/07/2016.
 */
public class Producto {
    String nombre, talla, marca, observaciones, fecha;
    int inventario;

    public Producto() {
    }

    public Producto(String nombre, String talla, String marca, String observaciones, String fecha, int inventario) {
        this.nombre = nombre;
        this.talla = talla;
        this.marca = marca;
        this.observaciones = observaciones;
        this.fecha = fecha;
        this.inventario = inventario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getInventario() {
        return inventario;
    }

    public void setInventario(int inventario) {
        this.inventario = inventario;
    }
}
