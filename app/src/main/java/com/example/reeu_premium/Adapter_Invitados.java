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

public class Adapter_Invitados extends ArrayAdapter<Usuario5> {
    Context context;
    List<Usuario5> arrayusuarios;

    public Adapter_Invitados(@NonNull Context context, List<Usuario5>arrayusuarios) {
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

        txtID.setText(arrayusuarios.get(position).getUsername());
        txtNombre.setText(arrayusuarios.get(position).getApellidos());

        return view;
    }
}