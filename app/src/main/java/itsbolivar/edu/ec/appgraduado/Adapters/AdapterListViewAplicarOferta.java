package itsbolivar.edu.ec.appgraduado.Adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import itsbolivar.edu.ec.appgraduado.R;
import itsbolivar.edu.ec.appgraduado.clases.Aplicar_oferta;
import itsbolivar.edu.ec.appgraduado.clases.Oferta_laboral;

/**
 * Created by XL on 11/12/2017.
 */

public class AdapterListViewAplicarOferta extends ArrayAdapter<Aplicar_oferta> {

    public AdapterListViewAplicarOferta(@NonNull Context context, List<Aplicar_oferta> data) {
        super(context, 0, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Aplicar_oferta aplicar_oferta = getItem(position);
        Oferta_laboral oferta_laboral = aplicar_oferta.getOferta_laboral();
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_aplicar_oferta, parent, false);
        }
        ImageView imageEmpresa = (ImageView) convertView.findViewById(R.id.AOimageEmpresa);
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
            imageEmpresa.setImageDrawable(getContext().getResources().getDrawable(listImg[id], getContext().getTheme()));
            Log.d("mayor a lolipop:", "true");
        } else {
            imageEmpresa.setImageDrawable(getContext().getResources().getDrawable(listImg[id]));
        }
        TextView txtnombreEmpresa = (TextView) convertView.findViewById(R.id.txtAOnombreEmpresa);
        txtnombreEmpresa.setText(oferta_laboral.getEmpresa().getNombre());

        TextView txtcaract_cargo = (TextView) convertView.findViewById(R.id.txtAOcaract_cargo);
        txtcaract_cargo.setText(oferta_laboral.getCaract_cargo());

        TextView txt_tipo_cargo_nombre = (TextView) convertView.findViewById(R.id.txt_AOtipo_cargo_nombre);
        txt_tipo_cargo_nombre.setText(oferta_laboral.getTipo_cargo().getNombre());

        TextView txtexperiencia = (TextView) convertView.findViewById(R.id.txtAOexperiencia);
        txtexperiencia.setText(oferta_laboral.getExperiencia());
        return convertView;
    }

}
