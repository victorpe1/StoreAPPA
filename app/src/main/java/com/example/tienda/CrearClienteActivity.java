package com.example.tienda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tienda.Clase.Cliente;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CrearClienteActivity extends AppCompatActivity {

    Spinner s_genero;
    private String genero_act, fecha_act;
    EditText mDNI, mNombre, mTelef, mDirec, mContra;
    Button btnConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cliente);

        mDNI = (EditText) findViewById(R.id.dniCliente);
        mNombre = (EditText) findViewById(R.id.nomCliente);
        mTelef = (EditText) findViewById(R.id.telefCliente);
        mDirec = (EditText) findViewById(R.id.direcCliente);
        mContra = (EditText) findViewById(R.id.contraCliente);

        s_genero = (Spinner) findViewById(R.id.genero);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        fecha_act = df.format(c);

        ArrayAdapter<CharSequence> arr_gen = ArrayAdapter.createFromResource(this,
                R.array.genero, android.R.layout.simple_spinner_item);
        arr_gen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_genero.setAdapter(arr_gen);

        s_genero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genero_act = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnConfirmar = (Button) findViewById(R.id.btnAgregar);

        btnConfirmar.setOnClickListener(v -> {

            final String DNI = mDNI.getText().toString();
            final String nombre = mNombre.getText().toString();
            final String telef = mTelef.getText().toString();
            final String direcc = mDirec.getText().toString();
            final String contra = mContra.getText().toString();

            try {
                crearCliente(DNI, nombre, telef, fecha_act, genero_act, direcc, contra);
            } catch (SQLException e) {
                e.printStackTrace();
                Toast.makeText(CrearClienteActivity.this, e.getErrorCode(), Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(CrearClienteActivity.this, "Cliente creado...", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(CrearClienteActivity.this, ClienteGestionActivity.class);
            startActivity(i);
        });
    }

    public void crearCliente(String dni, String nombre, String telef, String fecha, String sex,
                              String direcc, String contra)
            throws SQLException {
        CallableStatement entrada = BD.conexionBD().prepareCall("{call CREAR_CLIENTE(?,?,?,?,?,?,?)}");

        entrada.setString(1, dni);
        entrada.setString(2, nombre);
        entrada.setString(3, telef);
        entrada.setString(4, fecha);
        entrada.setString(5, sex);
        entrada.setString(6, direcc);
        entrada.setString(7, contra);
        entrada.execute();
    }

}