package com.example.tienda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.xml.transform.Result;

public class LoginActivity extends AppCompatActivity {

    private EditText mDNI, mPass;
    private Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDNI = (EditText) findViewById(R.id.DNI);
        mPass = (EditText) findViewById(R.id.password);

        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(v ->{

            final String DNI = mDNI.getText().toString();
            final String password = mPass.getText().toString();

            int conta = 0;

            try{

                Statement stm = BD.conexionBD().createStatement();
                ResultSet rs = stm.executeQuery("SELECT COUNT(DNI) FROM CLIENTE WHERE DNI='"+DNI+"' AND CONTRA='"+password+"'");
                if(rs.next()){
                    conta = rs.getInt(1);
                }

            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            if(conta == 1){
                Intent i = new Intent(this, MenuUsuarioActivity.class);
                i.putExtra("DNI", DNI);
                startActivity(i);
            }else{

                try{

                    Statement stm = BD.conexionBD().createStatement();
                    ResultSet rs = stm.executeQuery("SELECT COUNT(DNI) FROM EMPLEADO WHERE DNI='"+DNI+"' AND CONTRA='"+password+"'");
                    if(rs.next()){
                        conta = rs.getInt(1);
                    }

                }catch (Exception e){
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                if(conta == 1) {
                    Intent i = new Intent(this, MenuOpcionActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(this, "No existe cliente", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }

}