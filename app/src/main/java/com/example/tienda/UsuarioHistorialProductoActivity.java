package com.example.tienda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tienda.Adaptador.PedidoAdaptador;
import com.example.tienda.Clase.Historial;
import com.example.tienda.Clase.Pedido;
import com.example.tienda.Clase.Producto;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class UsuarioHistorialProductoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PedidoAdaptador productoPedidoAdaptador;
    private TextView precio_final;
    ArrayList<Pedido> listaProductoPedido;
    Historial historial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_historial_producto);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerPedido);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        precio_final = (TextView) findViewById(R.id.precio_final);

        historial = (Historial) getIntent().getSerializableExtra("ProductoPedido");


        precio_final.setText(String.valueOf(historial.getTotal()));

        listaProductoPedido = consultaProductosPedido(historial.getDNI(), historial.getCodVenta());

        precio_final = (TextView) findViewById(R.id.precio_final);

        productoPedidoAdaptador = new PedidoAdaptador(listaProductoPedido);
        recyclerView.setAdapter(productoPedidoAdaptador);

    }

    public ArrayList<Pedido> consultaProductosPedido(String dni, int codven) {
        ArrayList<Pedido> consultPedidoProducto = new ArrayList<>();
        try {
            Statement stm = BD.conexionBD().createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM PEDIDO_HISTORIAL WHERE ESTADO = 'ATENDIDO' AND DNI='"+dni+"' AND CODVEN='"+codven+"'");
            while (rs.next()) {
                Pedido p = new Pedido();
                p.setDNI(rs.getString(1));
                p.setCod_prod(rs.getInt(2));
                p.setProducto(rs.getString(3));
                p.setCategoria(rs.getString(4));
                p.setCantidad(rs.getInt(5));
                p.setPrecio(rs.getDouble(6));
                p.setSubprecio(rs.getDouble(7));
                p.setTotal(rs.getDouble(8));
                consultPedidoProducto.add(p);
            }

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return (consultPedidoProducto);
    }


}