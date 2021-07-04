package com.example.tienda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tienda.Adaptador.HistorialAdaptador;
import com.example.tienda.Adaptador.PedidoAdaptador;
import com.example.tienda.Clase.Historial;
import com.example.tienda.Clase.Pedido;
import com.example.tienda.Clase.Producto;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ClienteHistorialPedidoActivity extends AppCompatActivity implements HistorialAdaptador.OnInterface{

    private RecyclerView recyclerView;
    private HistorialAdaptador historialAdaptador;
    private TextView mFecha, mEstado, mTotal;
    String dni_cliente;
    ArrayList<Historial> listaHistorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_historial_pedido);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerHistorial);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dni_cliente = getIntent().getStringExtra("DNI");

        mFecha = (TextView) findViewById(R.id.fecha);
        mEstado = (TextView) findViewById(R.id.estado);
        mTotal = (TextView) findViewById(R.id.precio_final);

        listaHistorial = consultaHistorial(dni_cliente);

        historialAdaptador = new HistorialAdaptador(listaHistorial, this);
        recyclerView.setAdapter(historialAdaptador);

    }


    public ArrayList<Historial> consultaHistorial(String dni) {
        ArrayList<Historial> consultHistorial = new ArrayList<>();
        try {
            Statement stm = BD.conexionBD().createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM HISTORIAL_PEDIDO_2 WHERE ESTADO = 'ATENDIDO' AND DNI = '"+dni+"'  ORDER BY FECHA DESC");
            while (rs.next()) {
                Historial h = new Historial();
                h.setCodVenta(rs.getInt(1));
                h.setEstado(rs.getString(2));
                h.setFecha(rs.getString(3));
                h.setCodCli(rs.getInt(4));
                h.setDNI(rs.getString(5));
                h.setTotal(rs.getDouble(6));
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