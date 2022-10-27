package com.example.reeu_premium;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DetalleEvento extends AppCompatActivity {

    String dato1;
    String dato2 = "454564";
    String combo;
    private static final String AES = "AES";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_evento);
        EditText Descripcion = findViewById(R.id.txtDescripcion);
        EditText Ubicacion = findViewById(R.id.txtubicacion);
        EditText Fecha = findViewById(R.id.txtfecha);
        EditText Hora = findViewById(R.id.txthora);
        EditText AforoMax = findViewById(R.id.txtaforomax);
        EditText Aforo = findViewById(R.id.txtaforo);

        TextView Estado = findViewById(R.id.txtEstadoEvento);
        TextView Nombre = findViewById(R.id.txtnombreEvento);

        ImageView imagen = findViewById(R.id.imageEvento);

        Button ingreso = findViewById(R.id.btnIngreso);

        //EditText Deshabilitados
        Descripcion.setEnabled(false);
        Ubicacion.setEnabled(false);
        Fecha.setEnabled(false);
        Hora.setEnabled(false);
        AforoMax.setEnabled(false);
        Aforo.setEnabled(false);

        ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    combo = dato1 + dato2;
                    combo = encriptar(combo);


                    Intent i = new Intent(DetalleEvento.this, Codigo_QR_invitado.class);
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
}