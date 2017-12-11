package itsbolivar.edu.ec.appgraduado;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import itsbolivar.edu.ec.appgraduado.LN.LNOferta_laboral;
import itsbolivar.edu.ec.appgraduado.clases.Oferta_laboral;
import itsbolivar.edu.ec.appgraduado.clases.staticos.EstadoTelefono;
import itsbolivar.edu.ec.appgraduado.clases.staticos.UsuarioSingleton;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOfertas extends Fragment {
    ArrayList<Oferta_laboral> list = new ArrayList<>();
    ListView listViewDatos;


    public FragmentOfertas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fragment_ofertas, container, false);


        Toolbar toolbar = (Toolbar)view.findViewById(R.id.MyToolbarF);
       // setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapse_toolbarF);
        collapsingToolbarLayout.setTitle("Ofertas Disponibles");
        Context context = EstadoTelefono.context;
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(context, R.color.blanco));
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(context, R.color.blanco));


        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(context, R.color.colocollap));

        listViewDatos = (ListView)view.findViewById(R.id.listViewDatosF);
        LNOferta_laboral lnOferta_laboral = new LNOferta_laboral(EstadoTelefono.activity, EstadoTelefono.context, getString(R.string.IP_SERVIDOR)+"ws/graduado/ofertasNoAplicadas");
        lnOferta_laboral.getOferta_laboralListView(list, listViewDatos, UsuarioSingleton.getInstance().graduado);

        listViewDatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EstadoTelefono.appContext, VerDetalles.class);
                Log.i("ID", String.valueOf(list.get(position).getCodigo()));
                intent.putExtra("id", list.get(position).getCodigo());
                intent.putExtra("add",true);

                startActivity(intent);
            }
        });
        ViewCompat.setNestedScrollingEnabled(listViewDatos, true);

        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
