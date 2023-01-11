package com.reeu.reeu_premium;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Buscador extends AppCompatActivity {

    EditText edtEvent, edtCod, edtdescripcion, edtubicacion,edtfecha,edthora,edtaforo,edtestado;
    Button btnCod;

    RequestQueue requestQueue;
    public static ArrayList<Usuarios> usuariosArrayList2=new ArrayList<>();
    Usuarios usuarios;
    Adapter2 adapter2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);
        edtEvent = (EditText)findViewById(R.id.edtEvent);
        edtdescripcion = (EditText)findViewById(R.id.edtdescripcion);
        edtubicacion = (EditText)findViewById(R.id.edtubicacion);
        edtfecha = (EditText)findViewById(R.id.edtfecha);
        edthora = (EditText)findViewById(R.id.edthora);
        edtaforo = (EditText)findViewById(R.id.edtaforo);
        edtestado = (EditText)findViewById(R.id.edtestado);

        edtCod = (EditText)findViewById(R.id.edtCod);
        btnCod = (Button)findViewById(R.id.btnCod);

        Button Crear_Evento = findViewById(R.id.btn_crearevento);

        Crear_Evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Buscador.this, CrearEvento.class);
                startActivity(intent);
            }
        });

        btnCod.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                buscarEvento("https://polar-cove-80223.herokuapp.com/busqueda.php?codigo="+edtCod.getText()+"");
            }
        });
    }

    private void buscarEvento(String URL) {
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i= 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        //edtEvent.setText(jsonObject.getString("producto"));
                        edtEvent.setText(jsonObject.getString("nombre"));
                        edtdescripcion.setText(jsonObject.getString("descripcion"));
                        edtubicacion.setText(jsonObject.getString("ubicacion"));
                        edtfecha.setText(jsonObject.getString("fecha"));
                        edthora.setText(jsonObject.getString("hora"));
                        edtaforo.setText(jsonObject.getString("aforo"));
                        edtestado.setText(jsonObject.getString("estado"));
                        edtCod.setText(jsonObject.getString("id_evento"));

                        String value2 = edtEvent.getText().toString();
                        String value3 = edtdescripcion.getText().toString();
                        String value4 = edtubicacion.getText().toString();
                        String value5 = edtfecha.getText().toString();
                        String value6 = edthora.getText().toString();
                        String value7 = edtaforo.getText().toString();
                        String value8 = edtestado.getText().toString();
                        String value9 = edtCod.getText().toString();

                        System.out.println("esto se enviaaa");
                        System.out.println(value2);

                        System.out.println("esto es otro envio");
                        System.out.println(value3);
                        Intent in = new Intent(Buscador.this, DetalleEvento1.class);
                        in.putExtra("key",value2);
                        in.putExtra("key2",value3);
                        in.putExtra("key3",value4);
                        in.putExtra("key4",value5);
                        in.putExtra("key5", value6);
                        in.putExtra("key6", value7);
                        in.putExtra("key7", value8);
                        in.putExtra("key8", value9);
                        startActivity(in);

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alerta = new AlertDialog.Builder(Buscador.this);
                alerta.setTitle("Nota");
                alerta.setMessage("No encontramos el codigo del evento :(");

                alerta.create().show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }
}