package com.reeu.reeu_premium;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class DetalleEventoIngresado extends AppCompatActivity {

    Button btnQR;
    String dato1;
    String dato2;
    String combo;
    private ImageView cover;

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
        buscarIDEvento();
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                try {

                    dato2 = Codigo.getText().toString().trim();
                    combo = dato1 + dato2;
                    String encrp = encriptar(combo, "reeupremium");

                    Intent i = new Intent(DetalleEventoIngresado.this, Codigo_QR_invitado.class);
                    i.putExtra("codigo", codigo.getText());
                    i.putExtra("hashqr", encrp);

                    startActivity(i);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
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