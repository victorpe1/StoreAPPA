package com.example.tienda.Adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tienda.Clase.Pedido;
import com.example.tienda.R;

import java.util.ArrayList;

public class PedidoAdaptador extends RecyclerView.Adapter<PedidoAdaptador.ViewHolder>{

        public ArrayList<Pedido> listaProductoPedido;
        OnInterface m_onInterface;


        public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
                private TextView mNombre, mCantidad, mPrecio_Unit, mSubtotal;
                PedidoAdaptador.OnInterface onInterface;

                public ViewHolder(View item, PedidoAdaptador.OnInterface on) {
                        super(item);
                        mNombre = (TextView) item.findViewById(R.id.nomProducto);
                        mCantidad = (TextView) item.findViewById(R.id.stockProducto);
                        mPrecio_Unit = (TextView) item.findViewById(R.id.precio_producto);
                        mSubtotal = (TextView) item.findViewById(R.id.subTotalProd);
                        this.onInterface = on;

                        item.setOnClickListener(this);
                }

                @Override
                public void onClick(View v) {
                        onInterface.onClick(getAdapterPosition());
                }

        }


        public PedidoAdaptador (ArrayList<Pedido> listaProduct, PedidoAdaptador.OnInterface m_onInterface){
                this.listaProductoPedido = listaProduct;
                this.m_onInterface = m_onInterface;
        }

        public PedidoAdaptador (ArrayList<Pedido> listaProduct){
                this.listaProductoPedido = listaProduct;
        }



        @Override
        public PedidoAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido_producto, parent, false);
                PedidoAdaptador.ViewHolder viewHolder = new PedidoAdaptador.ViewHolder(view, m_onInterface);

                return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull PedidoAdaptador.ViewHolder holder, int position) {
                holder.mNombre.setText(listaProductoPedido.get(position).getProducto());
                holder.mCantidad.setText(String.valueOf(listaProductoPedido.get(position).getCantidad()));
                holder.mPrecio_Unit.setText(String.valueOf(listaProductoPedido.get(position).getPrecio()));
                holder.mSubtotal.setText(String.valueOf(listaProductoPedido.get(position).getSubprecio()));
        }

        @Override
        public int getItemCount() {
                return listaProductoPedido.size();
        }


        public interface OnInterface{
                void onClick(int position);
        }


}