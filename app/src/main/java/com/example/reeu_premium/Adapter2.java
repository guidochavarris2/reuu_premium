package com.example.reeu_premium;

import static com.example.reeu_premium.R.layout.listados_home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
public class Adapter2 extends ArrayAdapter<Usuarios> {
    Context context;
    List<Usuarios> arrayusuarios;

    public Adapter2(@NonNull Context context, List<Usuarios>arrayusuarios) {
        super(context, listados_home,arrayusuarios);
        this.context=context;
        this.arrayusuarios = arrayusuarios;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listados_home, null,true);
        TextView txtID = view.findViewById(R.id.txtid);
        TextView txtNombre = view.findViewById(R.id.txtubicacion);
        //TextView txtCategoria = view.findViewById(R.id.txtubicacion);
        ImageView txtImage = view.findViewById(R.id.imagenEvento);



        txtID.setText(arrayusuarios.get(position).getId());
        txtNombre.setText(arrayusuarios.get(position).getCodigo());
        //txtImage.setImageResource(arrayusuarios.get(position).getimagen());
        //txtCategoria.setText(arrayusuarios.get(position).getCategoria());

        return view;
    }


}