package com.example.tienda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tienda.Clase.Cliente;
import com.example.tienda.Clase.Empleado;
import com.example.tienda.Clase.Producto;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditarClienteActivity extends AppCompatActivity {


    Spinner s_genero;
    private String genero_act;
    private String dni_cliente;
    Cliente cliente;
    EditText mDNI, mNombre, mTelef, mDirec, mContra;
    Button btnConfirmar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cliente);

        mDNI = (EditText) findViewById(R.id.dniCliente);
        mNombre = (EditText) findViewById(R.id.nomCliente);
        mTelef = (EditText) findViewById(R.id.telefCliente);
        mDirec = (EditText) findViewById(R.id.direcCliente);
        mContra = (EditText) findViewById(R.id.contraCliente);

        s_genero = (Spinner) findViewById(R.id.genero);

        dni_cliente = getIntent().getStringExtra("DNI");

        try {
            cliente = BusquedaCliente(dni_cliente);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

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

        btnConfirmar.setOnClickListener(v -> {

            final String DNI = mDNI.getText().toString();
            final String nombre = mNombre.getText().toString();
            final String telef = mTelef.getText().toString();
            final String direcc = mDirec.getText().toString();
            final String contra = mContra.getText().toString();

            final int cod = cliente.getCodCliente();

            try {
                updateCliente(cod, DNI, nombre, telef, genero_act, direcc, contra);
            } catch (SQLException e) {
                e.printStackTrace();
                Toast.makeText(EditarClienteActivity.this, e.getErrorCode(), Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(EditarClienteActivity.this, "Cliente actualizado...", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(EditarClienteActivity.this, MenuUsuarioActivity.class);
            i.putExtra("DNI", dni_cliente);
            startActivity(i);


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


    public Cliente BusquedaCliente(String dni_cliente) throws SQLException {
        ResultSet res;
        Cliente buscarCli= new Cliente();
        CallableStatement entrada = BD.conexionBD().prepareCall("{call BUSCAR_CLIENTE(?)}");
        entrada.setString(1, dni_cliente);
        entrada.execute();
        res = entrada.getResultSet();
        try{
            if (res.next()){
                buscarCli.setCodCliente(res.getInt(1));
                buscarCli.setDni(res.getString(2));
                buscarCli.setNombre(res.getString(3));
                buscarCli.setDirecc(res.getString(4));
                buscarCli.setTelef(res.getString(5));
                buscarCli.setContra(res.getString(8));
            }
        }catch(Exception e){}

        return (buscarCli);
    }


}