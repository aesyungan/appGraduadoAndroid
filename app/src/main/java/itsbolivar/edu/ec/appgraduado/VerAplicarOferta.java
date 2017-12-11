package itsbolivar.edu.ec.appgraduado;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import itsbolivar.edu.ec.appgraduado.LN.LNOferta_laboral;
import itsbolivar.edu.ec.appgraduado.clases.Aplicar_oferta;
import itsbolivar.edu.ec.appgraduado.clases.Oferta_laboral;
import itsbolivar.edu.ec.appgraduado.clases.staticos.EstadoTelefono;
import itsbolivar.edu.ec.appgraduado.clases.staticos.UsuarioSingleton;

public class VerAplicarOferta extends AppCompatActivity {

    Aplicar_oferta aplicar_oferta = new Aplicar_oferta();
    TextView txtdirecion,txtFechaAplica,txtFechaInicio, txtDetalleCarac_cargo, txtDetalleCargo, txtDetalleSueldo, txtDetalleExperiencia;
    ImageView imgCards,imgCardsFondo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_aplicar_oferta);

        aplicar_oferta = UsuarioSingleton.getInstance().aplicar_ofertaSelect;

        Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbarVerAplicarOferta);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbarVerAplicarOferta);
         //collapsingToolbarLayout.setTitle(aplicar_oferta.getOferta_laboral().getEmpresa().getNombre());
        collapsingToolbarLayout.setTitleEnabled(false);
        Context context = this;
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(context, R.color.blanco));
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(context, R.color.blanco));
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(Html.fromHtml("<font color='#ccc'>"+aplicar_oferta.getOferta_laboral().getEmpresa().getNombre()+" </font>"));


        imgCards = (ImageView) findViewById(R.id.imgHeaderVerAplicarOferta);
        imgCardsFondo = (ImageView) findViewById(R.id.imgCardsFondo);

        txtDetalleCarac_cargo = (TextView) findViewById(R.id.txtDetalleCarac_cargoVerAplicarOferta);
        txtDetalleCarac_cargo.setText(aplicar_oferta.getOferta_laboral().getCaract_cargo());
        txtDetalleCargo = (TextView) findViewById(R.id.txtDetalleCargoVerAplicarOferta);
        txtDetalleCargo.setText(aplicar_oferta.getOferta_laboral().getTipo_cargo().getNombre());
        txtDetalleSueldo = (TextView) findViewById(R.id.txtDetalleSueldoVerAplicarOferta);
        txtDetalleSueldo.setText(aplicar_oferta.getOferta_laboral().getTipo_sueldo().getRango());
        txtDetalleExperiencia = (TextView) findViewById(R.id.txtDetalleExperienciaVerAplicarOferta);
        txtDetalleExperiencia.setText(aplicar_oferta.getOferta_laboral().getExperiencia());
        txtdirecion=(TextView)findViewById(R.id.txtDetalleDireccionVerAplicarOferta);
        txtdirecion.setText(aplicar_oferta.getOferta_laboral().getEmpresa().getDireccion());
        txtFechaInicio=(TextView)findViewById(R.id.txtDetalleFechaInicioVerAplicarOferta);

        txtFechaAplica=(TextView)findViewById(R.id.txtDetalleFechaAplicaVerAplicarOferta);
        try {
            txtFechaInicio.setText(convertMillisecondsToString(aplicar_oferta.getFecha_inicio()));
            txtFechaAplica.setText(convertMillisecondsToString(aplicar_oferta.getFecha_aplica()));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        int listImg[] = new int[]{R.drawable.si, R.drawable.no, R.drawable.espera2};
        int id = 0;
        if (aplicar_oferta.getContrato().equals("SI") || aplicar_oferta.getContrato().equals("Si") || aplicar_oferta.getContrato().equals("si")) {
            id = 0;
        }
        if (aplicar_oferta.getContrato().equals("NO") || aplicar_oferta.getContrato().equals("No") || aplicar_oferta.getContrato().equals("no")) {
            id = 1;
        }
        if (aplicar_oferta.getContrato().equals("")) {

            id = 2;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imgCards.setImageDrawable(EstadoTelefono.context.getResources().getDrawable(listImg[id], EstadoTelefono.context.getTheme()));
            Log.d("mayor a lolipop:", "true");
        } else {
            imgCards.setImageDrawable(EstadoTelefono.context.getResources().getDrawable(listImg[id]));
        }


        //header cards
        int listImg1[] = new int[]{R.drawable.empresa3, R.drawable.empresa4};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imgCardsFondo.setImageDrawable(getResources().getDrawable(listImg1[(int) (Math.random() * listImg1.length)], getTheme()));
            Log.d("mayor a lolipop:", "true");
        } else {
            imgCardsFondo.setImageDrawable(getResources().getDrawable(listImg1[(int) (Math.random() * listImg1.length)]));
        }

    }

    public String convertMillisecondsToString(long fecha) throws ParseException {
        String dateFormat = "yyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(fecha);
        return simpleDateFormat.format(calendar.getTime());
    }


}
