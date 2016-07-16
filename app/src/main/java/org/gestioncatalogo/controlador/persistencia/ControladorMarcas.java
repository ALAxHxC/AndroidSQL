package org.gestioncatalogo.controlador.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.gestioncatalogo.R;
import org.gestioncatalogo.modelo.Marca;
import org.gestioncatalogo.modelo.Marcas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 11/07/2016.
 */
public class ControladorMarcas {
    private Context context;
    private BaseDeDatos baseDeDatos;

    public ControladorMarcas(Context context) {
        this.context = context;
        baseDeDatos = new BaseDeDatos(context, context.getString(R.string.catalogo), null, 1);
    }

    public long RegistrarMarca(String id, String name) {
        SQLiteDatabase sqLiteDatabase = baseDeDatos.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(Querys.marca_marcaid, id);
        valores.put(Querys.marca_nombre, name);
        try {
            long rt = sqLiteDatabase.insert(Querys.table_marca_name, null, valores);
            return rt;
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.println(Log.ASSERT, "SQL", ex.toString());
            return -1;
        } finally {
            sqLiteDatabase.close();
        }
    }

    public void MostrarMarcas(Cursor fila, Marcas marcas) {
        if (fila.moveToFirst()) {
            do {
                marcas.getMarcaList().add(new Marca(fila.getString(0), fila.getString(1)));
                Log.println(Log.ASSERT, "SQL", "Cargando");
            } while (fila.moveToNext());
        }
        fila.close();

    }

    public void consultarTodasMarcas(Marcas marcas) {

            SQLiteDatabase sql = baseDeDatos.getWritableDatabase();
            Cursor cursor = sql.rawQuery(" SELECT * FROM " + Querys.table_marca_name, null);
            MostrarMarcas(cursor, marcas);

        }

    public List<Marca> filtrarMarcas(String id, String nombre) {
        SQLiteDatabase sql = baseDeDatos.getWritableDatabase();
        try {
            if (id == null || id.isEmpty() || id.equalsIgnoreCase("")) {
                return filtrarMarcaNombre(nombre, sql);
            }
            if (nombre == null || nombre.isEmpty() || nombre.equalsIgnoreCase("")) {
                return filtrarMarcaId(id, sql);
            }
            return filtrarMarcasCampos(id, nombre, sql);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.println(Log.ASSERT, "SQL", ex.toString());
            return null;
        } finally {
            sql.close();
        }

    }

    private List<Marca> filtrarMarcasCampos(String id, String nombre, SQLiteDatabase sql) {

        Cursor cursor = sql.rawQuery(" SELECT * FROM " + Querys.table_marca_name + "" +
                " WHERE " + Querys.marca_marcaid + " = " + id + " " +
                "OR " + Querys.marca_nombre + " LIKE '%" + nombre + "%' ;", null);
        return listaMarcas(cursor);
    }

    private List<Marca> filtrarMarcaId(String id, SQLiteDatabase sql) {

        Cursor cursor = sql.rawQuery(" SELECT * FROM " + Querys.table_marca_name + "" +
                " WHERE " + Querys.marca_marcaid + " = " + id + " ;", null);
        return listaMarcas(cursor);
    }

    private List<Marca> filtrarMarcaNombre(String nombre, SQLiteDatabase sql) {
        Cursor cursor = sql.rawQuery(" SELECT * FROM " + Querys.table_marca_name + "" +
                " WHERE " + Querys.marca_nombre + " LIKE '%" + nombre + "%';", null);
        return listaMarcas(cursor);

    }

    private List<Marca> listaMarcas(Cursor fila) {
        List<Marca> lista = new ArrayList<>();
        if (fila.moveToFirst()) {
            do {
                lista.add(new Marca(fila.getString(0), fila.getString(1)));
                Log.println(Log.ASSERT, "SQL", "Cargando");
            } while (fila.moveToNext());
        }
        fila.close();
        return lista;
    }

}
