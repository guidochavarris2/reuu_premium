package com.example.reeu_premium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Principal extends AppCompatActivity {

    Button btnempezar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        btnempezar=(Button)findViewById(R.id.btnempezar);

        btnempezar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent nextScreen = new Intent(getApplicationContext(), Login.class);
                startActivity(nextScreen);

            }
        });

    }
}