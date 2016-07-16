package org.gestioncatalogo.vistas;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TabHost;

import org.gestioncatalogo.R;
import org.gestioncatalogo.controlador.persistencia.ControladorProducto;
import org.gestioncatalogo.modelo.Marca;
import org.gestioncatalogo.modelo.Marcas;
import org.gestioncatalogo.modelo.Producto;
import org.gestioncatalogo.modelo.Talla;
import org.gestioncatalogo.vistas.listas.MarcaAdapter;
import org.gestioncatalogo.vistas.listas.ProductoAdapter;
import org.gestioncatalogo.vistas.mensajes.Mensajes;
import org.gestioncatalogo.vistas.menu.ControladorMenu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.gestioncatalogo.vistas.Orientacion.isTablet;

public class MainActivity extends AppCompatActivity {
    private EditText nombre, inventario, observaciones, fecha;
    private Button buttonCalendario;
    private Mensajes mensajes;
    private TabHost tabHost;
    private TabHost.TabSpec registroProducto, listaproductos, listamarcas;
    private Date fechaactual;
    private Marcas marcas;
    private Spinner marcasSpinner, tallas;
    private Button registrar, cancelar;
    private ControladorProducto controladorProducto;
    private GridView listaProductos, listaMarcas;
    private ControladorMenu menuControler;
//orientacion

    //orientacion
    private void orientacion() {
        if (isTablet(this)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        orientacion();
        Log.println(Log.ASSERT, "NUEVA COFIG", "NEUVA");
        //here you can handle orientation change
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        orientacion();
        mensajes = new Mensajes(MainActivity.this, this);
        fechaactual = new Date();
        controladorProducto = new ControladorProducto(MainActivity.this);
        marcas = new Marcas(MainActivity.this);
        cargarVistas();
        cargarTabs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        menuControler = new ControladorMenu(menu, MainActivity.this);
        menuControler.ActualizarMenu(getString(R.string.productos));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.Menu_marca:
                mensajes.registrarMarca();
                return true;
            case R.id.Menu_buscarMarca:
                mensajes.BuscarMarcas();
                return true;
            case R.id.Menu_buscarProducto:
               mensajes.BuscarProductos();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void cargarVistas() {
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        buttonCalendario = (Button) findViewById(R.id.buttonCalendario);
        nombre = (EditText) findViewById(R.id.editTextNombre);
        fecha = (EditText) findViewById(R.id.editTextCalendario);
        inventario = (EditText) findViewById(R.id.editTextInvetario);
        observaciones = (EditText) findViewById(R.id.editTextObservaciones);
        registrar = (Button) findViewById(R.id.buttonRegistrrarProducto);
        cancelar = (Button) findViewById(R.id.buttonCancelarProducto);
        listaProductos = (GridView) findViewById(R.id.gridViewCatalago);
        listaMarcas = (GridView) findViewById(R.id.gridViewMarca);

        fecha.setEnabled(false);
        configurarBotones();
        cargarSpinners();
        reiniciarBotones();
    }

    private void cargarTabs() {
        cargarlistadoProductos();
        cargarlistadoMarcas();
        cargarregistroProducto();
        configurarTab();
        cargarProductos(controladorProducto.todosProductos());
    }

    private void cargarlistadoProductos() {
        listaproductos = tabHost.newTabSpec(getString(R.string.productos));
        listaproductos.setContent(R.id.listadoproduto);
        listaproductos.setIndicator(getString(R.string.productos));
        tabHost.addTab(listaproductos);
    }

    private void cargarlistadoMarcas() {
        listamarcas = tabHost.newTabSpec(getString(R.string.marcas));
        listamarcas.setContent(R.id.listadomarca);
        listamarcas.setIndicator(getString(R.string.marcas));
        tabHost.addTab(listamarcas);
    }

    private void cargarregistroProducto() {
        registroProducto = tabHost.newTabSpec(getString(R.string.registro_pro));
        registroProducto.setContent(R.id.reigstro);
        registroProducto.setIndicator(getString(R.string.registrar));
        tabHost.addTab(registroProducto);
    }

    private void cargarSpinners() {
        marcasSpinner = (Spinner) findViewById(R.id.spinnerMarca);
        tallas = (Spinner) findViewById(R.id.spinnerTallla);
        ArrayAdapter<Talla> adapterTalla = new ArrayAdapter<Talla>(this
                , R.layout.item_spinner, R.id.spinnerTarget, Talla.values());

        tallas.setAdapter(adapterTalla);
        spinnerMarca();
    }

    public void cargarProductos(List<Producto> lista) {
        ProductoAdapter adapter = new ProductoAdapter(lista, MainActivity.this, marcas);
        listaProductos.setAdapter(adapter);
    }

    public void cargarMarcas(List<Marca> marcas) {
        MarcaAdapter adapter = new MarcaAdapter(MainActivity.this, marcas);
        listaMarcas.setAdapter(adapter);

    }

    private void spinnerMarca() {
        ArrayAdapter<Marca> adapterMarca = new ArrayAdapter<Marca>(MainActivity.this, R.layout.item_spinner, R.id.spinnerTarget, marcas.getMarcaList());
        marcasSpinner.setAdapter(adapterMarca);
    }

    private void configurarBotones() {
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarBotones();
            }
        });
        buttonCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensajes.Calendario();
            }
        });
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarProducto()) {
                    registrarProducto();
                }
            }
        });
    }

    private void configurarTab() {
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                menuControler.ActualizarMenu(tabId);
                if (registroProducto.getTag().equalsIgnoreCase(tabId)) {
                    validarMarcas();
                }
                if (listaproductos.getTag().equalsIgnoreCase(tabId)) {

                    if (controladorProducto.todosProductos().size() <= 0) {
                        mensajes.ToastMensaje(getString(R.string.no_productos));
                    }
                    cargarProductos(controladorProducto.todosProductos());
                }
                if (listamarcas.getTag().equalsIgnoreCase(tabId)) {
                    validarMarcas();
                    cargarMarcas(marcas.getMarcaList());
                }
            }
        });
    }

    public void reiniciarBotones() {
        validarMarcas();
        fecha.setText(new SimpleDateFormat("yyyy-MM-dd").format(fechaactual));
        nombre.setText("");
        observaciones.setText("");
        inventario.setText("");
    }

    public void validarMarcas() {

        if (marcas.getMarcaList().size() <= 0) {
            mensajes.ToastMensaje(getString(R.string.agregar_marcas));
            mensajes.registrarMarca();
            registrar.setEnabled(false);
        } else {
            spinnerMarca();
            registrar.setEnabled(true);
        }
    }

    private boolean validarProducto() {
        if (nombre.getEditableText().toString().isEmpty() || nombre.getEditableText().length() <= 2) {
            mensajes.ToastMensaje(getString(R.string.name_nulo));
            return false;
        }
        try {
            if (inventario.getEditableText().toString().isEmpty() || inventario.getEditableText().toString().equalsIgnoreCase("") ||
                    Integer.parseInt(inventario.getEditableText().toString()) <= 0) {
                mensajes.ToastMensaje(getString(R.string.no_inventario));
                return false;
            }
        } catch (NumberFormatException ex) {
            mensajes.ToastMensaje(getString(R.string.inventario_numerico));
            return false;
        }
        if (marcasSpinner.getSelectedItem() == null) {
            mensajes.ToastMensaje(getString(R.string.no_marca));
            return false;
        }
        return true;
    }

    private void registrarProducto() {
        Producto registro = new Producto();
        registro.setFecha(fecha.getText().toString());
        registro.setInventario(Integer.parseInt(inventario.getEditableText().toString()));
        registro.setMarca(((Marca) marcasSpinner.getSelectedItem()).getId());
        registro.setNombre(nombre.getEditableText().toString());
        registro.setTalla(((Talla) tallas.getSelectedItem()).talla);
        registro.setObservaciones(observaciones.getEditableText().toString() == null ? "" : observaciones.getEditableText().toString());
        if (controladorProducto.RegistrarProducto(registro)) {
            mensajes.ToastMensaje(getString(R.string.productro_registrado));
            reiniciarBotones();
        } else {
            mensajes.ToastMensaje(getString(R.string.producto_no_registrado));
        }
    }


    //getter
    public EditText getFecha() {
        return fecha;
    }

    public Marcas getMarcas() {
        return marcas;
    }

}
