package com.example.reeu_premium;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ImageSlider imageSlider;
    //Adapter2 adapter2;
    //Adapter adapter2;


    List<Usuarios>productosList;


    RequestQueue queue;
    RecyclerView recyclerView;

    SwipeRefreshLayout swipeRefreshLayout;

    public static ArrayList<Usuarios> usuariosArrayList2=new ArrayList<>();
    String url="https://polar-cove-80223.herokuapp.com/estacionamientos.php";
    Usuarios usuarios;

    Button siguiente;

    ImageView buttonLogout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);
        //buttonLogout = (Button)findViewById(R.id.buttonLogout);
        imageSlider = findViewById(R.id.image_slider);
        buttonLogout = (ImageView)findViewById(R.id.imagenEntrada);
        FloatingActionButton Boton_Agregar = findViewById(R.id.agregar_evento);

        //listView=findViewById(R.id.recy);
        recyclerView=findViewById(R.id.recy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productosList=new ArrayList<>();

        userLogin3();



        //cargargarImagen();
        /*recyclerView=findViewById(R.id.recy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter2= new Adapter(this,usuariosArrayList2);
        recyclerView.setAdapter(adapter2);*/



        /*RecyclerView.ViewHolder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Usuarios a = productosList.get(i);
                String envio1 = a.id_evento;String envio2 = a.id;String envio3 = a.categoria;
                String envio4 = a.codigo;String envio5 = a.fecha;String envio6 = a.hora;
                String envio7 = a.ubicacion;String envio8 = a.imagen;String envio9 = a.estado;
                String envio10 = a.id_tipo_evento;String envio11 = a.id_usuario;

                Intent intent = new Intent(MainActivity.this, DetalleEvento.class);
                intent.putExtra("envio1", envio1);
                intent.putExtra("envio2", envio2);
                intent.putExtra("envio3", envio3);
                intent.putExtra("envio4", envio4);
                intent.putExtra("envio5", envio5);
                intent.putExtra("envio6", envio6);
                intent.putExtra("envio7", envio7);
                intent.putExtra("envio8", envio8);
                intent.putExtra("envio9", envio9);
                intent.putExtra("envio10", envio10);
                intent.putExtra("envio11", envio11);
                startActivity(intent);
            }

        });*/

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Eventos.class);
                startActivity(intent);
            }
        });
        Boton_Agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Buscador.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Perfil.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.entry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Eventos.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.config).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Configuracion.class);
                startActivity(intent);
            }
        });

        /*buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Home_2.class);
                startActivity(intent);
            }
        });*/

        //leerJSON2();
        //userLogin2();
/*

        if(SharedPrefManager.getInstance(this).isLoggedIn()){


            User user = SharedPrefManager.getInstance(this).getUser();
            buttonLogout.setOnClickListener(this);
        }
        else{
            Intent intent = new Intent(Home.this,Login.class);
            startActivity(intent);
            finish();
        }*/

        //agregando slider de imagenes
        ArrayList<SlideModel> images = new ArrayList<>();
        images.add(new SlideModel(R.drawable.cumple, null));
        images.add(new SlideModel(R.drawable.conferencia, null));
        images.add(new SlideModel(R.drawable.concierto, null));
        images.add(new SlideModel(R.drawable.compromisos, null));
        imageSlider.setImageList(images, ScaleTypes.CENTER_CROP);

    }








    private void userLogin3() {
        //first getting the values

        String principal = new String();
        if(SharedPrefManager.getInstance(this).isLoggedIn()){


            User user = SharedPrefManager.getInstance(this).getUser();

            final String id = String.valueOf(user.getId());

            //principal.append(id);


            System.out.println("este es mi iddddddddddddddddddddddddddddd");
            System.out.println(id);

            final String username = "GUIDO";
            final String password = "dovermori";
            //final Integer id = 1;
            //final String id = principal;
            //final StringBuffer id = principal;


            //validating inputs
            if (TextUtils.isEmpty(username)) {

                return;
            }

            if (TextUtils.isEmpty(password)) {

                return;
            }

            //if everything is fine
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_PUBLICO,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //progressBar.setVisibility(View.GONE);

                            try {
                                //converting response to json object
                                JSONObject obj = new JSONObject(response);

                                //if no error in response
                                if (!obj.getBoolean("error")) {
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                    //JSONArray agenteJSON=response.getJSONArray("datos");
                                    //getting the user from the response

                                    System.out.println("esta vainaaaaaaaaaaaaaaaaaa");
                                    System.out.println(obj);
                                    //JSONObject userJson = obj.getJSONObject("nodo");
                                    JSONArray userJson=obj.getJSONArray("nodo");

                                    System.out.println("ESTE ES EL JSONUSUARIO");

                                    System.out.println(userJson);


                                    for (int i = 0; i < userJson.length(); i++) {

                                        String numcadena = String.valueOf(i);

                                        JSONObject Usuarios = userJson.getJSONObject(i);



                                        productosList.add(new Usuarios(
                                                Usuarios.getString("nombre"),
                                                Usuarios.getString("aforo"),
                                                Usuarios.getString("descripcion"),
                                                Usuarios.getString("id_evento"),
                                                Usuarios.getString("fecha"),
                                                Usuarios.getString("hora"),
                                                Usuarios.getString("ubicacion"),
                                                Usuarios.getString("imagen"),
                                                Usuarios.getString("estado"),
                                                Usuarios.getString("id_tipo_evento"),
                                                Usuarios.getString("id_usuario")
                                        ));

                                        String id = Usuarios.getString("nombre");
                                        String id_evento = Usuarios.getString("id_evento");
                                        String fecha = Usuarios.getString("fecha");
                                        String hora = Usuarios.getString("hora");
                                        String ubicacion = Usuarios.getString("ubicacion");
                                        String imagen = Usuarios.getString("imagen");
                                        String estado = Usuarios.getString("estado");
                                        String tipo_evento = Usuarios.getString("id_tipo_evento");
                                        String id_usuario = Usuarios.getString("id_usuario");
                                        //listaid.add(id);
                                        String codigo = Usuarios.getString("aforo");
                                        String categoria = Usuarios.getString("descripcion");

                                        System.out.println(id + ", " + codigo + ", " + categoria);



                                        usuarios = new Usuarios(id,codigo, categoria, id_evento, fecha, hora, ubicacion, imagen, estado, tipo_evento, id_usuario);
                                        usuariosArrayList2.add(usuarios);


                                    }

                                    Adapter adapter = new Adapter(MainActivity.this, productosList, new RecyclerViewAdapter.ItemClickListener() {
                                        @Override
                                        public void onItemClick(Usuarios item) {
                                            ToDetalleEvento(item);
                                        }
                                    });
                                    recyclerView.setAdapter(adapter);



                                } else {
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
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
                    params.put("id_usuario",id);
                    //params.put("id", String.valueOf(id));
                    //params.put("id",id);
                    return params;
                }
            };

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
            Volley.newRequestQueue(this).add(stringRequest);


        }
    }




    private HashMap<String, Object> getHashMapFromJson(String json) throws JSONException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        JSONObject jsonObject = new JSONObject(json);
        for (Iterator<String> it = jsonObject.keys(); it.hasNext();) {
            String key = it.next();
            map.put(key, jsonObject.get(key));
        }
        return map;
    }

    public void ToDetalleEvento(Usuarios item) {
        Intent intent = new Intent(this, DetalleEvento.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }



   /* public void onClick(View view){

        if(view.equals(buttonLogout)){
            SharedPrefManager.getInstance(getApplicationContext()).logout();
        }
    }*/
}