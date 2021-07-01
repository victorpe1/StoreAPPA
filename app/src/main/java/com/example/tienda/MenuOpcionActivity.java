package com.example.tienda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MenuOpcionActivity extends AppCompatActivity {

    ImageButton btnPedido, btnProducto, btnCliente, btnEmpleado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_opcion);

        btnPedido = (ImageButton) findViewById(R.id.btnPedido);
        btnProducto = (ImageButton) findViewById(R.id.btnProducto);
        btnCliente = (ImageButton) findViewById(R.id.btnCliente);
        btnEmpleado = (ImageButton) findViewById(R.id.btnEmpleado);

        btnPedido.setOnClickListener(v -> {
            Intent i = new Intent(MenuOpcionActivity.this, PedidoGestionActivity.class);
            startActivity(i);
        });

        btnProducto.setOnClickListener(v -> {
            Intent i = new Intent(MenuOpcionActivity.this, ProductoGestionActivity.class);
            startActivity(i);
        });

        btnCliente.setOnClickListener(v -> {
            Intent i = new Intent(MenuOpcionActivity.this, ClienteGestionActivity.class);
            startActivity(i);
        });

        btnEmpleado.setOnClickListener(v -> {
            Intent i = new Intent(MenuOpcionActivity.this, EmpleadoGestionActivity.class);
            startActivity(i);
        });




    }


}