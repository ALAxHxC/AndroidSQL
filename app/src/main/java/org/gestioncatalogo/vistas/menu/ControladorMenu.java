package org.gestioncatalogo.vistas.menu;

import android.content.Context;
import android.util.Log;
import android.view.Menu;

import org.gestioncatalogo.R;

/**
 * Created by daniel on 12/07/2016.
 */
public class ControladorMenu {
    private Menu menu;
    private Context context;
    private final String producto, listadomarca, listadoproducto;

    public ControladorMenu(Menu menu, Context context) {
        this.menu = menu;
        this.context = context;
        producto = context.getString(R.string.registro_pro);
        listadoproducto = context.getString(R.string.productos);
        listadomarca = context.getString(R.string.marcas);


    }

    public void ActualizarMenu(String tag) {
        Log.println(Log.ASSERT, "menu", "revisando");
        if (producto.equalsIgnoreCase(tag)) {
            menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(false);
            menu.getItem(2).setVisible(false);
            return;
        }
        if (listadoproducto.equalsIgnoreCase(tag)) {
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(true);
            menu.getItem(2).setVisible(false);
            return;
        }
        if (listadomarca.equalsIgnoreCase(tag)) {
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(false);
            menu.getItem(2).setVisible(true);
            return;
        }
    }

}
