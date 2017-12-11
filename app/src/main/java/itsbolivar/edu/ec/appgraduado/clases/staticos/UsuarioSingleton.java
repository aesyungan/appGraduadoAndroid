package itsbolivar.edu.ec.appgraduado.clases.staticos;


import itsbolivar.edu.ec.appgraduado.clases.Aplicar_oferta;
import itsbolivar.edu.ec.appgraduado.clases.Graduado;

/**
 * Created by XL on 3/1/2017.
 */
public class UsuarioSingleton {
    public Aplicar_oferta aplicar_ofertaSelect;

    public Graduado graduado;
    //region singleton
    private static UsuarioSingleton ourInstance = new UsuarioSingleton();

    public static UsuarioSingleton getInstance() {
        if (ourInstance==null){
            ourInstance=new UsuarioSingleton();
        }
        return ourInstance;
    }

    private UsuarioSingleton() {
    }
    //endregion



}
