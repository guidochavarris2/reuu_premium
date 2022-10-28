package com.example.reeu_premium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Configuracion extends AppCompatActivity {

    Button tbnTerminos;
    Button btnPoliticas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        tbnTerminos=(Button)findViewById(R.id.tbnTerminos);

        tbnTerminos.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent nextScreen = new Intent(getApplicationContext(), Terminos_condiciones.class);
                startActivity(nextScreen);

            }
        });

        btnPoliticas=(Button)findViewById(R.id.btnPoliticas);

        btnPoliticas.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent nextScreen = new Intent(getApplicationContext(), Privacidad.class);
                startActivity(nextScreen);

            }
        });

    }
}