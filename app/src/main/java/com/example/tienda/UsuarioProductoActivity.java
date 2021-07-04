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

    private String dni_cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_producto);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerProductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        categoria = (Categoria) getIntent().getSerializableExtra("Categoria");
        dni_cliente = getIntent().getStringExtra("DNI");

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
                i.putExtra("DNI", dni_cliente);
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
                buscarPro.setCod_pro(res.getInt(1));
                buscarPro.setNombre(res.getString(2));
                buscarPro.setCosto(res.getDouble(3));
                buscarPro.setPrecio_unit(res.getDouble(4));
                buscarPro.setStock(res.getInt(5));
                buscarPro.setCategoria(res.getString(6));
                buscarPro.setCod_cat(res.getInt(7));
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
        i.putExtra("DNI", dni_cliente);
        startActivity(i);
    }


}