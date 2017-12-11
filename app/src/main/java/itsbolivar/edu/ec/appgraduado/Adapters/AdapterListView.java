package itsbolivar.edu.ec.appgraduado.Adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
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
        CircleImageView imageEmpresa =(CircleImageView) convertView.findViewById(R.id.imageEmpresa);
        int listImg[]= new int[]{R.drawable.empresa,R.drawable.empresa2,R.drawable.empresa5,R.drawable.empresa6};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageEmpresa.setImageDrawable(getContext().getResources().getDrawable(listImg[(int) (Math.random() * listImg.length)] , getContext().getTheme()));
            Log.d("mayor a lolipop:","true");
        } else {
            imageEmpresa.setImageDrawable(getContext().getResources().getDrawable(listImg[(int) (Math.random() * listImg.length)]));
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
