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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import itsbolivar.edu.ec.appgraduado.Adapters.AdapterListView;
import itsbolivar.edu.ec.appgraduado.clases.Empresa;
import itsbolivar.edu.ec.appgraduado.clases.JSON;
import itsbolivar.edu.ec.appgraduado.clases.Oferta_laboral;
import itsbolivar.edu.ec.appgraduado.clases.Tipo_actividad;
import itsbolivar.edu.ec.appgraduado.clases.Tipo_cargo;
import itsbolivar.edu.ec.appgraduado.clases.Tipo_sueldo;
import itsbolivar.edu.ec.appgraduado.clases.Tools;

/**
 * Created by XL on 7/12/2017.
 */

public class LNOferta_laboral {
    public Activity activity;
    public Context context;
    public String url;


    public LNOferta_laboral(Activity activity, Context context, String url) {
        this.context = context;
        this.url = url;
        this.activity = activity;
    }

    public void getOferta_laboralListView(List<Oferta_laboral> lstOferta_laboral, ListView listView) {
        if (Tools.getInstance().estadoConneccion(this.context)) {
            new getOferta_laboralListViewAsyncTask(lstOferta_laboral, listView).execute();
        } else {
            new getOferta_laboralListViewAsyncTaskLocal(lstOferta_laboral, listView).execute();
            Toast.makeText(context, "Error Coneccion", Toast.LENGTH_LONG).show();
        }
    }

    public void getOferta_laboralVer(Oferta_laboral oferta_laboralRes, TextView txtdetalleNombre, TextView txtDetalleCarac_cargo, TextView txtDetalleCargo, TextView txtDetalleSueldo, TextView txtDetalleExperiencia) {
        if (Tools.getInstance().estadoConneccion(this.context)) {
            new getOferta_laboralVerAsyncTask(oferta_laboralRes, txtdetalleNombre, txtDetalleCarac_cargo, txtDetalleCargo, txtDetalleSueldo, txtDetalleExperiencia).execute();
        } else {
            new getOferta_laboralVerAsyncTaskLocal(oferta_laboralRes, txtdetalleNombre, txtDetalleCarac_cargo, txtDetalleCargo, txtDetalleSueldo, txtDetalleExperiencia).execute();
            Toast.makeText(context, "Error Coneccion", Toast.LENGTH_LONG).show();
        }

    }

    public void insertarBaseDatos(String descripcion) {
        ManagerHBHelper admin = new ManagerHBHelper(context, "admiData", null, 1);

        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", 1);
        contentValues.put("descripcion", descripcion);
        db.insert("oferta_laboral", null, contentValues);
        db.close();
        Log.d("Data Base :", "Ingreso Correcto");
    }

    public void eliminarDatosDataBase() {

        ManagerHBHelper admin = new ManagerHBHelper(context, "admiData", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        db.execSQL("delete from oferta_laboral");
        db.close();
        Log.i("BorradoTabla->", "usuario");


    }

    public String mostrarDataBase() throws JSONException {
        String descripcion = "";
        ManagerHBHelper admin = new ManagerHBHelper(context, "admiData", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor fila = db.rawQuery("select * from oferta_laboral ", null);
        while (fila.moveToNext()) {
            Log.i("Fila data base ->", "" + fila.toString());
            int id = fila.getInt(fila.getInt(0));
            descripcion = fila.getString(1);
            Log.d("Datos Base De Datos", "id:" + id + "->descripcion:" + descripcion);

        }
        return descripcion;
    }

    //local
    private class getOferta_laboralListViewAsyncTaskLocal extends AsyncTask<String, String, Boolean> {
        JSON json = new JSON();
        private List<Oferta_laboral> lstOferta_laboral = new ArrayList<>();
        private ListView listView;
        private AdapterListView adapterListViewOferta_laboral;
        private ProgressDialog pDialog;

        public getOferta_laboralListViewAsyncTaskLocal(List<Oferta_laboral> lstOferta_laboral, ListView listView) {
            this.lstOferta_laboral = lstOferta_laboral;
            this.listView = listView;
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
                String datos = mostrarDataBase();
                if (datos != "") {
                    JSONArray json = new JSONArray(datos);
                    for (int i = 0; i < json.length(); i++) {
                        JSONObject item = json.getJSONObject(i);
                        //datos sin rreferencia
                        Oferta_laboral oferta_laboral = new Oferta_laboral();
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
                        tipo_cargo.setNombre(objtipo_cargo.getString("nombre"))
                        ;
                        JSONObject objtipo_sueldo = item.getJSONObject("tipo_sueldo");
                        Tipo_sueldo tipo_sueldo = new Tipo_sueldo();
                        tipo_sueldo.setCodigo(objtipo_sueldo.getInt("codigo"));
                        tipo_sueldo.setRango(objtipo_sueldo.getString("rango"));

                        oferta_laboral.setTipo_cargo(tipo_cargo);
                        oferta_laboral.setTipo_sueldo(tipo_sueldo);
                        lstOferta_laboral.add(oferta_laboral);
                        Log.d("codigo", item.getString("codigo"));
                    }
                    return true;
                } else {
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
                //si optuvo datos crea el adapter y pone en listview
                adapterListViewOferta_laboral = new AdapterListView(context, lstOferta_laboral);
                listView.setAdapter(adapterListViewOferta_laboral);

            }
        }
    }

    //asysntak remoto
    private class getOferta_laboralListViewAsyncTask extends AsyncTask<String, String, Boolean> {
        JSON json = new JSON();
        private List<Oferta_laboral> lstOferta_laboral = new ArrayList<>();
        private ListView listView;
        private AdapterListView adapterListViewOferta_laboral;
        private ProgressDialog pDialog;

        public getOferta_laboralListViewAsyncTask(List<Oferta_laboral> lstOferta_laboral, ListView listView) {
            this.lstOferta_laboral = lstOferta_laboral;
            this.listView = listView;
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
                // Building Parameters
                List params = new ArrayList();
                //params.add(new BasicNameValuePair("cedula", userNow));
                String REGISTER_URL = url;//servio web
                //Posting user data to script
                JSONArray json = this.json.makeHttpRequestReturnArray(REGISTER_URL, "GET", params);
                // full json response

                Log.d("cargando datos ", json.toString());

                eliminarDatosDataBase();
                insertarBaseDatos(json.toString());
                mostrarDataBase();

                for (int i = 0; i < json.length(); i++) {
                    JSONObject item = json.getJSONObject(i);
                    //datos sin rreferencia
                    Oferta_laboral oferta_laboral = new Oferta_laboral();
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
                    tipo_cargo.setNombre(objtipo_cargo.getString("nombre"))
                    ;
                    JSONObject objtipo_sueldo = item.getJSONObject("tipo_sueldo");
                    Tipo_sueldo tipo_sueldo = new Tipo_sueldo();
                    tipo_sueldo.setCodigo(objtipo_sueldo.getInt("codigo"));
                    tipo_sueldo.setRango(objtipo_sueldo.getString("rango"));

                    oferta_laboral.setTipo_cargo(tipo_cargo);
                    oferta_laboral.setTipo_sueldo(tipo_sueldo);
                    lstOferta_laboral.add(oferta_laboral);
                    Log.d("codigo", item.getString("codigo"));
                }

                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Boolean res) {
            pDialog.dismiss();
            if (res) {
                //si optuvo datos crea el adapter y pone en listview
                adapterListViewOferta_laboral = new AdapterListView(context, lstOferta_laboral);
                listView.setAdapter(adapterListViewOferta_laboral);

            }
        }
    }
//ver remoto

    private class getOferta_laboralVerAsyncTask extends AsyncTask<String, String, Boolean> {
        JSON json = new JSON();
        private List<Oferta_laboral> lstOferta_laboral = new ArrayList<>();
        TextView txtdetalleNombre, txtDetalleCarac_cargo, txtDetalleCargo, txtDetalleSueldo, txtDetalleExperiencia;
        private ProgressDialog pDialog;
        private Oferta_laboral oferta_laboralSetect = new Oferta_laboral();

        public getOferta_laboralVerAsyncTask(Oferta_laboral oferta_laboral, TextView txtdetalleNombre, TextView txtDetalleCarac_cargo, TextView txtDetalleCargo, TextView txtDetalleSueldo, TextView txtDetalleExperiencia) {
            oferta_laboralSetect = oferta_laboral;
            this.txtdetalleNombre = txtdetalleNombre;
            this.txtDetalleCarac_cargo = txtDetalleCarac_cargo;
            this.txtDetalleCargo = txtDetalleCargo;
            this.txtDetalleSueldo = txtDetalleSueldo;
            this.txtDetalleExperiencia = txtDetalleExperiencia;

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
                // Building Parameters
                List params = new ArrayList();
                //params.add(new BasicNameValuePair("cedula", userNow));
                String REGISTER_URL = url;//servio web
                //Posting user data to script
                JSONArray json = this.json.makeHttpRequestReturnArray(REGISTER_URL, "GET", params);
                // full json response
                Log.d("cargando datos ", json.toString());
                for (int i = 0; i < json.length(); i++) {
                    JSONObject item = json.getJSONObject(i);
                    //datos sin rreferencia
                    Oferta_laboral oferta_laboral = new Oferta_laboral();
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
                    tipo_cargo.setNombre(objtipo_cargo.getString("nombre"))
                    ;
                    JSONObject objtipo_sueldo = item.getJSONObject("tipo_sueldo");
                    Tipo_sueldo tipo_sueldo = new Tipo_sueldo();
                    tipo_sueldo.setCodigo(objtipo_sueldo.getInt("codigo"));
                    tipo_sueldo.setRango(objtipo_sueldo.getString("rango"));

                    oferta_laboral.setTipo_cargo(tipo_cargo);
                    oferta_laboral.setTipo_sueldo(tipo_sueldo);
                    lstOferta_laboral.add(oferta_laboral);
                    Log.d("codigo", item.getString("codigo"));
                }

                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Boolean res) {
            pDialog.dismiss();
            if (res) {
                for (Oferta_laboral i : lstOferta_laboral) {
                    if (i.getCodigo() == oferta_laboralSetect.getCodigo()) {
                        oferta_laboralSetect = i;
                        this.txtdetalleNombre.setText(i.getEmpresa().getNombre().toString());
                        this.txtDetalleCarac_cargo.setText(i.getCaract_cargo().toString());
                        this.txtDetalleCargo.setText(i.getTipo_cargo().getNombre().toString());
                        this.txtDetalleSueldo.setText(i.getTipo_sueldo().getRango().toString());
                        this.txtDetalleExperiencia.setText(i.getExperiencia().toString());
                        Log.d("ver:", "" + i.getCodigo());
                    }
                }


            }
        }
    }

    //local
    private class getOferta_laboralVerAsyncTaskLocal extends AsyncTask<String, String, Boolean> {
        JSON json = new JSON();
        private List<Oferta_laboral> lstOferta_laboral = new ArrayList<>();
        TextView txtdetalleNombre, txtDetalleCarac_cargo, txtDetalleCargo, txtDetalleSueldo, txtDetalleExperiencia;
        private ProgressDialog pDialog;
        private Oferta_laboral oferta_laboralSetect = new Oferta_laboral();

        public getOferta_laboralVerAsyncTaskLocal(Oferta_laboral oferta_laboral, TextView txtdetalleNombre, TextView txtDetalleCarac_cargo, TextView txtDetalleCargo, TextView txtDetalleSueldo, TextView txtDetalleExperiencia) {
            oferta_laboralSetect = oferta_laboral;
            this.txtdetalleNombre = txtdetalleNombre;
            this.txtDetalleCarac_cargo = txtDetalleCarac_cargo;
            this.txtDetalleCargo = txtDetalleCargo;
            this.txtDetalleSueldo = txtDetalleSueldo;
            this.txtDetalleExperiencia = txtDetalleExperiencia;

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
                String datos = mostrarDataBase();
                if (datos != "") {
                    JSONArray json = new JSONArray(datos);

                    for (int i = 0; i < json.length(); i++) {
                        JSONObject item = json.getJSONObject(i);
                        //datos sin rreferencia
                        Oferta_laboral oferta_laboral = new Oferta_laboral();
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
                        tipo_cargo.setNombre(objtipo_cargo.getString("nombre"))
                        ;
                        JSONObject objtipo_sueldo = item.getJSONObject("tipo_sueldo");
                        Tipo_sueldo tipo_sueldo = new Tipo_sueldo();
                        tipo_sueldo.setCodigo(objtipo_sueldo.getInt("codigo"));
                        tipo_sueldo.setRango(objtipo_sueldo.getString("rango"));

                        oferta_laboral.setTipo_cargo(tipo_cargo);
                        oferta_laboral.setTipo_sueldo(tipo_sueldo);
                        lstOferta_laboral.add(oferta_laboral);
                        Log.d("codigo", item.getString("codigo"));
                    }
                    return true;
                } else {
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
                for (Oferta_laboral i : lstOferta_laboral) {
                    if (i.getCodigo() == oferta_laboralSetect.getCodigo()) {
                        oferta_laboralSetect = i;
                        this.txtdetalleNombre.setText(i.getEmpresa().getNombre().toString());
                        this.txtDetalleCarac_cargo.setText(i.getCaract_cargo().toString());
                        this.txtDetalleCargo.setText(i.getTipo_cargo().getNombre().toString());
                        this.txtDetalleSueldo.setText(i.getTipo_sueldo().getRango().toString());
                        this.txtDetalleExperiencia.setText(i.getExperiencia().toString());
                        Log.d("ver:", "" + i.getCodigo());
                    }
                }


            }
        }
    }


}
