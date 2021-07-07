package com.example.tienda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tienda.Adaptador.EmpleadoAdaptador;
import com.example.tienda.Adaptador.ProductoAdaptador;
import com.example.tienda.Clase.Empleado;
import com.example.tienda.Clase.Producto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EmpleadoGestionActivity extends AppCompatActivity implements EmpleadoAdaptador.OnInterface {

    private boolean error_busqueda = false;

    private Button btnBuscarEmpl;
    private RecyclerView recyclerView;
    private EmpleadoAdaptador empleadoAdaptador;
    private EditText buscarEmpleado;

    ArrayList<Empleado> listaEmpleado;
    Empleado empleado_buscado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleado_gestion);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerEmpleado);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaEmpleado = consultaEmpleado();
        empleadoAdaptador = new EmpleadoAdaptador(listaEmpleado, this);
        recyclerView.setAdapter(empleadoAdaptador);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent i = new Intent(this, CrearEmpleadoActivity.class);
            startActivity(i);
        });

        btnBuscarEmpl= (Button) findViewById(R.id.btnBuscarEmp);

        btnBuscarEmpl.setOnClickListener(v -> {

                buscarEmpleado = (EditText) findViewById(R.id.buscarEmp);
                String dniEmpleado_buscar = buscarEmpleado.getText().toString();

                try {
                    empleado_buscado = BusquedaEmpleado(dniEmpleado_buscar);

                    if(error_busqueda == false){
                        Intent i = new Intent(this, EditarEmpleadoActivity.class);
                        i.putExtra("Empleado", (Serializable) empleado_buscado);

                        startActivity(i);
                    }
                    else{
                        Toast.makeText(this, "No existe esa busqueda", Toast.LENGTH_SHORT).show();
                    }

                } catch (SQLException e) {
                    Toast.makeText(this,e.getErrorCode(), Toast.LENGTH_SHORT).show();
                }
            });

    }


    public Empleado BusquedaEmpleado(String dni) throws SQLException {
        ResultSet res;
        Empleado buscarEmpl = new Empleado();
        CallableStatement entrada = BD.conexionBD().prepareCall("{call BUSCAR_EMPL(?)}");
        entrada.setString(1, dni);
        entrada.execute();
        res = entrada.getResultSet();
        try{
            if (res.next()){
                buscarEmpl.setCodEmpleado(res.getInt(1));
                buscarEmpl.setDni(res.getString(2));
                buscarEmpl.setNombre(res.getString(3));
                buscarEmpl.setTelef(res.getString(4));
                buscarEmpl.setSexo(res.getString(5));
                String aux2 = res.getString(6);
                if( aux2 == null){
                    buscarEmpl.setDirecc("");
                }else buscarEmpl.setDirecc(aux2);

                buscarEmpl.setFecha_in(res.getString(7));
                Double aux = res.getDouble(8);
                if( aux == null){
                    buscarEmpl.setSalario(0.0);
                }else buscarEmpl.setSalario(aux);

                buscarEmpl.setContra(res.getString(9));
                buscarEmpl.setCargo(res.getString(10));
            }else{
                error_busqueda = true;
            }

        }catch(Exception e){}

        return (buscarEmpl);
    }

    public ArrayList<Empleado> consultaEmpleado() {
        ArrayList<Empleado> consultEmpleado = new ArrayList<>();
        try {
            Statement stm = BD.conexionBD().createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM EMPLEADO_1");
            while (rs.next()) {
                Empleado p = new Empleado();
                p.setCodEmpleado(rs.getInt(1));
                p.setDni(rs.getString(2));
                p.setNombre(rs.getString(3));
                p.setTelef(rs.getString(4));
                p.setSexo(rs.getString(5));
                p.setDirecc(rs.getString(6));
                p.setFecha_in(rs.getString(7));
                p.setSalario(rs.getDouble(8));
                p.setContra(rs.getString(9));
                p.setCargo(rs.getString(10));
                consultEmpleado.add(p);
            }

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return (consultEmpleado);
    }

    @Override
    public void onClick(int position) {

        listaEmpleado.get(position);
        Intent i = new Intent(this, EditarEmpleadoActivity.class);
        i.putExtra("Empleado", (Serializable) listaEmpleado.get(position));

        startActivity(i);
    }
}