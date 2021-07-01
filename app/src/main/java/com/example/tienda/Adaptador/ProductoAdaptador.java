package com.example.tienda.Adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tienda.Clase.Producto;
import com.example.tienda.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductoAdaptador extends RecyclerView.Adapter<ProductoAdaptador.ViewHolder> {

    public ArrayList<Producto> listaProducto;
    OnInterface m_onInterface;



    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mNombre, mStock, mCateogia;
        OnInterface onInterface;

        public ViewHolder (View item, OnInterface on){
            super(item);
            mNombre = (TextView) item.findViewById(R.id.nomProducto);
            mStock = (TextView) item.findViewById(R.id.stockProducto);
            mCateogia = (TextView) item.findViewById(R.id.catProducto);
            this.onInterface = on;

            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onInterface.onClick(getAdapterPosition());
        }
    }


    public ProductoAdaptador (ArrayList<Producto> listaProduct, OnInterface m_onInterface){
        this.listaProducto = listaProduct;
        this.m_onInterface = m_onInterface;
    }



    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, m_onInterface);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoAdaptador.ViewHolder holder, int position) {
        holder.mNombre.setText(listaProducto.get(position).getNombre());
        String stock = String.valueOf(listaProducto.get(position).getStock());
        holder.mStock.setText(stock);
        holder.mCateogia.setText(listaProducto.get(position).getCategoria());
    }

    @Override
    public int getItemCount() {
        return listaProducto.size();
    }


    public interface OnInterface{
        void onClick(int position);
    }


}
