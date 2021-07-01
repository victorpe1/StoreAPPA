package com.example.tienda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RegistroActivity extends AppCompatActivity {


    private EditText mDNI, mPass, mNombre, mTelef;
    private String fecha_act, genero_act;
    Spinner  s_genero;
    private Button btnRegistro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mDNI = (EditText) findViewById(R.id.DNI);
        mPass = (EditText) findViewById(R.id.password);
        mNombre = (EditText) findViewById(R.id.nombre);
        mTelef = (EditText) findViewById(R.id.telefono);

        s_genero = (Spinner) findViewById(R.id.genero);

        btnRegistro = (Button) findViewById(R.id.btnRegistro);

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

        btnRegistro.setOnClickListener(v -> {

            final String DNI = mDNI.getText().toString();
            final String password = mPass.getText().toString();
            final String nombre = mNombre.getText().toString();
            final String telef = mTelef.getText().toString();


            try {
                setInsertCliente(DNI, nombre, telef, genero_act, fecha_act, password);
            } catch (SQLException e) {
                e.printStackTrace();
                Toast.makeText(RegistroActivity.this, e.getErrorCode(), Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(RegistroActivity.this, "Registrado...", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(RegistroActivity.this, LoginActivity.class);
            startActivity(i);
        });

    }

    public void setInsertCliente(String dni, String nom, String telef, String sex, String fecha_act,
                                      String contra) throws SQLException {
        CallableStatement entrada = BD.conexionBD().prepareCall("{call REGISTRO_CLIENTE(?,?,?,?,?,?)}");

        entrada.setString(1, dni);
        entrada.setString(2, nom);
        entrada.setString(3, telef);
        entrada.setString(4, sex);
        entrada.setString(5, fecha_act);
        entrada.setString(6, contra);
        entrada.execute();
    }

    /* public ArrayList<String> consultaCargo() {
        ArrayList<String> consultCargo = new ArrayList<>();
        try {
            Statement stm = BD.conexionBD().createStatement();
            ResultSet rs = stm.executeQuery("SELECT NOMCAR FROM CARGO");
            while (rs.next()) {
                consultCargo.add(rs.getString(1));
            }

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return (consultCargo);
    } */

}