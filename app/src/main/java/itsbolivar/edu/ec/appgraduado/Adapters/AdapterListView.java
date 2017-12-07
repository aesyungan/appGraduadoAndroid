package itsbolivar.edu.ec.appgraduado.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import itsbolivar.edu.ec.appgraduado.R;
import itsbolivar.edu.ec.appgraduado.clases.Oferta_laboral;

/**
 * Created by XL on 26/7/2017.
 */

public class AdapterListView extends ArrayAdapter<Oferta_laboral> {

    public AdapterListView(@NonNull Context context, List<Oferta_laboral> data) {
        super(context,0,data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Oferta_laboral Oferta_laboral =getItem(position);
        if (convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.row,parent,false);
        }

        TextView txtnombreEmpresa = (TextView) convertView.findViewById(R.id.txtnombreEmpresa);
        txtnombreEmpresa.setText(Oferta_laboral.getEmpresa().getNombre());

        TextView txtcaract_cargo = (TextView) convertView.findViewById(R.id.txtcaract_cargo);
        txtcaract_cargo.setText(Oferta_laboral.getCaract_cargo());

        TextView txt_tipo_cargo_nombre = (TextView) convertView.findViewById(R.id.txt_tipo_cargo_nombre);
        txt_tipo_cargo_nombre.setText(Oferta_laboral.getTipo_cargo().getNombre());

        TextView txtexperiencia = (TextView) convertView.findViewById(R.id.txtexperiencia);
        txtexperiencia.setText(Oferta_laboral.getExperiencia());
        return convertView;
    }

}
