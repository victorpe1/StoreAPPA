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

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CrearProductoActivity extends AppCompatActivity {
    EditText mNombre, mCosto, mPrecio_unit, mStock;
    Spinner s_categoria;
    String actual_cat;
    ArrayList<String> listaCategoria;
    private String fecha_act, categoria_actual;
    Button btnConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_producto);

        mNombre = (EditText) findViewById(R.id.nombre);
        mCosto = (EditText) findViewById(R.id.costo);
        mPrecio_unit = (EditText) findViewById(R.id.precio_venta);
        mStock = (EditText) findViewById(R.id.stock);

        s_categoria = (Spinner) findViewById(R.id.categoria);

        listaCategoria = consultaCategoria();

        ArrayAdapter<CharSequence> arr_cargo = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, listaCategoria);
        arr_cargo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_categoria.setAdapter(arr_cargo);


        s_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoria_actual = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnConfirmar = (Button) findViewById(R.id.btnConfirmar);

        btnConfirmar.setOnClickListener(v -> {
            final String nombre = mNombre.getText().toString();
            final Double costo = Double.parseDouble(mCosto.getText().toString());
            final Double precio_unit = Double.parseDouble(mPrecio_unit.getText().toString());
            final String stock = mStock.getText().toString();
            final int stock1 = Integer.parseInt(stock);

            try {

                crearProducto(nombre, costo, precio_unit, stock1, categoria_actual);
            } catch (SQLException e) {
                e.printStackTrace();
                Toast.makeText(CrearProductoActivity.this, e.getErrorCode(), Toast.LENGTH_SHORT).show();
            }

            Intent i = new Intent(CrearProductoActivity.this, ProductoGestionActivity.class);
            startActivity(i);

            Toast.makeText(CrearProductoActivity.this, "Producto creado...", Toast.LENGTH_SHORT).show();
        });


    }

    public void crearProducto(String nombre, Double costo, Double precio_unit,
                               int stock, String categoria)
                                throws SQLException {
        CallableStatement entrada = BD.conexionBD().prepareCall("{call CREAR_PROD(?,?,?,?,?)}");

        entrada.setString(1, nombre);
        entrada.setDouble(2, costo);
        entrada.setDouble(3, precio_unit);
        entrada.setInt(4, stock);
        entrada.setString(5, categoria);
        entrada.execute();
    }


    public ArrayList<String> consultaCategoria() {
        ArrayList<String> consultCat = new ArrayList<>();
        try {
            Statement stm = BD.conexionBD().createStatement();
            ResultSet rs = stm.executeQuery("SELECT NOMCAT FROM CATEGORIA");
            while (rs.next()) {
                consultCat.add(rs.getString(1));
            }

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return (consultCat);
    }
}