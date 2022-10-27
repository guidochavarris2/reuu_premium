package com.example.reeu_premium;

import static com.android.volley.Request.Method.GET;

import androidx.appcompat.app.AppCompatActivity;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ListView listView;
    Adapter2 adapter2;

    RequestQueue queue;

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

        buttonLogout = (ImageView)findViewById(R.id.imagenEntrada);
        FloatingActionButton Boton_Agregar = findViewById(R.id.agregar_evento);

        listView=findViewById(R.id.lisMostrar);
        adapter2= new Adapter2(this,usuariosArrayList2);
        listView.setAdapter(adapter2);

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

        /*buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Home_2.class);
                startActivity(intent);
            }
        });*/

        //leerJSON2();
        userLogin2();
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

                                usuarios = new Usuarios(id,codigo,categoria);
                                usuariosArrayList2.add(usuarios);
                                adapter2.notifyDataSetChanged();
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

                                        JSONObject animal = userJson.getJSONObject(i);

                                        String id = animal.getString("nombre");
                                        //listaid.add(id);
                                        String codigo = animal.getString("aforo");
                                        String categoria = animal.getString("descripcion");

                                        System.out.println(id + ", " + codigo + ", " + categoria);

                                        usuarios = new Usuarios(id,codigo,categoria);
                                        usuariosArrayList2.add(usuarios);
                                        adapter2.notifyDataSetChanged();
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

        /*if(view.equals(buttonLogout)){
            SharedPrefManager.getInstance(getApplicationContext()).logout();
        }*/
    }
}