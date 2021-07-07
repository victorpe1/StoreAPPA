package com.example.tienda;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.os.Parcelable;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;

import com.example.tienda.Adaptador.ProductoAdaptador;
import com.example.tienda.Clase.Producto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductoGestionActivity extends AppCompatActivity implements ProductoAdaptador.OnInterface, SearchView.OnQueryTextListener {

    private RecyclerView  recyclerView;
    private ProductoAdaptador productoAdaptador;
    private SearchView svBuscarProducto;
    ArrayList<Producto> listaProducto;
    Producto producto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_gestion);

        svBuscarProducto = (SearchView) findViewById(R.id.svBuscar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerProductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaProducto = consultaProductos();
        productoAdaptador = new ProductoAdaptador(listaProducto, this);
        recyclerView.setAdapter(productoAdaptador);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v ->{
            Intent i = new Intent(this, CrearProductoActivity.class);
            startActivity(i);
        });

        svBuscarProducto.setOnQueryTextListener(this);

    }

    /*public Producto BusquedaProducto(String nombre) throws SQLException {
        ResultSet res;
        Producto buscarPro = new Producto();
        CallableStatement entrada = BD.conexionBD().prepareCall("{call BUSCAR_PROD(?)}");
        entrada.setString(1, nombre);
        entrada.execute();
        res = entrada.getResultSet();
        try{
            if (res.next()){
                buscarPro.setNombre(res.getString(1));
                buscarPro.setStock(res.getInt(2));
                buscarPro.setCategoria(res.getString(3));
                buscarPro.setCod_pro(res.getInt(4));
                buscarPro.setCosto(res.getDouble(5));
                buscarPro.setPrecio_unit(res.getDouble(6));
            }
        }catch(Exception e){}

        return (buscarPro);
    }*/

    public ArrayList<Producto> consultaProductos() {
        ArrayList<Producto> consultProducto = new ArrayList<>();
        try {
            Statement stm = BD.conexionBD().createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM PRODUCTO_1");
            while (rs.next()) {
                Producto p = new Producto();
                p.setNombre(rs.getString(1));
                p.setStock(rs.getInt(2));
                p.setCategoria(rs.getString(3));
                p.setCod_pro(rs.getInt(4));
                p.setCosto(rs.getDouble(5));
                p.setPrecio_unit(rs.getDouble(6));
                consultProducto.add(p);
            }

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return (consultProducto);
    }


    @Override
    public void onClick(int position) {

        listaProducto.get(position);
        Intent i = new Intent(this, EditarProductoActivity.class);
        i.putExtra("Producto", (Serializable) listaProducto.get(position));

        startActivity(i);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onQueryTextChange(String newText) {
        productoAdaptador.filtrado(newText);
        return false;
    }
}