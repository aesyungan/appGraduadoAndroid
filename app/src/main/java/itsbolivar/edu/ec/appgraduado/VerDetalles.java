package itsbolivar.edu.ec.appgraduado;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import itsbolivar.edu.ec.appgraduado.LN.LNOferta_laboral;
import itsbolivar.edu.ec.appgraduado.clases.Oferta_laboral;

public class VerDetalles extends AppCompatActivity {
    String URL_Service_oferta_laboral = "http://192.168.1.8:8080/graduado/ws/oferta/listar2";

    Oferta_laboral oferta_laboral = new Oferta_laboral();
    TextView txtdetalleNombre, txtDetalleCarac_cargo, txtDetalleCargo, txtDetalleSueldo, txtDetalleExperiencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_detalles);
        txtdetalleNombre=(TextView) findViewById(R.id.txtdetalleNombre);
        txtDetalleCarac_cargo=(TextView) findViewById(R.id.txtDetalleCarac_cargo);
        txtDetalleCargo=(TextView) findViewById(R.id.txtDetalleCargo);
        txtDetalleSueldo=(TextView) findViewById(R.id.txtDetalleSueldo);
        txtDetalleExperiencia=(TextView) findViewById(R.id.txtDetalleExperiencia);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Funcion no implementado", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Intent intent = getIntent();
        Bundle parametros = intent.getExtras();
        int id = parametros.getInt("id");
        oferta_laboral.setCodigo(id);
        LNOferta_laboral ln = new LNOferta_laboral(VerDetalles.this, getBaseContext(), URL_Service_oferta_laboral);
        ln.getOferta_laboralVer(oferta_laboral,txtdetalleNombre, txtDetalleCarac_cargo, txtDetalleCargo, txtDetalleSueldo, txtDetalleExperiencia);
    }

}
