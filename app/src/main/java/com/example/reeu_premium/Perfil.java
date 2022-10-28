package com.example.reeu_premium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Perfil extends AppCompatActivity implements View.OnClickListener{

    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        if(SharedPrefManager.getInstance(this).isLoggedIn()){

            btnLogout = findViewById(R.id.button2);
            User user = SharedPrefManager.getInstance(this).getUser();



            btnLogout.setOnClickListener(this);
        }
        else{
            Intent intent = new Intent(Perfil.this,Login.class);
            startActivity(intent);
            finish();
        }
    }

    public void onClick(View view){
        if(view.equals(btnLogout)){
            SharedPrefManager.getInstance(getApplicationContext()).logout();
        }
    }
}