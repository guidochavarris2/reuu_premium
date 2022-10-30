package com.example.reeu_premium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DetalleEventoIngresado extends AppCompatActivity {

    Button btnQR;

    TextView codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_evento_ingresado);

        recibirdetalles();

        //guido
        btnQR=(Button)findViewById(R.id.btnIngreso);

        codigo = (TextView) findViewById(R.id.txtCodigo);

        btnQR.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent nextScreen = new Intent(getApplicationContext(), Codigo_QR_invitado.class);
                nextScreen.putExtra("codigo", codigo.getText());

                startActivity(nextScreen);


            }
        });
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