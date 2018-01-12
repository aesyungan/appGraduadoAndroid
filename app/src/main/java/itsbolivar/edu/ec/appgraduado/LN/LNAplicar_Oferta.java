package itsbolivar.edu.ec.appgraduado.LN;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import itsbolivar.edu.ec.appgraduado.Adapters.AdapterListView;
import itsbolivar.edu.ec.appgraduado.Adapters.AdapterListViewAplicarOferta;
import itsbolivar.edu.ec.appgraduado.clases.Aplicar_oferta;
import itsbolivar.edu.ec.appgraduado.clases.Empresa;
import itsbolivar.edu.ec.appgraduado.clases.Estado_civil;
import itsbolivar.edu.ec.appgraduado.clases.Graduado;
import itsbolivar.edu.ec.appgraduado.clases.JSON;
import itsbolivar.edu.ec.appgraduado.clases.Oferta_laboral;
import itsbolivar.edu.ec.appgraduado.clases.Tipo_actividad;
import itsbolivar.edu.ec.appgraduado.clases.Tipo_cargo;
import itsbolivar.edu.ec.appgraduado.clases.Tipo_licencia;
import itsbolivar.edu.ec.appgraduado.clases.Tipo_sueldo;
import itsbolivar.edu.ec.appgraduado.clases.Tools;

/**
 * Created by XL on 10/12/2017.
 */

public class LNAplicar_Oferta {
    public Activity activity;
    public Context context;
    public String url;


    public LNAplicar_Oferta(Activity activity, Context context, String url) {
        this.context = context;
        this.url = url;
        this.activity = activity;
    }

    public void optenerDatos(ArrayList<Aplicar_oferta> listDatos, ListView listView, Graduado graduado) {
        new optenerDatosAsyncTask(listDatos, listView, graduado).execute();
    }

    public void Agregar(Oferta_laboral oferta_laboral, Graduado graduado) {
        new insertarAsyncTask(oferta_laboral, graduado).execute();
    }

    public void insertarBaseDatos(String descripcion) {
        ManagerHBHelper admin = new ManagerHBHelper(context, "admiData", null, 1);

        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", 1);
        contentValues.put("descripcion", descripcion);
        db.insert("aplicar_oferta", null, contentValues);
        db.close();
        Log.d("Data Base :", "Ingreso Correcto");
    }

    public void eliminarDatosDataBase() {

        ManagerHBHelper admin = new ManagerHBHelper(context, "admiData", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        db.execSQL("delete from aplicar_oferta");
        db.close();
        Log.i("BorradoTabla->", "usuario");


    }

    public String mostrarDataBase() throws JSONException {
        String descripcion = "";
        ManagerHBHelper admin = new ManagerHBHelper(context, "admiData", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor fila = db.rawQuery("select * from aplicar_oferta ", null);
        while (fila.moveToNext()) {
            Log.i("Fila data base ->", "" + fila.toString());
            int id = fila.getInt(fila.getInt(0));
            descripcion = fila.getString(1);
            Log.d("Datos Base De Datos", "id:" + id + "->descripcion:" + descripcion);

        }
        return descripcion;
    }


    private class insertarAsyncTask extends AsyncTask<String, String, Boolean> {
        JSON json = new JSON();
        private ArrayList<Aplicar_oferta> lstDatos = new ArrayList<>();

        private ProgressDialog pDialog;
        Graduado graduadoLog = new Graduado();
        Oferta_laboral oferta_laboral = new Oferta_laboral();
        boolean errorInternet = false;
        String mensaje = "Error de coneccion";

        public insertarAsyncTask(Oferta_laboral oferta_laboral, Graduado graduado) {
            this.oferta_laboral = oferta_laboral;

            this.graduadoLog = graduado;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Cargando ..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            try {
                if (Tools.getInstance().estadoConneccion(context)) {
                    // si hay internet
                    errorInternet = false;
                    List params = new ArrayList();
                    params.add(new BasicNameValuePair("cod_graduado", Integer.toString(graduadoLog.getCodigo())));
                    params.add(new BasicNameValuePair("cod_ofertalaboral", Integer.toString(oferta_laboral.getCodigo())));
                    String REGISTER_URL = url;//servio web
                    //Posting user data to script
                    JSONObject json = this.json.makeHttpRequest(REGISTER_URL, "GET", params);
                    // full json response
                    Log.d("cargando datos ", json.toString());

                    mensaje = json.getString("message");
                    return true;

                } else {
                    errorInternet = true;
                    return true;//no hay internet
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Boolean res) {
            pDialog.dismiss();
            if (res) {
                if (errorInternet) {
                    Toast.makeText(context, "Error de Conección ", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private class optenerDatosAsyncTask extends AsyncTask<String, String, Boolean> {
        JSON json = new JSON();
        private ArrayList<Aplicar_oferta> lstDatos = new ArrayList<>();
        private ListView listView;
        private AdapterListViewAplicarOferta adapter;
        private ProgressDialog pDialog;
        Graduado graduadoLog = new Graduado();
        boolean errorInternet = false;

        public optenerDatosAsyncTask(ArrayList<Aplicar_oferta> lstDatos, ListView listView, Graduado graduado) {
            this.lstDatos = lstDatos;
            this.listView = listView;
            this.graduadoLog = graduado;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Cargando ..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            try {
                if (Tools.getInstance().estadoConneccion(context)) {
                    // si hay internet
                    errorInternet = false;
                    List params = new ArrayList();
                    params.add(new BasicNameValuePair("codigo", Integer.toString(graduadoLog.getCodigo())));
                    String REGISTER_URL = url;//servio web
                    //Posting user data to script
                    JSONArray json = this.json.makeHttpRequestReturnArray(REGISTER_URL, "GET", params);
                    // full json response
                    Log.d("cargando datos ", json.toString());
                    eliminarDatosDataBase();
                    insertarBaseDatos(json.toString());
                    mostrarDataBase();
                    MaperarDatos(json, lstDatos);
                    return true;
                } else {
                    String data = mostrarDataBase();
                    if (data != "") {

                        JSONArray json = new JSONArray(data);
                        MaperarDatos(json, lstDatos);
                        errorInternet = true;
                    } else {
                        return false;
                    }
                    return true;//no hay internet
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Boolean res) {
            pDialog.dismiss();
            if (res) {
                //si optuvo datos crea el adapter y pone en listview
                adapter = new AdapterListViewAplicarOferta(context, lstDatos);
                listView.setAdapter(adapter);
                if (errorInternet) {
                    Toast.makeText(context, "Error de Conección ", Toast.LENGTH_LONG).show();

                }
            }
        }
    }


    public void MaperarDatos(JSONArray json, ArrayList<Aplicar_oferta> lstAplicarOferta) {

        try {
            for (int i = 0; i < json.length(); i++) {
                JSONObject itemAll = json.getJSONObject(i);
                Aplicar_oferta aplicar_oferta = new Aplicar_oferta();
                //inio oferta laboral
                Oferta_laboral oferta_laboral = Mapear.oferta_laboral(itemAll.getJSONObject("oferta_laboral"));
                //graduado
                Graduado graduado = Mapear.graduado(itemAll.getJSONObject("graduado"));
                aplicar_oferta.setOferta_laboral(oferta_laboral);
                aplicar_oferta.setGraduado(graduado);
                //no selacionados
                aplicar_oferta.setFecha_aplica(itemAll.getLong("fecha_aplica"));
                aplicar_oferta.setContrato(itemAll.getString("contrato"));
                aplicar_oferta.setFecha_inicio(itemAll.getLong("fecha_inicio"));
                lstAplicarOferta.add(aplicar_oferta);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
