package com.reeu.reeu_premium;

import static com.reeu.reeu_premium.R.id.ListViewInvitado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Lista_de_invitados extends AppCompatActivity {

    //TextView codigo;

     String codigo;

    ListView listView;
    Adapter_Invitados adapter_invitados;
    Usuario5 usuario5;

    RequestQueue queue;
   // Usuarios_Invitados Usuarios_Invitados;

    SwipeRefreshLayout swipeRefreshLayout;

    public static ArrayList<Usuario5> usuariosArrayListInvitado=new ArrayList<>();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_invitados);
        queue = Volley.newRequestQueue(this);

        Bundle bundle = getIntent().getExtras();

        //String valor_recibido = bundle.getString("codigo");
        String valor_recibido = bundle.getString("codigo");

        codigo = valor_recibido;





        //codigo.setText(valor_recibido);


        System.out.println("codigooooooooooooooooooooooooooooooooooo");
        System.out.println(codigo);


        listView=findViewById(ListViewInvitado);
        adapter_invitados= new Adapter_Invitados(this,usuariosArrayListInvitado);
        listView.setAdapter(adapter_invitados);

        usuariosArrayListInvitado.clear();

        invitado();






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
            final String codiguin = codigo;
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

                                        String username = animal.getString("username");
                                        String apellidos = animal.getString("apellidos");


                                        usuario5  = new Usuario5(username, apellidos);
                                        usuariosArrayListInvitado.add(usuario5);
                                        adapter_invitados.notifyDataSetChanged();
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