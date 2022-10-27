package com.example.reeu_premium;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Codigo_QR_invitado extends AppCompatActivity {

    public void recibirqr() {
        Bundle extras = getIntent().getExtras();
        String hash = extras.getString("hashqr");

        ImageView imgQR = findViewById(R.id.imageQR);

        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(hash, BarcodeFormat.QR_CODE, 750, 750);

            imgQR.setImageBitmap(bitmap);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_qr_invitado);

        recibirqr();
    }
}