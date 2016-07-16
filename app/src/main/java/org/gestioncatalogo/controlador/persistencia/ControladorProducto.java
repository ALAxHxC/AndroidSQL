package org.gestioncatalogo.controlador.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.gestioncatalogo.R;
import org.gestioncatalogo.modelo.Producto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 11/07/2016.
 */
public class ControladorProducto {
    private Context context;
    private BaseDeDatos baseDeDatos;

    public ControladorProducto(Context context) {
        this.context = context;
        baseDeDatos = new BaseDeDatos(context, context.getString(R.string.catalogo), null, 1);
    }

    public boolean RegistrarProducto(Producto producto) {
        SQLiteDatabase sql = baseDeDatos.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(Querys.producto_nombre, producto.getNombre());
        valores.put(Querys.producto_fecha, producto.getFecha());
        valores.put(Querys.producto_inventario, producto.getInventario());
        valores.put(Querys.producto_talla, producto.getTalla());
        valores.put(Querys.producto_marca, producto.getMarca());
        valores.put(Querys.producto_observaciones, producto.getObservaciones());
        try {
            long ret = sql.insert(Querys.table_producto_name, null, valores);
            if (ret > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            Log.println(Log.ASSERT, "SQL", ex.toString());
            ex.printStackTrace();
            return false;
        } finally {
            sql.close();
        }
    }

    public List<Producto> todosProductos() {
        SQLiteDatabase sql = baseDeDatos.getWritableDatabase();
        Cursor cursor = sql.rawQuery("SELECT * FROM " + Querys.table_producto_name, null);
        return listarProducto(cursor);
    }

    public List<Producto> filtrarProductos(String nombre, String talla, String marca) {
        SQLiteDatabase sql = baseDeDatos.getWritableDatabase();
        try {

            Cursor fila = sql.rawQuery(consulta(nombre, talla, marca), null);
            return listarProducto(fila);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.println(Log.ASSERT, "sql", ex.toString());
            return null;
        } finally {
            sql.close();
        }
    }

    public String consulta(String nombre, String talla, String marca) {
        int contador = 0;
        StringBuilder consulta = new StringBuilder("SELECT * FROM " + Querys.table_producto_name + " WHERE ");
        if (!(nombre.isEmpty() || nombre.equalsIgnoreCase(""))) {
            consulta.append(" " + Querys.producto_nombre + " like '%" + nombre + "%' ");
            contador++;
        }
        if (!(talla.isEmpty() || talla.equalsIgnoreCase(""))) {
            if (contador >= 1) {
                consulta.append(" OR ");
            }
            consulta.append(Querys.producto_talla + " like '%" + talla + "%'");
            contador++;
        }
        if (!(marca.isEmpty() || marca.equalsIgnoreCase(""))) {
            if (contador >= 1) {
                consulta.append(" OR ");
            }
            consulta.append(Querys.producto_marca + " IN" + " " +
                    "(SELECT " + Querys.marca_marcaid + " FROM " + Querys.table_marca_name + " WHERE " + Querys.marca_nombre + " like '%" + marca + "%' )");
            contador++;
        }
        return consulta.toString();
    }

    public List<Producto> listarProducto(Cursor fila) {
        List<Producto> lista = new ArrayList<>();
        if (fila.moveToFirst()) {
            do {
                Producto producto = new Producto();
                producto.setNombre(fila.getString(0));
                producto.setTalla(fila.getString(1));
                producto.setMarca(fila.getString(2));
                producto.setInventario(Integer.parseInt(fila.getString(3)));
                producto.setFecha(fila.getString(4));
                producto.setObservaciones(fila.getString(5));
                lista.add(producto);
            } while (fila.moveToNext());

        }
        return lista;
    }
}
