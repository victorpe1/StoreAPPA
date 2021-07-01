package com.example.tienda;

import android.os.StrictMode;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class BD {


    public static Connection conexionBD(){
        Connection cnn = null;

        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            //192.168.0.21

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            //cnn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.0.21/BODEGA; user=sa; password=123456;");
            cnn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.0.21/BODEGA", "sa", "123456");
        }catch(Exception e){

        }
        return cnn;
    }
}
