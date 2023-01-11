package com.reeu.reeu_premium;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Entrada_denegada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada_denegada);
        Bundle extras = getIntent().getExtras();
        String qr = extras.getString("scanqr");

        System.out.println(qr + "----------- qr del usuario ---------");

        ImageView imgqr = findViewById(R.id.imageQR);

        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(qr, BarcodeFormat.QR_CODE, 850, 850);

            imgqr.setImageBitmap(bitmap);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}