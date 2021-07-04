package com.example.tienda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tienda.Adaptador.CategoriaAdaptador;
import com.example.tienda.Adaptador.ProductoAdaptador;
import com.example.tienda.Clase.Categoria;
import com.example.tienda.Clase.Producto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MenuUsuarioActivity extends AppCompatActivity implements CategoriaAdaptador.OnInterface{

    private RecyclerView recyclerView;
    private CategoriaAdaptador categoriaAdaptador;
    private String dni_cliente;

    ArrayList<Categoria> listaCategoria;
    Categoria categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_usuario);


        dni_cliente = getIntent().getStringExtra("DNI");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerCategoria);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaCategoria = consultaCategoria();
        categoriaAdaptador = new CategoriaAdaptador(listaCategoria, this);
        recyclerView.setAdapter(categoriaAdaptador);

    }

    public ArrayList<Categoria> consultaCategoria() {
        ArrayList<Categoria> consultCat = new ArrayList<>();
        try {
            Statement stm = BD.conexionBD().createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM CATEGORIA");
            while (rs.next()) {
                Categoria p = new Categoria();
                p.setCodigoCategoria(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setDetalle(rs.getString(3));
                consultCat.add(p);
            }

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return (consultCat);
    }


    @Override
    public void onClick(int position) {

        listaCategoria.get(position);
        Intent i = new Intent(this, UsuarioProductoActivity.class);
        i.putExtra("Categoria", (Serializable) listaCategoria.get(position));
        i.putExtra("DNI", dni_cliente);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.barra_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if(id == R.id.ver_perfil){

            Intent i = new Intent(this, EditarClienteActivity.class);

            i.putExtra("DNI", dni_cliente);

            startActivity(i);

           Toast.makeText(this, "Perfil del usuario", Toast.LENGTH_SHORT).show();

        }else if(id == R.id.ver_pedido){

            Intent i = new Intent(this, ClientePedidoActivity.class);

            i.putExtra("DNI", dni_cliente);
            startActivity(i);
            Toast.makeText(this, "Pedido del usuario", Toast.LENGTH_SHORT).show();

        }else if(id == R.id.ver_historial){

            Intent i = new Intent(this, ClienteHistorialPedidoActivity.class);

            i.putExtra("DNI", dni_cliente);
            startActivity(i);
            Toast.makeText(this, "Pedido del usuario", Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }

}