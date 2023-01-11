package com.reeu.reeu_premium;



import static com.reeu.reeu_premium.R.layout.listados_home_recuperado;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
public class Adapter2 extends ArrayAdapter<Usuarios> {
    Context context;
    List<Usuarios> arrayusuarios;

    public Adapter2(@NonNull Context context, List<Usuarios>arrayusuarios) {
        super(context, listados_home_recuperado,arrayusuarios);
        this.context=context;
        this.arrayusuarios = arrayusuarios;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listados_home_recuperado, null,true);
        TextView txtID = view.findViewById(R.id.txtid);
        TextView txtNombre = view.findViewById(R.id.txtnombre);
        //TextView txtCategoria = view.findViewById(R.id.txtubicacion);
        //ImageView txtImage = view.findViewById(R.id.imagenEvento);




        txtID.setText(arrayusuarios.get(position).getId());
        txtNombre.setText(arrayusuarios.get(position).getCodigo());
        //txtImage.setImageResource(arrayusuarios.get(position).getimagen());
        //txtCategoria.setText(arrayusuarios.get(position).getCategoria());

        return view;
    }


}