package itsbolivar.edu.ec.appgraduado;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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

import itsbolivar.edu.ec.appgraduado.LN.LNOferta_laboral;
import itsbolivar.edu.ec.appgraduado.clases.Oferta_laboral;

public class VerDetalles extends AppCompatActivity {


    Oferta_laboral oferta_laboral = new Oferta_laboral();
    TextView txtdetalleNombre, txtDetalleCarac_cargo, txtDetalleCargo, txtDetalleSueldo, txtDetalleExperiencia;
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
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Success", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Intent intent = getIntent();
        Bundle parametros = intent.getExtras();
        int id = parametros.getInt("id");
        oferta_laboral.setCodigo(id);
        LNOferta_laboral ln = new LNOferta_laboral(VerDetalles.this, getBaseContext(), getString(R.string.URL_Service_oferta_laboral));
        ln.getOferta_laboralVer(oferta_laboral, txtdetalleNombre, txtDetalleCarac_cargo, txtDetalleCargo, txtDetalleSueldo, txtDetalleExperiencia);
        int listImg[] = new int[]{R.drawable.empresa3, R.drawable.empresa4};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imgCards.setImageDrawable(getResources().getDrawable(listImg[(int) (Math.random() * listImg.length)], getTheme()));
            Log.d("mayor a lolipop:", "true");
        } else {
            imgCards.setImageDrawable(getResources().getDrawable(listImg[(int) (Math.random() * listImg.length)]));
        }

    }

}
