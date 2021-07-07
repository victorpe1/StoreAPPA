package com.example.tienda.Adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tienda.Clase.Historial;
import com.example.tienda.R;

import java.util.ArrayList;

public class PedidoFechaAdaptador extends RecyclerView.Adapter<PedidoFechaAdaptador.ViewHolder>{

    public ArrayList<Historial> listaPedidoFecha;
    PedidoFechaAdaptador.OnInterface m_onInterface;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mFecha, mDNI, mNombre, mTotal;
        PedidoFechaAdaptador.OnInterface onInterface;

        public ViewHolder(View item, PedidoFechaAdaptador.OnInterface on) {
            super(item);
            mFecha = (TextView) item.findViewById(R.id.fecha);
            mDNI = (TextView) item.findViewById(R.id.dniCliente);
            mNombre = (TextView) item.findViewById(R.id.nombreCliente);
            mTotal = (TextView) item.findViewById(R.id.precio_final);
            this.onInterface = on;

            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onInterface.onClick(getAdapterPosition());
        }

    }

    public PedidoFechaAdaptador(ArrayList<Historial> listaPedido, PedidoFechaAdaptador.OnInterface m_onInterface){
        this.listaPedidoFecha = listaPedido;
        this.m_onInterface = m_onInterface;
    }

    @Override
    public PedidoFechaAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido_filtrado, parent, false);
        PedidoFechaAdaptador.ViewHolder viewHolder = new PedidoFechaAdaptador.ViewHolder(view, m_onInterface);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoFechaAdaptador.ViewHolder holder, int position) {
        holder.mFecha.setText(listaPedidoFecha.get(position).getFecha());
        holder.mDNI.setText(listaPedidoFecha.get(position).getDNI());
        holder.mNombre.setText(listaPedidoFecha.get(position).getNomCli());
        holder.mTotal.setText(String.valueOf(listaPedidoFecha.get(position).getTotal()));
    }

    @Override
    public int getItemCount() {
        return listaPedidoFecha.size();
    }


    public interface OnInterface{
        void onClick(int position);
    }




}
