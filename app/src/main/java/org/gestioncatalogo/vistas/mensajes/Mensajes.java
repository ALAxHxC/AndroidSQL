package org.gestioncatalogo.vistas.mensajes;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.gestioncatalogo.R;
import org.gestioncatalogo.controlador.persistencia.ControladorMarcas;
import org.gestioncatalogo.controlador.persistencia.ControladorProducto;
import org.gestioncatalogo.modelo.Marca;
import org.gestioncatalogo.modelo.Producto;
import org.gestioncatalogo.modelo.Talla;
import org.gestioncatalogo.vistas.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 11/07/2016.
 */
public class Mensajes {
    private Context context;
    private MainActivity mainActivity;
    private ControladorMarcas baseDeDatos;
    private ControladorProducto baseDeDatosProducto;

    public Mensajes(Context context, MainActivity mainActivity) {
        this.context = context;
        this.mainActivity = mainActivity;
        baseDeDatos = new ControladorMarcas(context);
        baseDeDatosProducto = new ControladorProducto(context);
    }

    public void Calendario() {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.fecha_ingreso);
        dialog.setTitle(context.getString(R.string.ingresa_fecha));
        final DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);
        datePicker.setSpinnersShown(true);
        Button botonpositivo = (Button) dialog.findViewById(R.id.buttonAceptar);
        botonpositivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.getFecha().setText(datePicker.getYear() + "-" + datePicker.getMonth() + "-" + datePicker.getDayOfMonth());
                dialog.dismiss();
            }
        });

        Button negativo = (Button) dialog.findViewById(R.id.buttonCancelar);
        ConfigurarCancelar(negativo, dialog);

        dialog.show();


    }

    public void registrarMarca() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.registro_marca);
        final EditText idmarca = (EditText) dialog.findViewById(R.id.editTextIDmarca);
        idmarca.setEnabled(false);
        idmarca.setText(mainActivity.getMarcas().getMarcaList().size() + 1 + "");
        final EditText nombreamarca = (EditText) dialog.findViewById(R.id.editTextNameMarca);
        nombreamarca.setText("");
        nombreamarca.setHint(context.getString(R.string.nombre_marca));
        Button negativo = (Button) dialog.findViewById(R.id.buttonCancelarMarca);
        ConfigurarCancelar(negativo, dialog);
        Button positivo = (Button) dialog.findViewById(R.id.buttonRegistrarMarca);
        botonRegistrarMarca(positivo, dialog, idmarca, nombreamarca);
        dialog.show();

    }


    public void BuscarMarcas() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.buscar_marca);
        final EditText idMarca = (EditText) dialog.findViewById(R.id.editTextBuscarMarcaID);
        final EditText nombreMarca = (EditText) dialog.findViewById(R.id.editTextBuscarNombreMarca);
        Button cancelar = (Button) dialog.findViewById(R.id.buttonBuscarMarcaCancelar);
        ConfigurarCancelar(cancelar, dialog);
        Button buscar = (Button) dialog.findViewById(R.id.buttonBuscarMarcaBuscar);
        botonBuscarMarca(buscar, dialog, idMarca, nombreMarca);
        dialog.show();
    }

    public void BuscarProductos() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.buscar_producto);
        final EditText nombre = (EditText) dialog.findViewById(R.id.editTextBuscarProductoNombre);
        final AutoCompleteTextView marca = (AutoCompleteTextView) dialog.findViewById(R.id.autoCompleteTextViewBuscarProductoTalla);
        configurarAutoCompletarMarca(marca);
        final Spinner talla = (Spinner) dialog.findViewById(R.id.spinnerBuscarProductoTalla);
        configurarSpinnerTallas(talla);
        final Button negativo = (Button) dialog.findViewById(R.id.buttonBuscarProductoCancelar);
        ConfigurarCancelar(negativo, dialog);
        final Button positivo = (Button) dialog.findViewById(R.id.buttonBuscarProductoBuscar);
        botonBuscarProducto(positivo, dialog, nombre, talla, marca);
        dialog.show();

    }


//vistas

    public void configurarAutoCompletarMarca(AutoCompleteTextView marca) {
        ArrayAdapter<Marca> adapterMarca = new ArrayAdapter<Marca>(context, R.layout.item_spinner, R.id.spinnerTarget, mainActivity.getMarcas().getMarcaList());
        marca.setAdapter(adapterMarca);
    }

    public void configurarSpinnerTallas(Spinner talla) {
        List<String> tallas = new ArrayList<>();
        tallas.add("");
        for (Talla t : Talla.values()) {
            tallas.add(t.talla);
        }
        ArrayAdapter<String> adapterTalla = new ArrayAdapter<String>(context
                , R.layout.item_spinner, R.id.spinnerTarget, tallas);
        talla.setAdapter(adapterTalla);

    }

    //botones

    private void botonRegistrarMarca(Button positivo, final Dialog dialog, final EditText idmarca, final EditText nombreamarca) {
        positivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validadRegistroMarca(idmarca, nombreamarca)) {
                    if (baseDeDatos.RegistrarMarca(idmarca.getEditableText().toString(), nombreamarca.getEditableText().toString()) >= 1) {
                        mainActivity.getMarcas().getMarcaList().add(new Marca(idmarca.getEditableText().toString(), nombreamarca.getEditableText().toString()));
                        mainActivity.validarMarcas();
                        dialog.dismiss();
                    }
                }
            }
        });
    }

    private void botonBuscarProducto(Button buscar, final Dialog dialog, final EditText nombre, final Spinner talla, final AutoCompleteTextView marca) {
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validadBusquedaProducto(nombre, talla, marca)) {
                    List<Producto> lista = baseDeDatosProducto.filtrarProductos(nombre.getEditableText().toString(), ((String) talla.getSelectedItem()), marca.getEditableText().toString());
                    if (lista.size() > 0) {
                        mainActivity.cargarProductos(lista);
                        dialog.dismiss();
                    } else {
                        ToastMensaje(context.getString(R.string.sin_resultados));
                    }
                } else {
                    ToastMensaje(context.getString(R.string.campos_busqueda_marca));
                }

            }
        });
    }

    private void botonBuscarMarca(Button buscar, final Dialog dialog, final EditText idMarca, final EditText nombreMarca) {
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarBusquedaMarca(idMarca, nombreMarca)) {
                    List<Marca> marcaList = baseDeDatos.filtrarMarcas(idMarca.getEditableText().toString(), nombreMarca.getEditableText().toString());
                    if (marcaList.size() > 0) {
                        mainActivity.cargarMarcas(marcaList);
                        dialog.dismiss();
                    } else {
                        ToastMensaje(context.getString(R.string.sin_resultados));
                    }
                } else {
                    ToastMensaje(context.getString(R.string.campos_busqueda_marca));
                }
            }
        });
    }


    //validaciones
    private boolean validadRegistroMarca(EditText id, EditText marca) {

        if (marca.getText().toString().isEmpty() || marca.getText().length() <= 3) {
            ToastMensaje(context.getString(R.string.name_nulo));
            return false;
        }
        return true;
    }

    private boolean validarBusquedaMarca(EditText id, EditText nombre) {
        if ((id.getEditableText().toString().isEmpty() || id.getEditableText().toString().equalsIgnoreCase("")) &&
                (nombre.getEditableText().toString().isEmpty() || nombre.getEditableText().toString().equalsIgnoreCase(""))) {
            return false;
        }
        return true;
    }

    private boolean validadBusquedaProducto(EditText nombre, Spinner talla, AutoCompleteTextView marca) {
        String texto = (String) talla.getSelectedItem();
        String marcatext = marca.getEditableText().toString();
        if ((nombre.getEditableText().toString().isEmpty() || nombre.getEditableText().toString().equalsIgnoreCase(""))
                && (texto.isEmpty() || texto.equalsIgnoreCase("")) && (marcatext.isEmpty() || marcatext.equalsIgnoreCase(""))
                ) {
            return false;
        }
        return true;

    }


    public void ToastMensaje(String mensaje) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void ConfigurarCancelar(Button cancelar, final Dialog dialog) {
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
    }

}
