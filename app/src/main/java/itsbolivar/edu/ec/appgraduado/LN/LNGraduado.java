package itsbolivar.edu.ec.appgraduado.LN;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import itsbolivar.edu.ec.appgraduado.clases.Estado_civil;
import itsbolivar.edu.ec.appgraduado.clases.Graduado;
import itsbolivar.edu.ec.appgraduado.clases.JSON;
import itsbolivar.edu.ec.appgraduado.clases.Tipo_licencia;
import itsbolivar.edu.ec.appgraduado.clases.Tools;
import itsbolivar.edu.ec.appgraduado.clases.staticos.UsuarioSingleton;

/**
 * Created by XL on 10/12/2017.
 */

public class LNGraduado {
    public Activity activity;
    public Context context;
    public String url;


    public LNGraduado(Activity activity, Context context, String url) {
        this.context = context;
        this.url = url;
        this.activity = activity;
    }

    public void Logear(Graduado graduado, Class classGo) {

        new loginAsyncTask(graduado,classGo).execute();

    }

    public void insertarBaseDatos(String descripcion) {
        ManagerHBHelper admin = new ManagerHBHelper(context, "admiData", null, 1);

        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", 1);
        contentValues.put("descripcion", descripcion);
        db.insert("graduado", null, contentValues);
        db.close();
        Log.d("Data Base :", "Ingreso Correcto");
    }

    public void eliminarDatosDataBase() {

        ManagerHBHelper admin = new ManagerHBHelper(context, "admiData", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        db.execSQL("delete from graduado");
        db.close();
        Log.i("BorradoTabla->", "usuario");


    }

    public String mostrarDataBase() throws JSONException {
        String descripcion = "";
        ManagerHBHelper admin = new ManagerHBHelper(context, "admiData", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor fila = db.rawQuery("select * from graduado ", null);
        while (fila.moveToNext()) {
            Log.i("Fila data base ->", "" + fila.toString());
            int id = fila.getInt(fila.getInt(0));
            descripcion = fila.getString(1);
            Log.d("Datos Base De Datos", "id:" + id + "->descripcion:" + descripcion);

        }
        return descripcion;
    }


    private class loginAsyncTask extends AsyncTask<String, String, Boolean> {
        JSON json = new JSON();
        Graduado graduado = new Graduado();
        private ProgressDialog pDialog;
        boolean errorInternet = false;
        Class classGo;

        public loginAsyncTask(Graduado graduado,Class classGo) {
            this.graduado = graduado;
            this.classGo=classGo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Verificando ..");
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
                    errorInternet = false;
                    //por internet
                    List params = new ArrayList();
                    //params.add(new BasicNameValuePair("cedula", userNow));
                    String REGISTER_URL = url;//servio web
                    params.add(new BasicNameValuePair("user", graduado.getCi()));
                    params.add(new BasicNameValuePair("password", graduado.getClave()));
                    //Posting user data to script
                    JSONObject json = this.json.makeHttpRequest(REGISTER_URL, "GET", params);
                    Log.d("cargando datos ", json.toString());
                    int success = 0;
                    success = json.getInt("success");//si fue correcto el login
                    if (success == 1) {
                        graduado = MaperarDatos(json.getJSONArray("data"));
                        eliminarDatosDataBase();
                        insertarBaseDatos(json.getJSONArray("data").toString());
                        UsuarioSingleton.getInstance().graduado = graduado;//guardamos en  Global
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    errorInternet = true;
                    return false;
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Boolean res) {
            pDialog.dismiss();
            if (res) {
                Toast.makeText(context, "Correcto", Toast.LENGTH_LONG).show();
                activity.finish();
                Intent i = new Intent(activity, classGo);
                activity.startActivity(i);
            } else {
                if (errorInternet) {
                    Toast.makeText(context, "Error de Conección ", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(context, "Error de Usuario o Contraseña", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public Graduado optenerUsuarioLogueado() throws JSONException {
        Graduado graduado = null;
        String data = mostrarDataBase();
        if (data != "") {
            JSONArray json = new JSONArray(data);
            graduado = MaperarDatos(json);
        }
        return graduado;
    }

    public Graduado MaperarDatos(JSONArray json) {
        Graduado graduado = null;
        try {
            for (int i = 0; i < json.length(); i++) {
                JSONObject item = json.getJSONObject(i);
                Graduado g = new Graduado();
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
                graduado = g;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return graduado;

    }


}
