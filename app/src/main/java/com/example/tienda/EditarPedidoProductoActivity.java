package com.example.tienda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tienda.Clase.Pedido;
import com.example.tienda.Clase.Producto;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditarPedidoProductoActivity extends AppCompatActivity {

    Pedido pedido;
    TextView mNombre, mPrecio, mCategoria, mCantidad_ant;
    EditText mCantidad;
    Button btnActualizar, btnEliminar;
    String fecha_act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_pedido_producto);


        pedido = (Pedido) getIntent().getSerializableExtra("ProductoPedido");

        mNombre = (TextView) findViewById(R.id.nomProducto);
        mPrecio = (TextView) findViewById(R.id.precio);
        mCategoria = (TextView) findViewById(R.id.nom_categoria);
        mCantidad_ant = (TextView) findViewById(R.id.cantidad_antigua);
        mCantidad = (EditText) findViewById(R.id.cantidad);

        btnActualizar = (Button) findViewById(R.id.btnActualizar);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        fecha_act = df.format(c);

        mNombre.setText(pedido.getProducto());
        mPrecio.setText(String.valueOf(pedido.getSubprecio()));
        mCantidad_ant.setText(String.valueOf(pedido.getCantidad()));
        mCategoria.setText(pedido.getCategoria());


        btnActualizar.setOnClickListener(v -> {
            final int cantidad = Integer.parseInt(mCantidad.getText().toString());

            try {
                ActualizarProductoPedido(pedido.getCod_prod(), pedido.getDNI(), cantidad, fecha_act);
                Toast.makeText(this, "Producto del pedido actualizado", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Toast.makeText(this,e.getErrorCode(), Toast.LENGTH_SHORT).show();
            }

            Intent i = new Intent(EditarPedidoProductoActivity.this, MenuUsuarioActivity.class);
            i.putExtra("DNI", pedido.getDNI());
            startActivity(i);

        });

        btnEliminar.setOnClickListener(v -> {
            try {
                EliminarProducto(pedido.getCod_prod(), pedido.getDNI());
                Toast.makeText(this, "Producto del pedido eliminado", Toast.LENGTH_SHORT).show();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            Intent i = new Intent(EditarPedidoProductoActivity.this, MenuUsuarioActivity.class);
            i.putExtra("DNI", pedido.getDNI());
            startActivity(i);
        });
    }

    public void ActualizarProductoPedido(int cod_pro, String DNI, int cantidad, String fecha)
            throws SQLException {
        CallableStatement entrada = BD.conexionBD().prepareCall("{call GRABAR_PEDIDO(?,?,?,?)}");

        entrada.setInt(1, cod_pro);
        entrada.setString(2, DNI);
        entrada.setInt(3, cantidad);
        entrada.setString(4, fecha);
        entrada.execute();
    }

    public void EliminarProducto(int cod_pro, String DNI)
            throws SQLException {
        CallableStatement entrada = BD.conexionBD().prepareCall("{call ELIMINAR_PRODUCTO_PEDIDO(?,?)}");

        entrada.setInt(1, cod_pro);
        entrada.setString(2, DNI);
        entrada.execute();
    }

}