package com.example.reeu_premium;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetalleEventoAdmin extends AppCompatActivity {

    Button btnLista, btnValidar;
    String codigoa;
    TextView codigo;
    QR_detector QR_detector;

    //String codigo_string = codigo.getText().toString();
    public static ArrayList<com.example.reeu_premium.QR_detector> usuariosArrayListInvitado=new ArrayList<com.example.reeu_premium.QR_detector>();

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

        recibirdetalles();


        codigo = (TextView) findViewById(R.id.txtCodigo);
        System.out.println("esta vaina es pwwwwwwwwwwwwwwwwwwwwwwwwwww");

        System.out.println(codigo);
        //System.out.println(codigo_string);

        /**findViewById(R.id.lista_de_invitados).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), Lista_invitados.class));
            }**/


        btnValidar=(Button)findViewById(R.id.btnValidarIngreso);
        btnLista=(Button)findViewById(R.id.btnLista);

        btnLista.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent nextScreen = new Intent(getApplicationContext(), Lista_de_invitados.class);
                nextScreen.putExtra("codigo", codigo.getText());

                startActivity(nextScreen);


            }
        });

        btnValidar.setOnClickListener(view -> {
            scanCode();
        });

    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scanear tu código");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLaucher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result -> {
        if(result.getContents() !=null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(DetalleEventoAdmin.this);
            builder.setTitle("Resultado");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
    });

    protected void ScannerResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show();
            } else {
                String scanqr = result.getContents();
                invitado();
                for (int i = 0; i < usuariosArrayListInvitado.size(); i++) {
                    if (scanqr.equals( usuariosArrayListInvitado.get(i))){
                        Intent a = new Intent(DetalleEventoAdmin.this, Entrada_exitosa.class);
                        a.putExtra("scanqr", scanqr);
                        startActivity(a);
                    } else {
                        Intent a = new Intent(DetalleEventoAdmin.this, Entrada_denegada.class);
                        a.putExtra("scanqr", scanqr);
                        startActivity(a);
                    }
                }
            }
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

        //System.out.println("fewfewfwooooooooooooooooooo");
        //System.out.println(codigoe);



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
    private void invitado() {
        //first getting the values

        String principal = new String();
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            User user = SharedPrefManager.getInstance(this).getUser();
            final String id = String.valueOf(user.getId());
            System.out.println(id);

            final String username = "GUIDO";
            final String password = "dovermori";
            final String codiguin = codigoa;

            //validating inputs
            if (TextUtils.isEmpty(username)) {
                return;
            }
            if (TextUtils.isEmpty(password)) {
                return;
            }
            //if everything is fine
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_USUARIOS_INVITADOS,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //progressBar.setVisibility(View.GONE);

                            try {
                                //converting response to json object
                                JSONObject obj = new JSONObject(response);

                                //if no error in response
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                if (!obj.getBoolean("error")) {
                                    System.out.println("esta vainaaaaaaaaaaaaaaaaaa");
                                    System.out.println(obj);
                                    JSONArray userJson=obj.getJSONArray("nodo");
                                    System.out.println("ESTE ES EL JSONUSUARIO");
                                    System.out.println(userJson);

                                    for (int i = 0; i < userJson.length(); i++) {
                                        JSONObject animal = userJson.getJSONObject(i);
                                        //String id = animal.getString("id");
                                        String clave = animal.getString("clave");

                                        QR_detector  = new QR_detector(clave);
                                        usuariosArrayListInvitado.add(QR_detector);
                                    }

                                    System.out.println("ggaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                                    System.out.println(userJson);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", password);
                    params.put("codigo",codiguin);
                    return params;
                }
            };

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


        }
    }
}