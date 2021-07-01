package com.example.tienda.Adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tienda.Clase.Categoria;
import com.example.tienda.Clase.Empleado;
import com.example.tienda.R;

import java.util.ArrayList;

public class CategoriaAdaptador extends RecyclerView.Adapter<CategoriaAdaptador.ViewHolder>{

    public ArrayList<Categoria> listaCategoria;
    CategoriaAdaptador.OnInterface m_onInterface;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mNombre, mDetalle;
        CategoriaAdaptador.OnInterface onInterface;

        public ViewHolder (View item, CategoriaAdaptador.OnInterface on){
            super(item);
            mNombre = (TextView) item.findViewById(R.id.nomCat);
            mDetalle = (TextView) item.findViewById(R.id.detalCat);

            this.onInterface = on;

            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onInterface.onClick(getAdapterPosition());
        }
    }

    public CategoriaAdaptador (ArrayList<Categoria> listaCategoria, CategoriaAdaptador.OnInterface m_onInterface){
        this.listaCategoria = listaCategoria;
        this.m_onInterface = m_onInterface;
    }

    @Override
    public CategoriaAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria, parent, false);
        CategoriaAdaptador.ViewHolder viewHolder = new CategoriaAdaptador.ViewHolder(view, m_onInterface);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaAdaptador.ViewHolder holder, int position) {
        holder.mNombre.setText(listaCategoria.get(position).getNombre());
        holder.mDetalle.setText(listaCategoria.get(position).getDetalle());
    }

    @Override
    public int getItemCount() {
        return listaCategoria.size();
    }

    public interface OnInterface{
        void onClick(int position);
    }
}
