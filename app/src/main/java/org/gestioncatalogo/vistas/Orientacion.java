package org.gestioncatalogo.vistas;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by daniel on 12/07/2016.
 */
public class Orientacion {

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
