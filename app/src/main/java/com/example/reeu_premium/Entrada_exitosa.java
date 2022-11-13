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
    public static ArrayList<User> usuariosArrayList2=new ArrayList<>();
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
                                        usuariosArrayList2.add(usuario);
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada_exitosa);
        invitado();
        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("env");
        String qr = extras.getString("scanqr");

        System.out.println(id + "----------- id del usuario ---------");
        System.out.println(qr + "----------- qr del usuario ---------");

        TextView nom = findViewById(R.id.txtnombrebd);
        TextView dni = findViewById(R.id.txtdnibd);
        ImageView imgqr = findViewById(R.id.imageQR);

        int position = usuariosArrayList2.indexOf(id);
        System.out.println(position + "-----------se encontro ---------");
        System.out.println(usuariosArrayList2);
        //System.out.println(usuariosArrayList2.get(position).getName() + "-----------nombre ---------");
        //System.out.println(usuariosArrayList2.get(position).getDni() + "-----------nombre ---------");

        //nom.setText(usuariosArrayList2.get(position).getName());
        //dni.setText(usuariosArrayList2.get(position).getDni());

        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(qr, BarcodeFormat.QR_CODE, 850, 850);

            imgqr.setImageBitmap(bitmap);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}