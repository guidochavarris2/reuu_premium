package com.example.reeu_premium;

import static com.android.volley.Request.Method.GET;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class Editar_perfil extends AppCompatActivity {


    TextView edid, eddni, edapellidos, edgender;
    EditText edemail, eduserName, edpassword;

    private int position;


    //RadioGroup radioGroupGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        if(SharedPrefManager.getInstance(this).isLoggedIn()) {


            edid = findViewById(R.id.editTextId);
            eddni = findViewById(R.id.editTextDni);
            eduserName = findViewById(R.id.editTextUsername);
            edapellidos = findViewById(R.id.editTextApellidos);
            edemail = findViewById(R.id.editTextEmail);
            edpassword = findViewById(R.id.editTextPassword);
            edgender = findViewById(R.id.editTextGenero);


            User user = SharedPrefManager.getInstance(this).getUser();

            edid.setText(String.valueOf(user.getId()));
            eddni.setText(user.getDni());
            eduserName.setText(user.getName());
            edapellidos.setText(user.getApellidos());
            edemail.setText(user.getEmail());

            edgender.setText(user.getGender());



        }
    }


    public void actualizar(View view){

        final String id =edid.getText().toString().trim();
        final String dni =eddni.getText().toString().trim();
        final String username =eduserName.getText().toString().trim();
        final String apellidos =edapellidos.getText().toString().trim();
        final String email=edemail.getText().toString().trim();
        final String password=edpassword.getText().toString().trim();
        final String genero=edgender.getText().toString().trim();


        final ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Actualizando");
        progressDialog.dismiss();

        StringRequest request =new StringRequest(Request.Method.POST, URLs.URL_EDITAR, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Editar_perfil.this, response, Toast.LENGTH_SHORT).show();

                System.out.println(response);

                startActivity(new Intent(getApplicationContext(), Configuracion.class));
                finish();
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Editar_perfil.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("dni",dni);
                params.put("username",username);
                params.put("apellidos",apellidos);
                params.put("email",email);
                params.put("password",password);
                params.put("gender",genero);
                params.put("id_usuario",id);

                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(Editar_perfil.this);
        requestQueue.add(request);
    }

}
