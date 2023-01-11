package com.reeu.reeu_premium;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    TextView tvregister;
    EditText etName, etPassword;

    String variable = "Invalid username or password";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }


        etName = findViewById(R.id.editNombre);
        etPassword = findViewById(R.id.editPassword);

        tvregister=(TextView) findViewById(R.id.tvregister);

        //calling the method userLogin() for login the user
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        //if user presses on textview not register calling RegisterActivity
        findViewById(R.id.tvregister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), Registro.class));
            }
        });


        tvregister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent nextScreen = new Intent(getApplicationContext(), Registro.class);
                startActivity(nextScreen);

            }
        });
    }

    private void userLogin() {
        //first getting the values
        final String username = etName.getText().toString();
        final String password = etPassword.getText().toString();
        //validating inputs
        if (TextUtils.isEmpty(username)) {
            etName.setError("Por favor ingrese su usuario");
            etName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Por favor ingrese su contraseña");
            etPassword.requestFocus();
            return;
        }

        final ProgressDialog loading = ProgressDialog.show(this, "Iniciando Sesión...", "Espere por favor");

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                System.out.println("mensajeeeeeeeeeeeeeeeee");
                                System.out.println(obj.getString("message"));
                                AlertDialog.Builder alerta = new AlertDialog.Builder(Login.this);
                                alerta.setTitle("validacion");
                                alerta.setMessage("Datos incorrectos");

                                alerta.create().show();


                                /*if (variable == obj.getString("message")){
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(Login.this);
                                    alerta.setTitle("validacion");
                                    alerta.setMessage("Datos incorrectos");

                                    alerta.create().show();

                                }*/


                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("user");

                                //creating a new user object
                                User user = new User(
                                        userJson.getInt("id_usuario"),
                                        userJson.getString("username"),
                                        userJson.getString("apellidos"),
                                        userJson.getString("email"),
                                        userJson.getString("dni"),
                                        userJson.getString("gender")
                                );

                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                //starting the profile activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}