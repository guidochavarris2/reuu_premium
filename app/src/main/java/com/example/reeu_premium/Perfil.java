package com.example.reeu_premium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

public class Perfil extends AppCompatActivity implements View.OnClickListener{
    //implements View.OnClickListener

    TextView userName,email,dni,gender;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        if(SharedPrefManager.getInstance(this).isLoggedIn()){

            userName = findViewById(R.id.textViewUsername);
            email = findViewById(R.id.textViewEmail);
            dni = findViewById(R.id.textViewDNI);
            gender = findViewById(R.id.textViewGender);


            btnLogout = findViewById(R.id.button2);
            User user = SharedPrefManager.getInstance(this).getUser();

            dni.setText(user.getDni());
            email.setText(user.getEmail());
            gender.setText(user.getGender());
            userName.setText(user.getName());



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