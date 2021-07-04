package com.example.tienda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tienda.Adaptador.PedidoAdaptador;
import com.example.tienda.Adaptador.ProductoAdaptador;
import com.example.tienda.Clase.Pedido;
import com.example.tienda.Clase.Producto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ClientePedidoActivity extends AppCompatActivity implements PedidoAdaptador.OnInterface{

    private Button btnGenerarPedido;
    private RecyclerView recyclerView;
    private PedidoAdaptador productoPedidoAdaptador;
    private TextView precio_final;
    String dni_cliente;
    ArrayList<Pedido> listaProductoPedido;
    Producto producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_pedido);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerPedido);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dni_cliente = getIntent().getStringExtra("DNI");
        precio_final = (TextView) findViewById(R.id.precio_final);

        listaProductoPedido = consultaProductosPedido(dni_cliente);
        Double precio_total = 0.0;
        int cant = getCantidad(dni_cliente);
        if(cant != 0){
            precio_total = listaProductoPedido.get(0).getTotal();
        }


       precio_final.setText(String.valueOf(precio_total));


        productoPedidoAdaptador = new PedidoAdaptador(listaProductoPedido, this);
        recyclerView.setAdapter(productoPedidoAdaptador);

        btnGenerarPedido = (Button) findViewById(R.id.btnRealizarPedido);

        btnGenerarPedido.setOnClickListener(v ->{

            try {
                generarPedidoFinalizado(dni_cliente);
                Toast.makeText(this, "Pedido generado", Toast.LENGTH_SHORT).show();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            Intent i = new Intent(this, MenuUsuarioActivity.class);
            i.putExtra("DNI", dni_cliente);
            startActivity(i);


        });

    }


    public int getCantidad(String usuario) {
        int cant  = 0;

        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement ps = null;

        String query = "SELECT CANTIDAD FROM PEDIDO WHERE DNI = ? AND ESTADO = 'PENDIENTE'";
        try {
            preparedStatement = BD.conexionBD().prepareStatement(query);
            preparedStatement.setString(1, usuario);
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

    public ArrayList<Pedido> consultaProductosPedido(String dni) {
        ArrayList<Pedido> consultPedidoProducto = new ArrayList<>();
        try {
            Statement stm = BD.conexionBD().createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM PEDIDO WHERE ESTADO = 'PENDIENTE' AND DNI='"+dni+"'");
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

    public void generarPedidoFinalizado(String DNI)
            throws SQLException {
        CallableStatement entrada = BD.conexionBD().prepareCall("{call FINALIZAR_PEDIDO(?)}");

        entrada.setString(1, DNI);
        entrada.execute();
    }


    @Override
    public void onClick(int position) {

        listaProductoPedido.get(position);
        Intent i = new Intent(this, EditarPedidoProductoActivity.class);
        i.putExtra("ProductoPedido", (Serializable) listaProductoPedido.get(position));
        startActivity(i);
    }

}