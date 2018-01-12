package itsbolivar.edu.ec.appgraduado.LN;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import itsbolivar.edu.ec.appgraduado.clases.Empresa;
import itsbolivar.edu.ec.appgraduado.clases.Estado_civil;
import itsbolivar.edu.ec.appgraduado.clases.Graduado;
import itsbolivar.edu.ec.appgraduado.clases.Oferta_laboral;
import itsbolivar.edu.ec.appgraduado.clases.Tipo_actividad;
import itsbolivar.edu.ec.appgraduado.clases.Tipo_cargo;
import itsbolivar.edu.ec.appgraduado.clases.Tipo_licencia;
import itsbolivar.edu.ec.appgraduado.clases.Tipo_sueldo;

/**
 * Created by XL on 11/12/2017.
 */

public class Mapear {
    public static Graduado graduado(JSONObject item) {

        Graduado g = new Graduado();
        try {
            g.setCodigo(item.getInt("codigo"));
            g.setNombre(item.getString("nombre"));
            g.setCi(item.getString("ci"));
            g.setFecha_nac(item.getLong("fecha_nac"));
            g.setCiudad_actual(item.getString("ciudad_actual"));
            g.setDireccion(item.getString("direccion"));
            g.setTelefono(item.getString("telefono"));
            g.setCelular1(item.getString("celular1"));
            g.setCelular2(item.getString("celular2"));
            g.setEmail(item.getString("email"));
            g.setFacebook(item.getString("facebook"));
            g.setClave(item.getString("clave"));

            JSONObject objEstado_civil = item.getJSONObject("estado_civil");
            Estado_civil estado_civil = new Estado_civil();
            estado_civil.setCodigo(objEstado_civil.getInt("codigo"));
            estado_civil.setNombre(objEstado_civil.getString("nombre"));

            JSONObject objTipo_licencia = item.getJSONObject("tipo_licencia");
            Tipo_licencia tipo_licencia = new Tipo_licencia();
            tipo_licencia.setCodigo(objTipo_licencia.getInt("codigo"));
            tipo_licencia.setNombre(objTipo_licencia.getString("nombre"));

            g.setEstado_civil(estado_civil);
            g.setTipo_licencia(tipo_licencia);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return g;

    }

    public static Oferta_laboral oferta_laboral(JSONObject item ) {
        Oferta_laboral oferta_laboral = new Oferta_laboral();
        try {

                //datos sin rreferencia

                oferta_laboral.setCodigo(item.getInt("codigo"));
                oferta_laboral.setCaract_cargo(item.getString("caract_cargo"));
                oferta_laboral.setExperiencia(item.getString("experiencia"));
                //datos con referencia
                Empresa empresa = new Empresa();
                JSONObject objEmpresa = item.getJSONObject("empresa");

                empresa.setCodigo(objEmpresa.getInt("codigo"));
                empresa.setNombre(objEmpresa.getString("nombre"));
                empresa.setDireccion(objEmpresa.getString("direccion"));
                empresa.setTelefono(objEmpresa.getString("telefono"));
                empresa.setUsuario(objEmpresa.getString("usuario"));
                empresa.setPertenece(objEmpresa.getString("pertenece"));
                empresa.setClave(objEmpresa.getString("clave"));

                Tipo_actividad tipo_actividad = new Tipo_actividad();
                JSONObject objtipo_actividad = objEmpresa.getJSONObject("tipo_actividad");

                tipo_actividad.setCodigo(objtipo_actividad.getInt("codigo"));
                tipo_actividad.setNombre(objtipo_actividad.getString("nombre"));

                empresa.setTipo_actividad(tipo_actividad);
                oferta_laboral.setEmpresa(empresa);

                JSONObject objtipo_cargo = item.getJSONObject("tipo_cargo");
                Tipo_cargo tipo_cargo = new Tipo_cargo();
                tipo_cargo.setCodigo(objtipo_cargo.getInt("codigo"));
                tipo_cargo.setNombre(objtipo_cargo.getString("nombre"));
                JSONObject objtipo_sueldo = item.getJSONObject("tipo_sueldo");
                Tipo_sueldo tipo_sueldo = new Tipo_sueldo();
                tipo_sueldo.setCodigo(objtipo_sueldo.getInt("codigo"));
                tipo_sueldo.setRango(objtipo_sueldo.getString("rango"));

                oferta_laboral.setTipo_cargo(tipo_cargo);
                oferta_laboral.setTipo_sueldo(tipo_sueldo);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return oferta_laboral;

    }
}
