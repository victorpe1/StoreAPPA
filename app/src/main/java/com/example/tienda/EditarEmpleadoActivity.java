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
import com.example.tienda.Clase.Producto;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EditarEmpleadoActivity extends AppCompatActivity {

    Spinner s_cargo, s_genero;
    private String cargo_act, genero_act;
    ArrayList<String> listaCargo;

    Empleado empleado;
    EditText mDNI, mNombre, mTelef, mDirec, mSalario, mContra;
    Button btnConfirmar, btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_empleado);

        mDNI = (EditText) findViewById(R.id.dniEmpleado);
        mNombre = (EditText) findViewById(R.id.nomEmpleado);
        mTelef = (EditText) findViewById(R.id.telefEmpleado);
        mDirec = (EditText) findViewById(R.id.direcEmpleado);
        mSalario = (EditText) findViewById(R.id.salarioEmpl);
        mContra = (EditText) findViewById(R.id.contraEmp);

        s_cargo = (Spinner) findViewById(R.id.cargo);
        s_genero = (Spinner) findViewById(R.id.genero);

        empleado = (Empleado) getIntent().getSerializableExtra("Empleado");

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

        mDNI.setText(empleado.getDni());
        mNombre.setText(empleado.getNombre());
        mTelef.setText(empleado.getTelef());
        mDirec.setText(empleado.getDirecc());
        mSalario.setText(String.valueOf(empleado.getSalario()));
        mContra.setText(empleado.getContra());

        btnConfirmar = (Button) findViewById(R.id.btnConfirmar);
        btnEliminar= (Button) findViewById(R.id.btnEliminar);

        btnConfirmar.setOnClickListener(v -> {

            final String DNI = mDNI.getText().toString();
            final String nombre = mNombre.getText().toString();
            final String telef = mTelef.getText().toString();
            final String direcc = mDirec.getText().toString();
            final Double salario = Double.parseDouble(mSalario.getText().toString());
            final String contra = mContra.getText().toString();

            try {
                updateEmpleado(empleado.getCodEmpleado(), DNI, nombre, telef,
                                genero_act, direcc, salario, contra, cargo_act);
            } catch (SQLException e) {
                e.printStackTrace();
                Toast.makeText(EditarEmpleadoActivity.this, e.getErrorCode(), Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(EditarEmpleadoActivity.this, "Empleado actualizado...", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(EditarEmpleadoActivity.this, EmpleadoGestionActivity.class);
            startActivity(i);
        });

        btnEliminar.setOnClickListener(v -> {
            Eliminar(empleado.getCodEmpleado());
            Intent i = new Intent(EditarEmpleadoActivity.this, EmpleadoGestionActivity.class);
            startActivity(i);

            Toast.makeText(EditarEmpleadoActivity.this, "Empleado eliminado...", Toast.LENGTH_SHORT).show();
        });

    }

    public void updateEmpleado(int cod, String dni, String nombre, String telef, String sex,
                               String direcc, Double salario, String contra, String cargo)
            throws SQLException {
        CallableStatement entrada = BD.conexionBD().prepareCall("{call ACTUALIZAR_EMP_COMPLET(?,?,?,?,?,?,?,?,?)}");

        entrada.setInt(1, cod);
        entrada.setString(2, dni);
        entrada.setString(3, nombre);
        entrada.setString(4, telef);
        entrada.setString(5, sex);
        entrada.setString(6, direcc);
        entrada.setDouble(7, salario);
        entrada.setString(8, contra);
        entrada.setString(9, cargo);
        entrada.execute();
    }


    public void Eliminar(int codigo) {

        String sql = "DELETE FROM EMPLEADO WHERE CODEMP = ?;";
        PreparedStatement ps = null;
        try {
            ps = BD.conexionBD().prepareStatement(sql);
            ps.setInt(1, codigo);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                ps.close();
            } catch (Exception ex) {
            }
        }
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