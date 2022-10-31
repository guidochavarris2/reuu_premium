package com.example.reeu_premium;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DetalleEvento extends AppCompatActivity {

    String dato1;
    String dato2;
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
        TextView Codigo = findViewById(R.id.txtCodigo);

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
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    buscarIDEvento();
                                    dato2 = Codigo.getText().toString().trim();
                                    combo = dato1 + dato2;
                                    System.out.println(dato1);
                                    combo = encriptar(combo);

                                    Intent e = new Intent(DetalleEvento.this, Codigo_QR_invitado.class);
                                    e.putExtra("hashqr", combo);
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