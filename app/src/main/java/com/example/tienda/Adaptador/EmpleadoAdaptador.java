package com.example.tienda.Adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tienda.Clase.Empleado;
import com.example.tienda.Clase.Producto;
import com.example.tienda.R;

import java.util.ArrayList;

public class EmpleadoAdaptador extends RecyclerView.Adapter<EmpleadoAdaptador.ViewHolder>{

    public ArrayList<Empleado> listaEmpleado;
    EmpleadoAdaptador.OnInterface m_onInterface;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mNombre, mDNI, mTelef, mCargo;
        EmpleadoAdaptador.OnInterface onInterface;

        public ViewHolder (View item, EmpleadoAdaptador.OnInterface on){
            super(item);
            mNombre = (TextView) item.findViewById(R.id.nomEmpleado);
            mDNI = (TextView) item.findViewById(R.id.dniEmpleado);
            mTelef = (TextView) item.findViewById(R.id.telefEmpleado);
            mCargo = (TextView) item.findViewById(R.id.cargoEmpl);
            this.onInterface = on;

            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onInterface.onClick(getAdapterPosition());
        }
    }

    public EmpleadoAdaptador (ArrayList<Empleado> listaEmp, EmpleadoAdaptador.OnInterface m_onInterface){
        this.listaEmpleado = listaEmp;
        this.m_onInterface = m_onInterface;
    }


    @Override
    public EmpleadoAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empleado, parent, false);
        EmpleadoAdaptador.ViewHolder viewHolder = new EmpleadoAdaptador.ViewHolder(view, m_onInterface);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EmpleadoAdaptador.ViewHolder holder, int position) {
        holder.mNombre.setText(listaEmpleado.get(position).getNombre());
        holder.mDNI.setText(listaEmpleado.get(position).getDni());
        holder.mTelef.setText(listaEmpleado.get(position).getTelef());
        holder.mCargo.setText(listaEmpleado.get(position).getCargo());
    }

    @Override
    public int getItemCount() {
        return listaEmpleado.size();
    }

    public interface OnInterface{
        void onClick(int position);
    }
}
