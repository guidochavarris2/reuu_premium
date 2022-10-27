package com.example.reeu_premium;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class CrearEvento extends AppCompatActivity {

    private int año1, mes2, dia3, hora4, minu5;
    static final int DATE_ID = 0;
    static final int HOUR_ID = 1;

    Calendar c = Calendar.getInstance();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_evento);

        año1 = c.get(Calendar.YEAR);
        mes2 = c.get(Calendar.MONTH);
        dia3 = c.get(Calendar.DAY_OF_MONTH);
        hora4 = c.get(Calendar.HOUR_OF_DAY);
        minu5 = c.get(Calendar.MINUTE);

        EditText Fecha = findViewById(R.id.txtfecha);
        EditText Hora = findViewById(R.id.txthora);
        Button Cancelar = findViewById(R.id.btnCancelar);

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
        Fecha.setText(dia3 + "-" + (mes2 + 1) + "-" + año1);
    }
    private void colocar_hora(){
        EditText Hora = findViewById(R.id.txthora);
        Hora.setText(hora4 + ":" + minu5);
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
}