package com.example.reeu_premium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DetalleEventoIngresado extends AppCompatActivity {

    Button btnQR;
    String dato1;
    String dato2;
    String combo;

    TextView codigo;
    private static final String AES = "AES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_evento_ingresado);

        Button ingreso = findViewById(R.id.btnIngreso);
        TextView Codigo = findViewById(R.id.txtCodigo);

        recibirdetalles();


        //guido
        btnQR=(Button)findViewById(R.id.btnIngreso);

        codigo = (TextView) findViewById(R.id.txtCodigo);
        /*

        btnQR.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent nextScreen = new Intent(getApplicationContext(), Codigo_QR_invitado.class);
                nextScreen.putExtra("codigo", codigo.getText());
                nextScreen.putExtra("clave", "�\u0002�\"��m��;YZeV8_");

                startActivity(nextScreen);


            }
        });*/

        ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    dato2 = Codigo.getText().toString().trim();
                    combo = dato1 + dato2;
                    System.out.println(dato1);
                    combo = encriptar(combo);


                    Intent i = new Intent(DetalleEventoIngresado.this, Codigo_QR_invitado.class);
                    i.putExtra("codigo", codigo.getText());
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
            Intent  intent = new Intent(DetalleEventoIngresado.this,Login.class);
            startActivity(intent);
            finish();
        }

    }
    public void recibirdetalles() {
        Bundle extras = getIntent().getExtras();
        String id_evento = extras.getString("envio1");
        String nombre = extras.getString("envio2");
        String descripcion = extras.getString("envio3");
        String aforo = extras.getString("envio4");
        String fecha = extras.getString("envio5");
        String hora = extras.getString("envio6");
        String ubicacion = extras.getString("envio7");
        String imagen = extras.getString("envio8");
        String tipo_evento = extras.getString("envio10");

        TextView nombree = findViewById(R.id.txtnombreEvento);
        EditText descripcione = findViewById(R.id.txtDescripcion);
        EditText ubicacione = findViewById(R.id.txtubicacion);
        EditText fechae = findViewById(R.id.txtfecha);
        EditText horae = findViewById(R.id.txthora);
        EditText aforomaxe = findViewById(R.id.txtaforomax);
        EditText aforoe = findViewById(R.id.txtaforo);
        TextView estadoe = findViewById(R.id.txtEstadoEvento);
        TextView codigoe = findViewById(R.id.txtCodigo);
        nombree.setText(nombre);
        descripcione.setText(descripcion);
        ubicacione.setText(ubicacion);
        fechae.setText(fecha);
        horae.setText(hora);
        aforomaxe.setText(aforo);
        aforoe.setText("0");
        if(tipo_evento == "1"){
            estadoe.setText("Publico");
        }else{
            estadoe.setText("Privado");
        }
        codigoe.setText(id_evento);

        //EditText Deshabilitados
        descripcione.setEnabled(false);
        ubicacione.setEnabled(false);
        fechae.setEnabled(false);
        horae.setEnabled(false);
        aforomaxe.setEnabled(false);
        aforoe.setEnabled(false);
    }
}