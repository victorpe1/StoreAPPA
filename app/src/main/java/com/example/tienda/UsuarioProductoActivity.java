package com.example.tienda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tienda.Adaptador.ProductoAdaptador;
import com.example.tienda.Clase.Categoria;
import com.example.tienda.Clase.Producto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UsuarioProductoActivity extends AppCompatActivity implements ProductoAdaptador.OnInterface{

    private Button btnBuscarProd;
    private RecyclerView recyclerView;
    private ProductoAdaptador productoAdaptador;
    private int codigo_categoria;
    private EditText buscarProducto;
    ArrayList<Producto> listaProducto;
    Producto producto;
    Categoria categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_producto);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerProductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        categoria = (Categoria) getIntent().getSerializableExtra("Categoria");
        codigo_categoria = categoria.getCodigoCategoria();

        listaProducto = consultaProductos(String.valueOf(codigo_categoria));
        productoAdaptador = new ProductoAdaptador(listaProducto, this);
        recyclerView.setAdapter(productoAdaptador);



        btnBuscarProd = (Button) findViewById(R.id.btnBuscarProd);

        btnBuscarProd.setOnClickListener(v ->{
            buscarProducto = (EditText) findViewById(R.id.buscarProducto);
            String nomProducto = buscarProducto.getText().toString();

            try {
                producto = BusquedaProducto(nomProducto, codigo_categoria);
                Intent i = new Intent(this, AgregarProductoPedidoActivity.class);
                i.putExtra("Producto", (Serializable) producto);

                startActivity(i);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });
    }

    public Producto BusquedaProducto(String nombre, int codigo) throws SQLException {
        ResultSet res;
        Producto buscarPro = new Producto();
        CallableStatement entrada = BD.conexionBD().prepareCall("{call BUSCAR_PROD_CAT(?,?)}");
        entrada.setString(1, nombre);
        entrada.setInt(2, codigo);
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
    }

    public ArrayList<Producto> consultaProductos(String cod) {
        ArrayList<Producto> consultProducto = new ArrayList<>();
        try {
            Statement stm = BD.conexionBD().createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM PRODUCTO_2 WHERE CODCAT='"+cod+"'");
            while (rs.next()) {
                Producto p = new Producto();
                p.setCod_pro(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setCosto(rs.getDouble(3));
                p.setPrecio_unit(rs.getDouble(4));
                p.setStock(rs.getInt(5));
                p.setCategoria(rs.getString(6));
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
        Intent i = new Intent(this, AgregarProductoPedidoActivity.class);
        i.putExtra("Producto", (Serializable) listaProducto.get(position));

        startActivity(i);
    }


}