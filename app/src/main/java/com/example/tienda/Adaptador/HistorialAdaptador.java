package com.example.tienda.Adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tienda.Clase.Historial;
import com.example.tienda.Clase.Pedido;
import com.example.tienda.R;

import java.util.ArrayList;

public class HistorialAdaptador extends RecyclerView.Adapter<HistorialAdaptador.ViewHolder>{

    public ArrayList<Historial> listaHistorial;
    HistorialAdaptador.OnInterface m_onInterface;


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mFecha, mEstado, mTotal;
        HistorialAdaptador.OnInterface onInterface;

        public ViewHolder(View item, HistorialAdaptador.OnInterface on) {
            super(item);
            mFecha = (TextView) item.findViewById(R.id.fecha);
            mEstado = (TextView) item.findViewById(R.id.estado);
            mTotal = (TextView) item.findViewById(R.id.precio_final);
            this.onInterface = on;

            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onInterface.onClick(getAdapterPosition());
        }

    }

    public HistorialAdaptador (ArrayList<Historial> listaHistorial, HistorialAdaptador.OnInterface m_onInterface){
        this.listaHistorial = listaHistorial;
        this.m_onInterface = m_onInterface;
    }

    @Override
    public HistorialAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido, parent, false);
        HistorialAdaptador.ViewHolder viewHolder = new HistorialAdaptador.ViewHolder(view, m_onInterface);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialAdaptador.ViewHolder holder, int position) {
        holder.mFecha.setText(listaHistorial.get(position).getFecha());
        holder.mEstado.setText(listaHistorial.get(position).getEstado());
        holder.mTotal.setText(String.valueOf(listaHistorial.get(position).getTotal()));
    }

    @Override
    public int getItemCount() {
        return listaHistorial.size();
    }


    public interface OnInterface{
        void onClick(int position);
    }
}
