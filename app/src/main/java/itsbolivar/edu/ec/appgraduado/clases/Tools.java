package itsbolivar.edu.ec.appgraduado.clases;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by XL on 7/12/2017.
 */

public class Tools {

    public Tools() {
    }

    private static Tools ourInstance = new Tools();



    public static Tools getInstance() {
        if (ourInstance == null) {
            ourInstance = new Tools();
        }
        return ourInstance;
    }



    private   boolean compruebaConexion(Context context) {

        boolean connected = false;

        ConnectivityManager connec = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Recupera todas las redes (tanto móviles como wifi)
        NetworkInfo[] redes = connec.getAllNetworkInfo();

        for (int i = 0; i < redes.length; i++) {
            // Si alguna red tiene conexión, se devuelve true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                connected = true;
            }
        }
        return connected;
    }

    //si hay acceso a internet
    private  Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    //ver si esta connectado y si hay internet

    public   boolean estadoConneccion(Context context){

        if (isOnlineNet()&&compruebaConexion(context)){
            return true;
        }else {
            return  false;
        }
    }
}

