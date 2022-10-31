package com.example.reeu_premium;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DetalleEvento1 extends AppCompatActivity {
    String dato1;
    String dato2;
    String combo;
    Button btnQR;

    TextView codigo;
    private static final String AES = "AES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_evento1);

        Button ingreso = findViewById(R.id.btnIngreso);
        TextView Codigo = findViewById(R.id.txtCodigo);

        detallebusqueda();

        //guido


        codigo = (TextView) findViewById(R.id.txtCodigo);



        ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    buscarIDEvento();
                    dato2 = Codigo.getText().toString().trim();
                    combo = dato1 + dato2;
                    System.out.println(dato1);
                    combo = encriptar(combo);

                    Intent i = new Intent(DetalleEvento1.this, Codigo_QR_invitado.class);
                    i.putExtra("codigo", codigo.getText());
                    i.putExtra("hashqr", combo);
                    agregado();
                    startActivity(i);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void agregado() {
        if(SharedPrefManager.getInstance(this).isLoggedIn()){

            User user = SharedPrefManager.getInstance(this).getUser();
            final String id = String.valueOf(user.getId());
            //final String codiguin = String.valueOf();
            final String codiguin = codigo.getText().toString();
            final String clavecita = combo;
            //final String username = .getText().toString();
            //final String clavecita = clave;


            final ProgressDialog progressDialog =new ProgressDialog(this);

            progressDialog.setMessage("Actualizando");
            progressDialog.dismiss();

            StringRequest request =new StringRequest(Request.Method.POST, URLs.URL_INGRESAR2, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(DetalleEvento1.this, response, Toast.LENGTH_SHORT).show();

                    System.out.println(response);

                    //startActivity(new Intent(getApplicationContext(), Configuracion.class));
                    //finish();
                    progressDialog.dismiss();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(DetalleEvento1.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<>();
                    params.put("id_usuario",id);
                    params.put("id_evento",codiguin);
                    params.put("clave",clavecita);


                    return params;
                }
            };
            //RequestQueue requestQueue= Volley.newRequestQueue(Codigo_QR_invitado.this);
            //requestQueue.add(request);
            VolleySingleton.getInstance(this).addToRequestQueue(request);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);

        }
    }


    public static String encriptar(String code) throws Exception{
        KeyGenerator KeyGenerator = javax.crypto.KeyGenerator.getInstance(AES);
        KeyGenerator.init(128);
        SecretKey secretKey = KeyGenerator.generateKey();
        byte[] bytesSecretKey = secretKey.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(bytesSecretKey, AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encriptado = cipher.doFinal(code.getBytes());
        return new String(encriptado);
    }

    //usuario logueado DNI
    private void buscarIDEvento() {

        if(SharedPrefManager.getInstance(this).isLoggedIn()) {
            User user = SharedPrefManager.getInstance(this).getUser();
            dato1 = user.getDni();
        } else {
            Intent  intent = new Intent(DetalleEvento1.this,Login.class);
            startActivity(intent);
            finish();
        }

    }


    public void detallebusqueda() {
        Bundle extras2 = getIntent().getExtras();
        if (extras2 != null) {
            String value = extras2.getString("key");
            String value2 = extras2.getString("key2");
            String value3 = extras2.getString("key3");
            String value4 = extras2.getString("key4");
            String value5 = extras2.getString("key5");
            String value6 = extras2.getString("key6");
            String value7 = extras2.getString("key7");
            String value8 = extras2.getString("key8");
            String value9 = extras2.getString("key9");


            TextView name = findViewById(R.id.txtnombreEvento);
            TextView des = findViewById(R.id.txtDescripcion);
            TextView ubi = findViewById(R.id.txtubicacion);
            TextView date = findViewById(R.id.txtfecha);
            TextView hour = findViewById(R.id.txthora);
            TextView cap = findViewById(R.id.txtaforomax);
            TextView cap1 = findViewById(R.id.txtaforo);
            TextView est = findViewById(R.id.txtEstadoEvento);
            TextView cod = findViewById(R.id.txtCodigo);
            name.setText(value);
            des.setText(value2);
            ubi.setText(value3);
            date.setText(value4);
            hour.setText(value5);
            cap.setText(value6);
            cap1.setText("0");

            if(value7 == "1"){
                est.setText("Publico");
            }else{
                est.setText("Privado");
            }
            cod.setText(value8);

            //EditText Deshabilitados
            des.setEnabled(false);
            ubi.setEnabled(false);
            date.setEnabled(false);
            hour.setEnabled(false);
            cap.setEnabled(false);
            cap1.setEnabled(false);
        }
    }
}