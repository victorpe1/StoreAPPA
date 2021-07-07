package com.example.tienda;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class PedidoGestionActivity extends AppCompatActivity {

    EditText t1;
    Button btnFechaAgregar;
    private int mYearIni, mMonthIni, mDayIni, sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID = 0;
    private String fecha_act;

    Calendar C = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_gestion);

        sMonthIni = C.get(Calendar.MONTH);
        sDayIni = C.get(Calendar.DAY_OF_MONTH);
        sYearIni = C.get(Calendar.YEAR);

        t1 = (EditText) findViewById(R.id.t1);
        btnFechaAgregar = (Button) findViewById(R.id.btnFechaEscoger);

        t1.setOnClickListener(view -> showDialog(DATE_ID));
        btnFechaAgregar.setOnClickListener(v1 -> {

            Intent i = new Intent(this, PedidoGestionUsuarioActivity.class);
            i.putExtra("fecha_escoger", fecha_act);
            startActivity(i);
            Toast.makeText(this, "Pedidos filtrado", Toast.LENGTH_SHORT).show();
        });


    }

    private void colocar_fecha() {
        t1.setText((mMonthIni + 1) + " - " + mDayIni + " - " + mYearIni+" ");
        fecha_act = mYearIni + "-"+ (mMonthIni + 1)  + "-" + mDayIni;
    }


    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYearIni = year;
                    mMonthIni = monthOfYear;
                    mDayIni = dayOfMonth;
                    colocar_fecha();
                }
            };


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID:
                return new DatePickerDialog(this, mDateSetListener, sYearIni, sMonthIni, sDayIni);
        }
        return null;
    }

}