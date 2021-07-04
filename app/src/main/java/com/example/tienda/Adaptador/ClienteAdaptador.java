package com.example.tienda.Adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tienda.Clase.Cliente;
import com.example.tienda.Clase.Empleado;
import com.example.tienda.R;

import java.util.ArrayList;

public class ClienteAdaptador extends RecyclerView.Adapter<ClienteAdaptador.ViewHolder> {


    public ArrayList<Cliente> listaCliente;
    ClienteAdaptador.OnInterface m_onInterface;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mNombre, mDNI, mTelef, mGenero;
        ClienteAdaptador.OnInterface onInterface;

        public ViewHolder (View item, ClienteAdaptador.OnInterface on){
            super(item);
            mNombre = (TextView) item.findViewById(R.id.nomCliente);
            mDNI = (TextView) item.findViewById(R.id.dniCliente);
            mTelef = (TextView) item.findViewById(R.id.telefCliente);
            mGenero = (TextView) item.findViewById(R.id.sexo);
            this.onInterface = on;

            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onInterface.onClick(getAdapterPosition());
        }
    }

    public ClienteAdaptador (ArrayList<Cliente> listaCliente, ClienteAdaptador.OnInterface m_onInterface){
        this.listaCliente = listaCliente;
        this.m_onInterface = m_onInterface;
    }

    @Override
    public ClienteAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cliente, parent, false);
        ClienteAdaptador.ViewHolder viewHolder = new ClienteAdaptador.ViewHolder(view, m_onInterface);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteAdaptador.ViewHolder holder, int position) {
        holder.mNombre.setText(listaCliente.get(position).getNombre());
        holder.mDNI.setText(listaCliente.get(position).getDni());
        holder.mTelef.setText(listaCliente.get(position).getTelef());
        holder.mGenero.setText(listaCliente.get(position).getSexo());
    }

    @Override
    public int getItemCount() {
        return listaCliente.size();
    }

    public interface OnInterface{
        void onClick(int position);
    }


}
