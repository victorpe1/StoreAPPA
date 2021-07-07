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

import com.example.tienda.Clase.Empleado;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CrearEmpleadoActivity extends AppCompatActivity {

    Spinner s_cargo, s_genero;
    private String cargo_act, genero_act, fecha_act;
    ArrayList<String> listaCargo;

    Empleado empleado;
    EditText mDNI, mNombre, mTelef, mDirec, mSalario, mContra;
    Button btnConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_empleado);


        mDNI = (EditText) findViewById(R.id.dniEmpleado);
        mNombre = (EditText) findViewById(R.id.nomEmpleado);
        mTelef = (EditText) findViewById(R.id.telefEmpleado);
        mDirec = (EditText) findViewById(R.id.direcEmpleado);
        mSalario = (EditText) findViewById(R.id.salarioEmpl);
        mContra = (EditText) findViewById(R.id.contraEmp);

        s_cargo = (Spinner) findViewById(R.id.cargo);
        s_genero = (Spinner) findViewById(R.id.genero);


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        fecha_act = df.format(c);

        listaCargo = consultaCargo();

        ArrayAdapter<CharSequence> arr_cargo = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, listaCargo);
        arr_cargo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_cargo.setAdapter(arr_cargo);


        ArrayAdapter<CharSequence> arr_gen = ArrayAdapter.createFromResource(this,
                R.array.genero, android.R.layout.simple_spinner_item);
        arr_gen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_genero.setAdapter(arr_gen);

        s_cargo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cargo_act = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        s_genero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genero_act = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnConfirmar = (Button) findViewById(R.id.btnConfirmar);

        btnConfirmar.setOnClickListener(v -> {
            final String DNI = mDNI.getText().toString();
            final String nombre = mNombre.getText().toString();
            final String telef = mTelef.getText().toString();
            final String direcc = mDirec.getText().toString();
            final Double salario = Double.parseDouble(mSalario.getText().toString());
            final String contra = mContra.getText().toString();

            try {
                insertEmpleado(DNI, nombre, telef, genero_act, direcc, fecha_act, salario, contra, cargo_act);
            } catch (SQLException e) {
                e.printStackTrace();
               //Toast.makeText(CrearEmpleadoActivity.this, e.getErrorCode(), Toast.LENGTH_SHORT).show();
            }

            Intent i = new Intent(CrearEmpleadoActivity.this, EmpleadoGestionActivity.class);
            startActivity(i);
            Toast.makeText(CrearEmpleadoActivity.this, "Empleado creado...", Toast.LENGTH_SHORT).show();

        });

    }

    public void insertEmpleado(String dni, String nombre, String telef, String sex,
                               String direcc, String fecha, Double salario, String contra, String cargo)
            throws SQLException {
        CallableStatement entrada = BD.conexionBD().prepareCall("{call CREAR_EMP_COMPLET(?,?,?,?,?,?,?,?,?)}");

        entrada.setString(1, dni);
        entrada.setString(2, nombre);
        entrada.setString(3, telef);
        entrada.setString(4, sex);
        entrada.setString(5, direcc);
        entrada.setString(6, fecha);
        entrada.setDouble(7, salario);
        entrada.setString(8, contra);
        entrada.setString(9, cargo);
        entrada.execute();
    }



    public ArrayList<String> consultaCargo() {
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
    }
}