package com.example.tienda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tienda.Clase.Producto;

import java.util.ArrayList;

public class AgregarProductoPedidoActivity extends AppCompatActivity {

    Producto producto;
    TextView mNombre, mPrecio, mCategoria;
    EditText mCantidad;
    Button btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto_pedido);

        producto = (Producto) getIntent().getSerializableExtra("Producto");

        mNombre = (TextView) findViewById(R.id.nomProducto);
        mPrecio = (TextView) findViewById(R.id.precio);
        mCategoria = (TextView) findViewById(R.id.nom_categoria);
        mCantidad = (EditText) findViewById(R.id.cantidad);

        btnAgregar = (Button) findViewById(R.id.btnAgregar);

        mNombre.setText(producto.getNombre());
        mPrecio.setText(String.valueOf(producto.getPrecio_unit()));
        mCategoria.setText(producto.getCategoria());

        btnAgregar.setOnClickListener(v -> {



        });



    }
}