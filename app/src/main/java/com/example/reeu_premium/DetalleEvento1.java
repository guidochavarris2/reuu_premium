package com.example.reeu_premium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DetalleEvento1 extends AppCompatActivity {
    String dato1;
    String dato2;
    String combo;
    private static final String AES = "AES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_evento1);

        Button ingreso = findViewById(R.id.btnIngreso);
        TextView Codigo = findViewById(R.id.txtCodigo);

        detallebusqueda();

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
                    i.putExtra("hashqr", combo);
                    startActivity(i);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
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