package com.example.reeu_premium;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Entrada_exitosa extends AppCompatActivity {
    User usuario;
    RequestQueue queue;
    public static ArrayList<User> usuariosArrayList2=new ArrayList<>();
    List myArrayList = new ArrayList();
    String variable;
    String igual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada_exitosa);
        queue = Volley.newRequestQueue(this);

        invitado();




        System.out.println("gaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");


        for (int i = 0; i < usuariosArrayList2.size(); i++) {


            igual = usuariosArrayList2.get(i).getName();

            System.out.println(igual + " ------------------------------------- es el igual qlero");

        }





        System.out.println("Guidoooooooooooooooooooooooooooooooooooooooooo");
       // System.out.println(myArrayList.get(1));

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

                                        //EventosClass = new EventosClass(id,codigo,categoria, id_evento, fecha, hora, ubicacion, imagen,tipo_evento);
                                        //usuariosArrayList2.add(EventosClass);
                                        //adapter3.notifyDataSetChanged();
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
            final String codiguin = "2";
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
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_USUARIOSENTRADA,
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

                                        //String id = animal.getString("id");

                                        int id = animal.getInt("id_usuario");
                                        String username = animal.getString("username");
                                        String apellidos = animal.getString("apellidos");
                                        String email = animal.getString("email");
                                        String dni = animal.getString("dni");
                                        String gender = animal.getString("gender");



                                        System.out.println(id + " , " + username + " , " + dni);

                                        usuario  = new User(id, username, apellidos, email, dni, gender);


                                        myArrayList.add(usuario);
                                        System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
                                        System.out.println(username);
                                        System.out.println(usuario);
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
                                );
*/

                                    System.out.println("ggaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                                    System.out.println(userJson);

                                    //storing the user in shared preferences
                                    //SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                    //starting the profile activity
                                    //finish();
                                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
                    //params.put("id", String.valueOf(id));
                    //params.put("id",id);
                    return params;
                }
            };

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


        }
    }

}