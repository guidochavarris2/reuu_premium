package com.example.reeu_premium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class DetalleEventoAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_evento_admin);

        EditText Descripcion = findViewById(R.id.txtDescripcion);
        EditText Ubicacion = findViewById(R.id.txtubicacion);
        EditText Fecha = findViewById(R.id.txtfecha);
        EditText Hora = findViewById(R.id.txthora);
        EditText AforoMax = findViewById(R.id.txtaforomax);
        EditText Aforo = findViewById(R.id.txtaforo);

        TextView Estado = findViewById(R.id.txtEstadoEvento);
        TextView Nombre = findViewById(R.id.txtnombreEvento);

        ImageView imagen = findViewById(R.id.imageEvento);

        //EditText Deshabilitados
        Descripcion.setEnabled(false);
        Ubicacion.setEnabled(false);
        Fecha.setEnabled(false);
        Hora.setEnabled(false);
        AforoMax.setEnabled(false);
        Aforo.setEnabled(false);

        /**findViewById(R.id.lista_de_invitados).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), Lista_invitados.class));
            }**/
    }
    public void onClick(View view) {
        if(view.getId() == R.id.btnValidarIngreso){
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.PDF_417);
            integrator.setCameraId(0);  // Use a specific camera of the device
            integrator.setOrientationLocked(true);
            integrator.setBarcodeImageEnabled(false);
            integrator.initiateScan();
        }
    }
    protected void ScannerResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show();
            } else {
                String scanqr = result.getContents();
                boolean verif = true;




                //falta codigo//



                if(verif != true){
                    Intent i = new Intent(DetalleEventoAdmin.this, Entrada_denegada.class);
                    i.putExtra("scanqr", scanqr);
                    startActivity(i);
                } else {
                    Intent i = new Intent(DetalleEventoAdmin.this, Entrada_exitosa.class);
                    i.putExtra("scanqr", scanqr);
                    startActivity(i);
                }
            }
        }
    }
}