package itsbolivar.edu.ec.appgraduado;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import itsbolivar.edu.ec.appgraduado.LN.LNAplicar_Oferta;
import itsbolivar.edu.ec.appgraduado.LN.LNOferta_laboral;
import itsbolivar.edu.ec.appgraduado.clases.Oferta_laboral;
import itsbolivar.edu.ec.appgraduado.clases.staticos.EstadoTelefono;
import itsbolivar.edu.ec.appgraduado.clases.staticos.UsuarioSingleton;

public class VerDetalles extends AppCompatActivity {


    Oferta_laboral oferta_laboral = new Oferta_laboral();
    TextView txtdetalleNombre, txtDireccion,txtTelefono, txtDetalleCarac_cargo, txtDetalleCargo, txtDetalleSueldo, txtDetalleExperiencia;
    ImageView imgCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_detalles);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_alex);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar1);
      //  collapsingToolbarLayout.setTitle("Ofertas Laborales");
        collapsingToolbarLayout.setTitleEnabled(false);
        Context context = this;
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(context, R.color.blanco));
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(context, R.color.blanco));
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>Detalles </font>"));


        imgCards = (ImageView) findViewById(R.id.imgCards);
        txtdetalleNombre = (TextView) findViewById(R.id.txtdetalleNombre);
        txtDetalleCarac_cargo = (TextView) findViewById(R.id.txtDetalleCarac_cargo);
        txtDetalleCargo = (TextView) findViewById(R.id.txtDetalleCargo);
        txtDetalleSueldo = (TextView) findViewById(R.id.txtDetalleSueldo);
        txtDetalleExperiencia = (TextView) findViewById(R.id.txtDetalleExperiencia);
        txtDireccion = (TextView) findViewById(R.id.txtDetalleDireccion);
        txtTelefono = (TextView) findViewById(R.id.txtDetalleTelefono);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LNAplicar_Oferta ln = new LNAplicar_Oferta(EstadoTelefono.activity, EstadoTelefono.context, getString(R.string.IP_SERVIDOR)+"ws/aplicarOferta/AplicarNuevaOferta");
                ln.Agregar(oferta_laboral, UsuarioSingleton.getInstance().graduado);
                /*
                Snackbar.make(view, "Success", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        Intent intent = getIntent();
        Bundle parametros = intent.getExtras();
        int id = parametros.getInt("id");
        oferta_laboral.setCodigo(id);
        LNOferta_laboral ln = new LNOferta_laboral(VerDetalles.this, getBaseContext(), getString(R.string.URL_Service_oferta_laboral));
        ln.getOferta_laboralVer(oferta_laboral, txtdetalleNombre, txtDetalleCarac_cargo, txtDetalleCargo, txtDetalleSueldo, txtDetalleExperiencia,txtTelefono,txtDireccion);

        int listImg[] = new int[]{R.drawable.empresa3, R.drawable.empresa4};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imgCards.setImageDrawable(getResources().getDrawable(listImg[(int) (Math.random() * listImg.length)], getTheme()));
            Log.d("mayor a lolipop:", "true");
        } else {
            imgCards.setImageDrawable(getResources().getDrawable(listImg[(int) (Math.random() * listImg.length)]));
        }

    }

}
