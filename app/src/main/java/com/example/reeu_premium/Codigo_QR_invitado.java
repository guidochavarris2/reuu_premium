package com.example.reeu_premium;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Codigo_QR_invitado extends AppCompatActivity {

    String codigo;
    RequestQueue queue;


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
    }




    public void recibirqr() {
        Bundle extras = getIntent().getExtras();
        String hash = extras.getString("hashqr");

        //CREAR QR
        ImageView imgQR = findViewById(R.id.imageQR);

        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(hash, BarcodeFormat.QR_CODE, 850, 850);

            imgQR.setImageBitmap(bitmap);
        }catch (Exception e) {
            e.printStackTrace();
        }
        //CREAR QR
    }



    public void back_home(View view){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}