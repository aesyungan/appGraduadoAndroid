package itsbolivar.edu.ec.appgraduado;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import itsbolivar.edu.ec.appgraduado.LN.LNOferta_laboral;
import itsbolivar.edu.ec.appgraduado.clases.Oferta_laboral;

public class MainActivity extends AppCompatActivity {
    //String URL_Service_oferta_laboral="http://192.168.1.8:8080/graduado/ws/oferta/listar2";
    String URL_Service_oferta_laboral="http://192.168.1.8:8080/graduado/ws/oferta/listar2";
    ArrayList<Oferta_laboral> list= new ArrayList<>();
    ListView listViewDatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewDatos=(ListView) findViewById(R.id.listViewDatos);
        LNOferta_laboral lnOferta_laboral= new LNOferta_laboral(MainActivity.this,getBaseContext(),URL_Service_oferta_laboral);
        lnOferta_laboral.getOferta_laboralListView(list,listViewDatos);

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
    }

}
