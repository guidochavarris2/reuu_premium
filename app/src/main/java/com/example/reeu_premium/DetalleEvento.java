package com.example.reeu_premium;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DetalleEvento extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_evento);
        EditText Descripcion = findViewById(R.id.txtDescripcion);
        EditText Ubicacion = findViewById(R.id.txtubicacion);
        EditText Fecha = findViewById(R.id.txtfecha);
        EditText Hora = findViewById(R.id.txthora);
        EditText AforoMax = findViewById(R.id.txtaforomax);
        EditText Aforo = findViewById(R.id.txtaforo);

        TextView Estado = findViewById(R.id.txtEstadoEvento);
        TextView Nombre = findViewById(R.id.txtnombreEvento);

        ImageView imagen = findViewById(R.id.imageEvento);

        //EditText Deshabilitados
        Descripcion.setEnabled(false);
        Ubicacion.setEnabled(false);
        Fecha.setEnabled(false);
        Hora.setEnabled(false);
        AforoMax.setEnabled(false);
        Aforo.setEnabled(false);
    }
}