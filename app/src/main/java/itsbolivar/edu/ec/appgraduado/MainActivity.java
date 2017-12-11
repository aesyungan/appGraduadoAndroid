package itsbolivar.edu.ec.appgraduado;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import itsbolivar.edu.ec.appgraduado.LN.LNOferta_laboral;
import itsbolivar.edu.ec.appgraduado.clases.Oferta_laboral;

public class MainActivity extends AppCompatActivity {

    ArrayList<Oferta_laboral> list = new ArrayList<>();
    ListView listViewDatos;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setTitle("Ofertas Laborales");
        Context context = this;
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(context, R.color.blanco));
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(context, R.color.blanco));


        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(context, R.color.respuesta));

        listViewDatos = (ListView) findViewById(R.id.listViewDatos);
        LNOferta_laboral lnOferta_laboral = new LNOferta_laboral(MainActivity.this, getBaseContext(), getString(R.string.URL_Service_oferta_laboral));
        //lnOferta_laboral.getOferta_laboralListView(list, listViewDatos);

        listViewDatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), VerDetalles.class);
                intent.putExtra("id", list.get(position).getCodigo());
                //intent.putExtra("host", host.getText().toString());
                //Toast.makeText(getApplicationContext(),name.getText()+""+host.getText(),Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
        ViewCompat.setNestedScrollingEnabled(listViewDatos, true);
        // listViewDatos.setNestedScrollingEnabled(true);
    }

}
