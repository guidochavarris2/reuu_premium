package com.reeu.reeu_premium;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class DetalleEvento extends AppCompatActivity  {

    String dato1;
    String dato2;
    String combo;
    String codiguero;
    TextView codigo;

    private static final String AES = "AES";

    private ImageView cover;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        codigo = (TextView) findViewById(R.id.txtCodigo);

        setContentView(R.layout.activity_detalle_evento);
        EditText Descripcion = findViewById(R.id.txtDescripcion);
        EditText Ubicacion = findViewById(R.id.txtubicacion);
        EditText Fecha = findViewById(R.id.txtfecha);
        EditText Hora = findViewById(R.id.txthora);
        EditText AforoMax = findViewById(R.id.txtaforomax);
        EditText Aforo = findViewById(R.id.txtaforo);

        TextView Estado = findViewById(R.id.txtEstadoEvento);
        TextView Nombre = findViewById(R.id.txtnombreEvento);
        TextView Codigo = findViewById(R.id.txtCodigo);


        System.out.println("gaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println(Codigo);

        ImageView imagen = findViewById(R.id.imageEvento);

        Button ingreso = findViewById(R.id.btnIngreso);

        recibirdetalles();



        ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(DetalleEvento.this);
                alerta.setTitle("Validación");
                alerta.setMessage("Gracias por confirmar su participación en el evento")
                        .setCancelable(false)
                        .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    buscarIDEvento();
                                    dato2 = Codigo.getText().toString().trim();
                                    combo = dato1 + dato2;
                                    String encrp = encriptar(combo, "reeupremium");

                                    Intent e = new Intent(DetalleEvento.this, Codigo_QR_invitado.class);
                                    e.putExtra("hashqr", encrp);
                                    combo = encrp;
                                    agregado();
                                    startActivity(e);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }
                        });
                alerta.create().show();

            }
        });
    }

    private void agregado() {
        if(SharedPrefManager.getInstance(this).isLoggedIn()){

            User user = SharedPrefManager.getInstance(this).getUser();
            final String id = String.valueOf(user.getId());
            //final String codiguin = String.valueOf();
            final String codiguin = dato2;
            final String clavecita = combo;

            //final String username = .getText().toString();
            //final String clavecita = clave;


            //final ProgressDialog progressDialog =new ProgressDialog(this);

            //progressDialog.setMessage("Actualizando");
            //progressDialog.dismiss();

            StringRequest request =new StringRequest(Request.Method.POST, URLs.URL_UNISE_EVENTO, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(DetalleEvento.this, response, Toast.LENGTH_SHORT).show();
                    System.out.println(response);
                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(response);

                        //System.out.println(obj);
                        //if no error in response
                        if (!obj.getBoolean("error")) {
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            System.out.println(obj.getString("message"));





                            //starting the profile activity
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            System.out.println(obj.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                    //startActivity(new Intent(getApplicationContext(), Configuracion.class));
                    //finish();
                    //progressDialog.dismiss();



            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(DetalleEvento.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    //progressDialog.dismiss();
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
            //RequestQueue requestQueue = Volley.newRequestQueue(this);
            //requestQueue.add(request);

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String encriptar(String code, String pss) throws Exception{
        SecretKeySpec secretKey = generateKey(pss);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
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

    //usuario logueado DNI
    private void buscarIDEvento() {

        if(SharedPrefManager.getInstance(this).isLoggedIn()) {
            User user = SharedPrefManager.getInstance(this).getUser();
            dato1 = user.getDni();
        } else {
            Intent  intent = new Intent(DetalleEvento.this,Login.class);
            startActivity(intent);
            finish();
        }

    }


    public void recibirdetalles() {
        Usuarios element = (Usuarios) getIntent().getSerializableExtra("item");

        TextView nombree = findViewById(R.id.txtnombreEvento);
        EditText descripcione = findViewById(R.id.txtDescripcion);
        EditText ubicacione = findViewById(R.id.txtubicacion);
        EditText fechae = findViewById(R.id.txtfecha);
        EditText horae = findViewById(R.id.txthora);

        EditText aforomaxe = findViewById(R.id.txtaforomax);
        EditText aforoe = findViewById(R.id.txtaforo);
        TextView estadoe = findViewById(R.id.txtEstadoEvento);
        TextView codigoe = findViewById(R.id.txtCodigo);
        cover = findViewById(R.id.imageEvento);
        Glide.with(this)
                .load(element.getimagen())
                .into(cover);

        nombree.setText(element.getId());
        descripcione.setText(element.getCategoria());
        ubicacione.setText(element.getubicacion());
        //imagene.setText(element.getimagen());
        fechae.setText(element.getfecha());
        horae.setText(element.gethora());
        aforomaxe.setText(element.getCodigo());
        aforoe.setText("0");
        if(element.getestado() == "1"){
            estadoe.setText("Publico");
        }else{
            estadoe.setText("Privado");
        }
        codigoe.setText(element.getid_evento());

        //EditText Deshabilitados
        descripcione.setEnabled(false);
        ubicacione.setEnabled(false);
        fechae.setEnabled(false);
        horae.setEnabled(false);
        aforomaxe.setEnabled(false);
        aforoe.setEnabled(false);
    }


}