package com.reeu.reeu_premium;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.util.Calendar;

public class CrearEvento extends AppCompatActivity {

    ImageView iv;
    EditText et;

    Bitmap bitmap;
    int PICK_IMAGE_REQUEST = 1;
    String UPLOAD_URL = "https://reunionguido.azurewebsites.net/imagen.php";

    String editImagen;

    String KEY_IMAGE = "foto";
    String KEY_NOMBRE = "nombre";

    EditText btnBuscar;


    EditText editTextNombre,editTextDescripcion,editTextUbicacion,editTextAforo,editTextFecha,editTextHora, editTextImagen;
    RadioGroup radioGroupEstado;

    private int año1, mes2, dia3, hora4, minu5;
    static final int DATE_ID = 0;
    static final int HOUR_ID = 1;

    //ImageView imagen;

    Calendar c = Calendar.getInstance();
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_evento);

        btnBuscar = findViewById(R.id.txtimagen);

        iv = findViewById(R.id.imageView);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();

            }
        });





        año1 = c.get(Calendar.YEAR);
        mes2 = c.get(Calendar.MONTH);
        dia3 = c.get(Calendar.DAY_OF_MONTH);
        hora4 = c.get(Calendar.HOUR_OF_DAY);
        minu5 = c.get(Calendar.MINUTE);

        //String imagen = getStringImage(bitmap);

        editTextNombre = findViewById(R.id.txtnombreEvento);
        editTextDescripcion = findViewById(R.id.txtDescripcion);
        editTextUbicacion = findViewById(R.id.txtUbicacion);
        editTextAforo = findViewById(R.id.txtAforo);
        editTextFecha = findViewById(R.id.txtfecha);
        editTextHora = findViewById(R.id.txthora);

        //editTextFecha.setEnabled(false);
        //editImagen = imagen;



        radioGroupEstado = findViewById(R.id.radioGroupEvento);
       /* EditText Nombre_Evento = findViewById(R.id.txtnombreEvento);
        EditText Descripcion = findViewById(R.id.txtUbicacion);
        EditText Aforo = findViewById(R.id.txtAforo);*/
        EditText Fecha = findViewById(R.id.txtfecha);
        EditText Hora = findViewById(R.id.txthora);
        Button Cancelar = findViewById(R.id.btnCancelar);
        iv = findViewById(R.id.imgVista);


        findViewById(R.id.buttonRegister2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on button register
                //here we will register the user to server
                registerUser();
            }
        });

        Fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_ID);
            }
        });
        Hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(HOUR_ID);
            }
        });
        Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CrearEvento.this, Buscador.class);
                startActivity(intent);
            }
        });
    }
    private void colocar_fecha(){
        EditText Fecha = findViewById(R.id.txtfecha);
        //Fecha.setText(dia3 + "-" + (mes2 + 1) + "-" + año1);
        Fecha.setText(año1 + "-" + (mes2 + 1) + "-" + dia3);
        editTextFecha = findViewById(R.id.txtfecha);
    }
    private void colocar_hora(){
        EditText Hora = findViewById(R.id.txthora);
        Hora.setText(hora4 + ":" + minu5 + ":" + "00");
    }
    private DatePickerDialog.OnDateSetListener nDateListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i0, int i1, int i2) {
                    año1 = i0;
                    mes2 = i1;
                    dia3 = i2;
                    colocar_fecha();
                }
            };
    private TimePickerDialog.OnTimeSetListener nDateListener2 =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    hora4 = i;
                    minu5 = i1;
                    colocar_hora();
                }
            };
    @Override
    protected Dialog onCreateDialog(int id){
        switch (id) {
            case DATE_ID:
                return new DatePickerDialog(this, nDateListener, año1, mes2, dia3);
            case HOUR_ID:
                return new TimePickerDialog(this, nDateListener2, hora4, minu5, false);
        }
        return null;
    }
    /*

    public void onclick(View view) {
        cargarImagen();
    }*/

    //imagen

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodeImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodeImage;

    }

    private void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "selecciona imagen"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri filePath = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                iv.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    //termino imagen

    /*

    private void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la app"),10);
    }*/
    /*

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            Uri path = data.getData();
            imagen.setImageURI(path);
        }
    }*/

    private void registerUser() {

        final String nombre1 = editTextNombre.getText().toString().trim();
        final String descripcion1 = editTextDescripcion.getText().toString().trim();
        final String ubicacion1 = editTextUbicacion.getText().toString().trim();
        final String fecha1 = editTextFecha.getText().toString().trim();
        final String hora1 = editTextHora.getText().toString().trim();
        final String aforo1 = editTextAforo.getText().toString().trim();

        if (TextUtils.isEmpty(nombre1)) {
            editTextNombre.setError("Por favor ingrese el nombre del evento");
            editTextNombre.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(descripcion1)) {
            editTextDescripcion.setError("Por favor ingrese una descripción para el evento");
            editTextDescripcion.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(ubicacion1)) {
            editTextUbicacion.setError("Por favor ingrese la ubicación del evento");
            editTextUbicacion.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(fecha1)) {
            editTextFecha.setError("Por favor ingrese la fecha del evento");
            editTextFecha.requestFocus();
            return;
        }else{
            editTextFecha.clearFocus();
        }
        if (TextUtils.isEmpty(hora1)) {
            editTextHora.setError("Por favor ingresa la hora del evento");
            editTextHora.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(aforo1)) {
            editTextAforo.setError("Por favor ingresa el aforo del evento");
            editTextAforo.requestFocus();
            return;
        }


        if(SharedPrefManager.getInstance(this).isLoggedIn()){


            User user = SharedPrefManager.getInstance(this).getUser();

            final String id = String.valueOf(user.getId());
            final ProgressDialog loading = ProgressDialog.show(this, "Subiendo...", "Espere por favor");

        final String nombre = editTextNombre.getText().toString().trim();
        final String descripcion = editTextDescripcion.getText().toString().trim();
        final String aforo = editTextAforo.getText().toString().trim();
        final String fecha = editTextFecha.getText().toString().trim();
        final String hora = editTextHora.getText().toString().trim();
        final String ubicacion = editTextUbicacion.getText().toString().trim();
        final String imagen = editImagen;
        final String gender = ((RadioButton) findViewById(radioGroupEstado.getCheckedRadioButtonId())).getText().toString();





        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_CREAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        System.out.println(response);

                        try {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            System.out.println("resultadooooooooooooooooooooooo");
                            System.out.println(obj);
                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("user");

                                //creating a new user object
                                User user = new User(
                                        userJson.getInt("id_evento"),
                                        userJson.getString("nombre"),
                                        userJson.getString("descripcion"),
                                        userJson.getString("aforo"),
                                        userJson.getString("fecha"),
                                        userJson.getString("hora")
                                );

                                //storing the user in shared preferences
                                //SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the profile activity
                                //finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
                        loading.dismiss();
                        //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder alerta = new AlertDialog.Builder(CrearEvento.this);
                        alerta.setTitle("Imagen necesaria");
                        alerta.setMessage("Por favor asegurese de ingresar una imagen :)");

                        alerta.create().show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String imagen = getStringImage(bitmap);
                System.out.println("gaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                System.out.println(imagen);
                Map<String, String> params = new HashMap<>();
                params.put("nombre", nombre);
                params.put("descripcion",descripcion);
                params.put("aforo", aforo);
                params.put("fecha",fecha);
                params.put("hora", hora);
                params.put("ubicacion", ubicacion);
                params.put("imagen", imagen);
                params.put("estado", "p");
                params.put("id_tipo_evento",gender);
                params.put("id_usuario",id);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
            //RequestQueue requestQueue = Volley.newRequestQueue(this);
            //requestQueue.add(stringRequest);


    }
    }


}