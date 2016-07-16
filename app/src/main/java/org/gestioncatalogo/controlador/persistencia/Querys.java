package org.gestioncatalogo.controlador.persistencia;

/**
 * Created by daniel on 11/07/2016.
 */
public class Querys {
    public static final String table_marca_create = "CREATE TABLE  IF NOT EXISTS marca( " +
            "marca_id INTEGER PRIMARY KEY, " +
            "marca_nombre TEXT NOT NULL " +
            ");";
    public static final String table_producto_create = "CREATE TABLE IF NOT EXISTS producto ( " +
            "producto_nombre TEXT NOT NULL , " +
            "producto_talla TEXT NOT NULL, " +
            "producto_marca INTEGER NOT NULL, " +
            "producto_inventario INTEGER NOT NULL, " +
            "producto_fecha TEXT NOT NULL," +
            "producto_observaciones TEXT NOT NULL, " +
            " FOREIGN KEY (producto_marca) REFERENCES marca(marca_id), " +
            " PRIMARY KEY (producto_nombre, producto_talla,producto_marca) " +
            ")";
    public static final String delete_producto_table = "DROP TABLE producto;";
    public static final String delete_marca_table = "DROP TABLE marca;";


    public static final String table_marca_name = "marca";
    public static final String marca_marcaid = "marca_id";
    public static final String marca_nombre = "marca_nombre";

    public static final String table_producto_name = "producto";
    public static final String producto_nombre = "producto_nombre";
    public static final String producto_talla = "producto_talla";
    public static final String producto_marca = "producto_marca";
    public static final String producto_fecha = "producto_fecha";
    public static final String producto_inventario = "producto_inventario";
    public static final String producto_observaciones = "producto_observaciones";
}
