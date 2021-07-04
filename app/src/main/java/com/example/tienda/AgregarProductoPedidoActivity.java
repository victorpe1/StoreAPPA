package com.example.tienda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tienda.Clase.Producto;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AgregarProductoPedidoActivity extends AppCompatActivity {

    Producto producto;
    TextView mNombre, mPrecio, mCategoria;
    EditText mCantidad;
    Button btnAgregar;
    String fecha_act, dni_cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto_pedido);

        producto = (Producto) getIntent().getSerializableExtra("Producto");
        dni_cliente = getIntent().getStringExtra("DNI");

        mNombre = (TextView) findViewById(R.id.nomProducto);
        mPrecio = (TextView) findViewById(R.id.precio);
        mCategoria = (TextView) findViewById(R.id.nom_categoria);
        mCantidad = (EditText) findViewById(R.id.cantidad);

        btnAgregar = (Button) findViewById(R.id.btnAgregar);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        fecha_act = df.format(c);

        mNombre.setText(producto.getNombre());
        mPrecio.setText(String.valueOf(producto.getPrecio_unit()));
        mCategoria.setText(producto.getCategoria());


        btnAgregar.setOnClickListener(v -> {

            final int cantidad = Integer.parseInt(mCantidad.getText().toString());
            int cantidad_sumada;
            cantidad_sumada = getCantidad(producto.getCod_pro(), dni_cliente);
            cantidad_sumada += cantidad;

            try {
                registrarProductoPedido(producto.getCod_pro(), dni_cliente, cantidad_sumada, fecha_act);
            } catch (SQLException e) {
                Toast.makeText(this,e.getErrorCode(), Toast.LENGTH_SHORT).show();
            }

            Intent i = new Intent(AgregarProductoPedidoActivity.this, MenuUsuarioActivity.class);
            i.putExtra("DNI", dni_cliente);
            startActivity(i);

        });
    }


    public int getCantidad(int codProd, String usuario) {
        int cant  = 0;

        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement ps = null;

        String query = "SELECT CANTIDAD FROM PEDIDO WHERE CODIGO = ? AND DNI = ? AND ESTADO = 'PENDIENTE'";
        try {
            preparedStatement = BD.conexionBD().prepareStatement(query);
            preparedStatement.setInt(1, codProd);
            preparedStatement.setString(2, usuario);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                cant = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                ps.close();
                rs.close();
            } catch (Exception ex) {
            }
        }
        return cant;
    }


    public void registrarProductoPedido(int cod_pro, String DNI, int cantidad, String fecha)
            throws SQLException {
        CallableStatement entrada = BD.conexionBD().prepareCall("{call GRABAR_PEDIDO(?,?,?,?)}");

        entrada.setInt(1, cod_pro);
        entrada.setString(2, DNI);
        entrada.setInt(3, cantidad);
        entrada.setString(4, fecha);
        entrada.execute();
    }

}