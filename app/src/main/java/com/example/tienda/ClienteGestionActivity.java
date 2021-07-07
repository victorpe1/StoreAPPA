package com.example.tienda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tienda.Adaptador.ClienteAdaptador;
import com.example.tienda.Adaptador.EmpleadoAdaptador;
import com.example.tienda.Clase.Cliente;
import com.example.tienda.Clase.Empleado;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ClienteGestionActivity extends AppCompatActivity implements ClienteAdaptador.OnInterface{

    private boolean error_busqueda = false;

    private Button btnBuscarCliente;
    private RecyclerView recyclerView;
    private ClienteAdaptador clienteAdaptador;
    private EditText buscarCliente;

    ArrayList<Cliente> listaCliente;
    Cliente cliente_buscado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_gestion);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerCliente);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaCliente = consultaCliente();

        clienteAdaptador = new ClienteAdaptador(listaCliente, this);
        recyclerView.setAdapter(clienteAdaptador);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent i = new Intent(this, CrearClienteActivity.class);
            startActivity(i);
        });

        btnBuscarCliente = (Button) findViewById(R.id.btnBuscarCliente);

        btnBuscarCliente.setOnClickListener(v -> {

            buscarCliente = (EditText) findViewById(R.id.buscarCliente);
            String dniCliente_buscar = buscarCliente.getText().toString();


            cliente_buscado = BusquedaCliente(dniCliente_buscar);

            if(!error_busqueda){

                Intent i = new Intent(this, EditarClienteAdmiActivity.class);
                i.putExtra("Cliente", (Serializable) cliente_buscado);

                startActivity(i);

            }
            else{
                Toast.makeText(this, "No existe esa busqueda", Toast.LENGTH_SHORT).show();
            }

        });

    }


    public Cliente BusquedaCliente(String dni) {

        Cliente buscarCli = new Cliente();
        try{

        Statement stm = BD.conexionBD().createStatement();
        ResultSet res = stm.executeQuery("SELECT * FROM CLIENTE WHERE DNI LIKE "+dni+"'+'%'");

            if (res.next()){

                buscarCli.setCodCliente(res.getInt(1));
                buscarCli.setDni(res.getString(2));
                buscarCli.setNombre(res.getString(3));
                String aux2 = res.getString(4);
                if( aux2 == null){
                    buscarCli.setDirecc("");
                }else buscarCli.setDirecc(aux2);
                buscarCli.setTelef(res.getString(5));
                buscarCli.setSexo(res.getString(6));
                buscarCli.setFecha_in(res.getString(7));
                buscarCli.setContra(res.getString(8));
            }else{
                error_busqueda = true;
            }

        }catch(Exception e){}

        return (buscarCli);
    }

    public ArrayList<Cliente> consultaCliente() {
        ArrayList<Cliente> consultCliente = new ArrayList<>();
        try {
            Statement stm = BD.conexionBD().createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM CLIENTE");
            while (rs.next()) {
                Cliente p = new Cliente();
                p.setCodCliente(rs.getInt(1));
                p.setDni(rs.getString(2));
                p.setNombre(rs.getString(3));
                p.setDirecc(rs.getString(4));
                p.setTelef(rs.getString(5));
                p.setSexo(rs.getString(6));
                p.setFecha_in(rs.getString(7));
                p.setContra(rs.getString(8));
                consultCliente.add(p);
            }

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return (consultCliente);
    }

    @Override
    public void onClick(int position) {

        listaCliente.get(position);
        Intent i = new Intent(this, EditarClienteAdmiActivity.class);
        i.putExtra("Cliente", (Serializable) listaCliente.get(position));

        startActivity(i);
    }

}