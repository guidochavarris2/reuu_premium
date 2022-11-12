package com.example.reeu_premium;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Codigo_QR_invitado extends AppCompatActivity {

    String codigo;
    //TextView edid;
    String clave;
    RequestQueue queue;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_qr_invitado);





        queue = Volley.newRequestQueue(this);

        Bundle bundle = getIntent().getExtras();

        //String valor_recibido = bundle.getString("codigo");
        String valor_recibido = bundle.getString("codigo");

        codigo = valor_recibido;

        recibirqr();

        System.out.println(valor_recibido);
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println(clave);
        System.out.println(codigo);

    }




    @RequiresApi(api = Build.VERSION_CODES.O)
    public void recibirqr() {
        Bundle extras = getIntent().getExtras();
        String hash = extras.getString("hashqr");
        clave = hash;
        System.out.println(hash + "  encriptado");
        try {
            hash = desencriptar(hash, "reeupremium");
            System.out.println(hash + "  no encriptado");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //CREAR QR
        ImageView imgQR = findViewById(R.id.imageQR);

        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(clave, BarcodeFormat.QR_CODE, 850, 850);

            imgQR.setImageBitmap(bitmap);
        }catch (Exception e) {
            e.printStackTrace();
        }
        //CREAR QR
        //agregado();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String encriptar(String code, String pss) throws Exception{
        SecretKeySpec secretKey = generateKey(pss);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] datosEncriptadosBytes = cipher.doFinal(code.getBytes());
        String datosEncriptadosString = Base64.getEncoder().encodeToString(datosEncriptadosBytes);
        return datosEncriptadosString;
    }
    private SecretKeySpec generateKey(String pss) throws  Exception{
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = pss.getBytes("UTF-8");
        key = sha.digest(key);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        return secretKey;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String desencriptar(String code, String pss) throws Exception{
        SecretKeySpec secretKey = generateKey(pss);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] datosDecodificados = Base64.getDecoder().decode(code);
        byte[] datosDesencriptados = cipher.doFinal(datosDecodificados);
        String datosDesencriptadosString = new String(datosDesencriptados);
        return datosDesencriptadosString;
    }

    private void agregado() {
        if(SharedPrefManager.getInstance(this).isLoggedIn()){

            User user = SharedPrefManager.getInstance(this).getUser();
            final String id = String.valueOf(user.getId());
            final String codiguin = codigo;
            final String clavecita = clave;


            final ProgressDialog progressDialog =new ProgressDialog(this);

            progressDialog.setMessage("Actualizando");
            progressDialog.dismiss();

            StringRequest request =new StringRequest(Request.Method.POST, URLs.URL_INGRESAR2, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(Codigo_QR_invitado.this, response, Toast.LENGTH_SHORT).show();

                    System.out.println(response);

                    //startActivity(new Intent(getApplicationContext(), Configuracion.class));
                    //finish();
                    progressDialog.dismiss();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Codigo_QR_invitado.this,error.getMessage(),Toast.LENGTH_SHORT).show();
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

    private void registerUser() {


        if(SharedPrefManager.getInstance(this).isLoggedIn()){


            User user = SharedPrefManager.getInstance(this).getUser();

            final String id = String.valueOf(user.getId());

            final String codiguin = codigo;
            final String clavecita = clave;
            final ProgressDialog loading = ProgressDialog.show(this, "Ingresando...", "Espere por favor");



            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_INGRESAR,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            loading.dismiss();
                            System.out.println(response);

                            try {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                //converting response to json object
                                JSONObject obj = new JSONObject(response);
                                System.out.println("resultadooooooooooooooooooooooo");
                                System.out.println(obj);
                                //if no error in response
                                if (!obj.getBoolean("error")) {
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                    //getting the user from the response
                                    JSONObject userJson = obj.getJSONObject("user");

                                    //creating a new user object
                                    User user = new User(
                                            userJson.getInt("id_evento"),
                                            userJson.getString("nombre"),
                                            userJson.getString("descripcion"),
                                            userJson.getString("aforo"),
                                            userJson.getString("fecha"),
                                            userJson.getString("hora")
                                    );

                                    //storing the user in shared preferences
                                    //SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                    //starting the profile activity
                                    //finish();
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
                            loading.dismiss();
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    System.out.println("gaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

                    Map<String, String> params = new HashMap<>();
                    params.put("id_usuario",id);
                    params.put("id_evento",codigo);
                    params.put("clave",clavecita);

                    return params;
                }
            };

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }



    public void back_home(View view){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}