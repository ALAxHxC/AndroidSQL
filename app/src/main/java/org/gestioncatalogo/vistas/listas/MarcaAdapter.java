package org.gestioncatalogo.vistas.listas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.gestioncatalogo.R;
import org.gestioncatalogo.modelo.Marca;

import java.util.List;

/**
 * Created by daniel on 12/07/2016.
 */
public class MarcaAdapter extends BaseAdapter {
    private Context context;
    private List<Marca> marcas;

    public MarcaAdapter(Context context, List<Marca> marcas) {
        this.context = context;
        this.marcas = marcas;
    }

    @Override
    public int getCount() {
        return marcas.size();
    }

    @Override
    public Object getItem(int position) {
        return marcas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_marca, null);
        Marca actual = marcas.get(position);
        TextView id = (TextView) convertView.findViewById(R.id.textViewIDitemMarca);
        id.setText(actual.getId());
        TextView nombre = (TextView) convertView.findViewById(R.id.textViewNombreItemMarca);
        nombre.setText(actual.getNombre());
        return convertView;
    }
}
