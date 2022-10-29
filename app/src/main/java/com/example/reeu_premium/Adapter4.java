package com.example.reeu_premium;

import static com.example.reeu_premium.R.layout.listados_home2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Adapter4 extends ArrayAdapter<Usuarios> {
    Context context;
    List<Usuarios> arrayusuarios;

    public Adapter4(@NonNull Context context, List<Usuarios>arrayusuarios) {
        super(context, listados_home2,arrayusuarios);
        this.context=context;
        this.arrayusuarios = arrayusuarios;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listados_home2, null,true);
        TextView txtID = view.findViewById(R.id.hora_fiesta);
        TextView txtNombre = view.findViewById(R.id.Nombre_fiesta);

        txtID.setText(arrayusuarios.get(position).getId());
        txtNombre.setText(arrayusuarios.get(position).getCodigo());

        return view;
    }
}
