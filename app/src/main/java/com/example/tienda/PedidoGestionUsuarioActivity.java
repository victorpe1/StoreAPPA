package com.example.tienda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tienda.Adaptador.CategoriaAdaptador;
import com.example.tienda.Adaptador.HistorialAdaptador;
import com.example.tienda.Adaptador.PedidoAdaptador;
import com.example.tienda.Adaptador.PedidoFechaAdaptador;
import com.example.tienda.Clase.Historial;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PedidoGestionUsuarioActivity extends AppCompatActivity implements PedidoFechaAdaptador.OnInterface{

    String fecha_act;

    private RecyclerView recyclerView;
    private PedidoFechaAdaptador pedidoFechaAdaptador;
    private TextView mFecha, mDNI, mNombre, mTotal, mBuscarDNI;
    private Button btnFiltrarDNI;
    String dni_cliente, dni_cli;
    ArrayList<Historial> listaHistorial, listaHistorialDNI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_gestion_usuario);

        fecha_act = getIntent().getStringExtra("fecha_escoger");


        recyclerView = (RecyclerView) findViewById(R.id.recyclerPedidoFiltrado);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFecha = (TextView) findViewById(R.id.fecha);
        mNombre = (TextView) findViewById(R.id.nomCliente);
        mDNI = (TextView) findViewById(R.id.dniCliente);
        mTotal = (TextView) findViewById(R.id.precio_final);
        mBuscarDNI = (TextView) findViewById(R.id.buscarPedidoDNI);
        btnFiltrarDNI = (Button) findViewById(R.id.btnBuscarPedidoDNI);

        if(getIntent().getStringExtra("DNI") != null){
            dni_cli = getIntent().getStringExtra("DNI");
            listaHistorial = consultaHistorial2(fecha_act, dni_cli);
        }else{
            listaHistorial = consultaHistorial(fecha_act);
        }


        pedidoFechaAdaptador = new PedidoFechaAdaptador(listaHistorial, this);
        recyclerView.setAdapter(pedidoFechaAdaptador);


        btnFiltrarDNI.setOnClickListener(v -> {

            dni_cliente = mBuscarDNI.getText().toString();

            Intent i = new Intent(this, PedidoGestionUsuarioActivity.class);
            i.putExtra("fecha_escoger", fecha_act);
            i.putExtra("DNI", dni_cliente);

            startActivity(i);
            Toast.makeText(this, "Pedidos filtrado", Toast.LENGTH_SHORT).show();

        });

    }

    public ArrayList<Historial> consultaHistorial2(String fecha_act, String dni) {
        ArrayList<Historial> consultHistorial = new ArrayList<>();
        try {
            Statement stm = BD.conexionBD().createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM HISTORIAL_PEDIDO_3 WHERE DNI ='"+dni+"' AND ESTADO = 'ATENDIDO' AND FECHA = '"+fecha_act+"'  ORDER BY FECHA DESC");
            while (rs.next()) {
                Historial h = new Historial();
                h.setCodVenta(rs.getInt(1));
                h.setEstado(rs.getString(2));
                h.setFecha(rs.getString(3));
                h.setCodCli(rs.getInt(4));
                h.setDNI(rs.getString(5));
                h.setTotal(rs.getDouble(6));
                h.setNomCli(rs.getString(7));
                consultHistorial.add(h);
            }

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return (consultHistorial);
    }


    public ArrayList<Historial> consultaHistorial(String fecha_act) {
        ArrayList<Historial> consultHistorial = new ArrayList<>();
        try {
            Statement stm = BD.conexionBD().createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM HISTORIAL_PEDIDO_3 WHERE ESTADO = 'ATENDIDO' AND FECHA = '"+fecha_act+"'  ORDER BY FECHA DESC");
            while (rs.next()) {
                Historial h = new Historial();
                h.setCodVenta(rs.getInt(1));
                h.setEstado(rs.getString(2));
                h.setFecha(rs.getString(3));
                h.setCodCli(rs.getInt(4));
                h.setDNI(rs.getString(5));
                h.setTotal(rs.getDouble(6));
                h.setNomCli(rs.getString(7));
                consultHistorial.add(h);
            }

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return (consultHistorial);
    }


    @Override
    public void onClick(int position) {

        listaHistorial.get(position);
        Intent i = new Intent(this, UsuarioHistorialProductoActivity.class);
        i.putExtra("ProductoPedido", (Serializable) listaHistorial.get(position));
        startActivity(i);

    }
}