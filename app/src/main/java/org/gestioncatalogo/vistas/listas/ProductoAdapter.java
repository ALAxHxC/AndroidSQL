package org.gestioncatalogo.vistas.listas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.gestioncatalogo.R;
import org.gestioncatalogo.modelo.Marcas;
import org.gestioncatalogo.modelo.Producto;

import java.util.List;

/**
 * Created by Daniel on 12/07/2016.
 */
public class ProductoAdapter extends BaseAdapter {
    private List<Producto> productoList;
    private Context context;
    private Marcas marcas;

    public ProductoAdapter(List<Producto> productoList, Context context, Marcas marcas) {
        this.productoList = productoList;
        this.context = context;
        this.marcas = marcas;
    }

    @Override
    public int getCount() {
        return productoList.size();
    }

    @Override
    public Object getItem(int position) {
        return productoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Producto producto = (Producto) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_producto, null);
        TextView nombre = (TextView) convertView.findViewById(R.id.itemNombreProducto);
        nombre.setText(producto.getNombre());
        TextView fecha = (TextView) convertView.findViewById(R.id.itemFechaProducto);
        fecha.setText(producto.getFecha());
        TextView inventario = (TextView) convertView.findViewById(R.id.itemInventarioProducto);
        inventario.setText(producto.getInventario() + "");
        TextView talla = (TextView) convertView.findViewById(R.id.itemTallaProducto);
        talla.setText(producto.getTalla());
        TextView marca = (TextView) convertView.findViewById(R.id.itemMarcaProducto);
        marca.setText(marcas.buscarId(producto.getMarca()).toString());
        TextView observaciones = (TextView) convertView.findViewById(R.id.itemProductoObservaciones);
        observaciones.setText(producto.getObservaciones());
        return convertView;
    }
}
