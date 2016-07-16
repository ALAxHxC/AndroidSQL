package org.gestioncatalogo.modelo;

/**
 * Created by Daniel on 11/07/2016.
 */
public enum Talla {
    L("L"), S("S"), M("M");

    Talla(String numero) {
        talla = numero;
    }

    public String talla;

}
