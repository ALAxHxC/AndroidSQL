package org.gestioncatalogo.modelo;

import android.content.Context;

import org.gestioncatalogo.controlador.persistencia.ControladorMarcas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 11/07/2016.
 */
public class Marcas {
    private List<Marca> marcaList;
    private Context context;
    private ControladorMarcas baseDeDatos;

    public Marcas(Context context) {
        marcaList = new ArrayList<>();
        this.context = context;
        baseDeDatos = new ControladorMarcas(context);
        baseDeDatos.consultarTodasMarcas(this);
    }

    public List<Marca> getMarcaList() {
        return marcaList;
    }

    public Marca buscarId(String id) {
        for (Marca marca : marcaList) {
            if (marca.getId().equalsIgnoreCase(id)) {
                return marca;
            }
        }
        return null;

    }

}
