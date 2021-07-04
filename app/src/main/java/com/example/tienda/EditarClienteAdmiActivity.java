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
import com.example.tienda.Clase.Empleado;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditarClienteAdmiActivity extends AppCompatActivity {

    Spinner s_genero;
    private String genero_act;
    Cliente cliente;
    EditText mDNI, mNombre, mTelef, mDirec, mContra;
    Button btnConfirmar, btnEliminar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cliente_admi);

        mDNI = (EditText) findViewById(R.id.dniCliente);
        mNombre = (EditText) findViewById(R.id.nomCliente);
        mTelef = (EditText) findViewById(R.id.telefCliente);
        mDirec = (EditText) findViewById(R.id.direcCliente);
        mContra = (EditText) findViewById(R.id.contraCliente);

        s_genero = (Spinner) findViewById(R.id.genero);

        cliente = (Cliente) getIntent().getSerializableExtra("Cliente");


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

        mDNI.setText(cliente.getDni());
        mNombre.setText(cliente.getNombre());
        mTelef.setText(cliente.getTelef());
        mDirec.setText(cliente.getDirecc());
        mContra.setText(cliente.getContra());

        btnConfirmar = (Button) findViewById(R.id.btnActualizar);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);

        final int cod = cliente.getCodCliente();

        btnConfirmar.setOnClickListener(v -> {

            final String DNI = mDNI.getText().toString();
            final String nombre = mNombre.getText().toString();
            final String telef = mTelef.getText().toString();
            final String direcc = mDirec.getText().toString();
            final String contra = mContra.getText().toString();

            try {
                updateCliente(cod, DNI, nombre, telef, genero_act, direcc, contra);
            } catch (SQLException e) {
                e.printStackTrace();
                Toast.makeText(EditarClienteAdmiActivity.this, e.getErrorCode(), Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(EditarClienteAdmiActivity.this, "Cliente actualizado...", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(EditarClienteAdmiActivity.this, ClienteGestionActivity.class);
            startActivity(i);
        });

        btnEliminar.setOnClickListener(v ->{

            Eliminar(cod);
            Intent i = new Intent(EditarClienteAdmiActivity.this, ClienteGestionActivity.class);
            startActivity(i);

            Toast.makeText(EditarClienteAdmiActivity.this, "Cliente eliminado...", Toast.LENGTH_SHORT).show();


        });
    }

    public void updateCliente(int cod, String dni, String nombre, String telef, String sex,
                              String direcc, String contra)
            throws SQLException {
        CallableStatement entrada = BD.conexionBD().prepareCall("{call ACTUALIZAR_CLIENTE(?,?,?,?,?,?,?)}");

        entrada.setInt(1, cod);
        entrada.setString(2, dni);
        entrada.setString(3, nombre);
        entrada.setString(4, telef);
        entrada.setString(5, sex);
        entrada.setString(6, direcc);
        entrada.setString(7, contra);
        entrada.execute();
    }

    public void Eliminar(int codigo) {

        String sql = "DELETE FROM CLIENTE WHERE CODEMP = ?;";
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

}