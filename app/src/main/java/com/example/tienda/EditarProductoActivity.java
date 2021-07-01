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

import com.example.tienda.Clase.Producto;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EditarProductoActivity extends AppCompatActivity {

    Producto producto;
    EditText mNombre, mCosto, mPrecio_unit, mStock;
    Spinner s_categoria;
    ArrayList<String> listaCategoria;
    private String fecha_act, categoria_actual;
    Button btnConfirmar, btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto);

        producto = (Producto) getIntent().getSerializableExtra("Producto");

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

        mNombre.setText(producto.getNombre());
        mStock.setText(String.valueOf(producto.getStock()));
        mCosto.setText(String.valueOf(producto.getCosto()));
        mPrecio_unit.setText(String.valueOf(producto.getPrecio_unit()));

        btnConfirmar = (Button) findViewById(R.id.btnConfirmar);
        btnEliminar= (Button) findViewById(R.id.btnEliminar);

        btnConfirmar.setOnClickListener(v -> {
            final String nombre = mNombre.getText().toString();
            final Double costo = Double.parseDouble(mCosto.getText().toString());
            final Double precio_unit = Double.parseDouble(mPrecio_unit.getText().toString());
            final String stock = mStock.getText().toString();
            final int stock1 = Integer.parseInt(stock);

            try {
               updateProducto(producto.getCod_pro(), nombre, costo, precio_unit, stock1, categoria_actual);
            } catch (SQLException e) {
                e.printStackTrace();
                Toast.makeText(EditarProductoActivity.this, e.getErrorCode(), Toast.LENGTH_SHORT).show();
            }

            Intent i = new Intent(EditarProductoActivity.this, ProductoGestionActivity.class);
            startActivity(i);

            Toast.makeText(EditarProductoActivity.this, "Producto actualizado...", Toast.LENGTH_SHORT).show();
        });

        btnEliminar.setOnClickListener(v -> {
            Eliminar(producto.getCod_pro());
            Intent i = new Intent(EditarProductoActivity.this, ProductoGestionActivity.class);
            startActivity(i);

            Toast.makeText(EditarProductoActivity.this, "Producto eliminado...", Toast.LENGTH_SHORT).show();
        });

    }

    public void updateProducto(int cod, String nombre, Double costo, Double precio_unit, int stock, String categoria)
                                                                            throws SQLException {
        CallableStatement entrada = BD.conexionBD().prepareCall("{call ACTUALIZAR_PROD(?,?,?,?,?,?)}");

        entrada.setInt(1, cod);
        entrada.setString(2, nombre);
        entrada.setDouble(3, costo);
        entrada.setDouble(4, precio_unit);
        entrada.setInt(5, stock);
        entrada.setString(6, categoria);
        entrada.execute();
    }

    public void Eliminar(int codigo) {

        String sql = "DELETE FROM PRODUCTO WHERE CODPRO = ?;";
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