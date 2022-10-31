package com.example.reeu_premium;

import static com.android.volley.Request.Method.GET;
import static com.example.reeu_premium.R.id.lisMostraMisEventos;
//import static com.example.reeu_premium.R.id.lisMostrar;
import static com.example.reeu_premium.R.id.lisMostrarEntradas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Eventos extends AppCompatActivity implements View.OnClickListener{

    ListView listView;
    ListView listView2;
    Adapter3 adapter3;
    Adapter4 adapter4;


    RequestQueue queue;

    SwipeRefreshLayout swipeRefreshLayout;

    public static ArrayList<EventosClass> usuariosArrayList3=new ArrayList<>();

    public static ArrayList<EventosClass> usuariosArrayList4=new ArrayList<>();



    String url="https://polar-cove-80223.herokuapp.com/estacionamientos.php";
    EventosClass EventosClass;

    Button siguiente;

    Button buttonLogout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);
        queue = Volley.newRequestQueue(this);



        listView=findViewById(lisMostraMisEventos);
        adapter3= new Adapter3(this,usuariosArrayList3);
        listView.setAdapter(adapter3);



        listView2=findViewById(lisMostrarEntradas);
        adapter4= new Adapter4(this,usuariosArrayList4);
        listView2.setAdapter(adapter4);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EventosClass a = usuariosArrayList3.get(i);
                String envio1 = a.id_evento;String envio2 = a.id;String envio3 = a.categoria;
                String envio4 = a.codigo;String envio5 = a.fecha;String envio6 = a.hora;
                String envio7 = a.ubicacion;String envio8 = a.imagen;
                String envio10 = a.id_tipo_evento;

                Intent intent = new Intent(Eventos.this, DetalleEventoAdmin.class);
                intent.putExtra("envio1", envio1);
                intent.putExtra("envio2", envio2);
                intent.putExtra("envio3", envio3);
                intent.putExtra("envio4", envio4);
                intent.putExtra("envio5", envio5);
                intent.putExtra("envio6", envio6);
                intent.putExtra("envio7", envio7);
                intent.putExtra("envio8", envio8);
                intent.putExtra("envio10", envio10);
                startActivity(intent);
            }
        });
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EventosClass a = usuariosArrayList4.get(i);
                String envio1 = a.id_evento;String envio2 = a.id;String envio3 = a.categoria;
                String envio4 = a.codigo;String envio5 = a.fecha;String envio6 = a.hora;
                String envio7 = a.ubicacion;String envio8 = a.imagen;
                String envio10 = a.id_tipo_evento;

                Intent intent = new Intent(Eventos.this, DetalleEventoIngresado.class);
                intent.putExtra("envio1", envio1);
                intent.putExtra("envio2", envio2);
                intent.putExtra("envio3", envio3);
                intent.putExtra("envio4", envio4);
                intent.putExtra("envio5", envio5);
                intent.putExtra("envio6", envio6);
                intent.putExtra("envio7", envio7);
                intent.putExtra("envio8", envio8);
                intent.putExtra("envio10", envio10);
                startActivity(intent);
            }
        });

        findViewById(R.id.profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Eventos.this, Perfil.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.init).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Eventos.this, MainActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.config).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Eventos.this, Configuracion.class);
                startActivity(intent);
            }
        });

        //leerJSON2();
        userLogin2();
        invitado();



        System.out.println("datos de mi listaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println(usuariosArrayList3);
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
    }

    private void leerJSON2(){
        String url = "https://polar-cove-80223.herokuapp.com/estacionamientos.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        System.out.println("Response: " + response.toString());


                        try {
                            // JSONObject jsonObject = new JSONObject(response);
                            //String exito = jsonObject.getString("exito");

                            //int id2 = (int) listaid.get(listaid.size() - 1);

                            // nuevalista.add(id2);


                            JSONArray agenteJSON=response.getJSONArray("datos");
                            for (int i = 0; i < agenteJSON.length(); i++) {

                                String numcadena = String.valueOf(i);

                                JSONObject animal = agenteJSON.getJSONObject(i);

                                String id = animal.getString("id");
                                //listaid.add(id);
                                String codigo = animal.getString("codigo");
                                String categoria = animal.getString("categoria");

                                System.out.println(id + ", " + codigo + ", " + categoria);

                                //usuarios = new Usuarios(id,codigo,categoria);
                                //usuariosArrayList3.add(usuarios);
                                //adapter3.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

        // Access the RequestQueue through your singleton class.
        //Volley.newRequestQueue(this).add(jsonObjectRequest);
        //System.out.println("estos son todos los datos que" + jsonObjectRequest);

        RequestQueue requestQueue =Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
        System.out.println(jsonObjectRequest);
    }

    private void userLogin2() {
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
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_MISEVENTOS,
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

                                        JSONObject animal = userJson.getJSONObject(i);

                                        String id = animal.getString("nombre");
                                        String id_evento = animal.getString("id_evento");
                                        String fecha = animal.getString("fecha");
                                        String hora = animal.getString("hora");
                                        String ubicacion = animal.getString("ubicacion");
                                        String imagen = animal.getString("imagen");
                                        String tipo_evento = animal.getString("id_tipo_evento");
                                        //listaid.add(id);
                                        String codigo = animal.getString("aforo");
                                        String categoria = animal.getString("descripcion");

                                        System.out.println(id + ", " + codigo + ", " + categoria);

                                        EventosClass = new EventosClass(id,codigo,categoria, id_evento, fecha, hora, ubicacion, imagen,tipo_evento);
                                        usuariosArrayList3.add(EventosClass);
                                        adapter3.notifyDataSetChanged();
                                    }

                                    //creating a new user object
                                /*
                                User user = new User(
                                        userJson.getInt("id"),
                                        userJson.getString("username"),
                                        userJson.getString("apellidos"),
                                        userJson.getString("email"),
                                        userJson.getString("dni"),
                                        userJson.getString("gender")
                                );*/


                                    System.out.println("ggaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                                    System.out.println(userJson);

                                    //storing the user in shared preferences
                                    //SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                    //starting the profile activity
                                    //finish();
                                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
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


        }
    }

    private void invitado() {
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
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_INVITADO,
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

                                        JSONObject animal = userJson.getJSONObject(i);

                                        String id = animal.getString("nombre");
                                        String id_evento = animal.getString("id_evento");
                                        String fecha = animal.getString("fecha");
                                        String hora = animal.getString("hora");
                                        String ubicacion = animal.getString("ubicacion");
                                        String imagen = animal.getString("imagen");
                                        String tipo_evento = animal.getString("id_tipo_evento");
                                        //String id_usuario = animal.getString("id_usuario");
                                        //listaid.add(id);
                                        String codigo = animal.getString("aforo");
                                        String categoria = animal.getString("descripcion");

                                        System.out.println(id + ", " + codigo + ", " + categoria);

                                        EventosClass = new EventosClass(id,codigo,categoria, id_evento, fecha, hora, ubicacion, imagen,tipo_evento);
                                        usuariosArrayList4.add(EventosClass);
                                        adapter4.notifyDataSetChanged();
                                    }

                                    //creating a new user object
                                /*
                                User user = new User(
                                        userJson.getInt("id"),
                                        userJson.getString("username"),
                                        userJson.getString("apellidos"),
                                        userJson.getString("email"),
                                        userJson.getString("dni"),
                                        userJson.getString("gender")
                                );*/


                                    System.out.println("ggaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                                    System.out.println(userJson);

                                    //storing the user in shared preferences
                                    //SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                    //starting the profile activity
                                    //finish();
                                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
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




    public void onClick(View view){
        if(view.equals(buttonLogout)){
            SharedPrefManager.getInstance(getApplicationContext()).logout();
        }
    }
}