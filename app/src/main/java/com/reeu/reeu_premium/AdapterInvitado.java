package com.reeu.reeu_premium;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterInvitado extends RecyclerView.Adapter<AdapterInvitado.PlayerViewnHolder> {

    private Context mCtx;
    private List<Usuarios>productosList2;
    private RecyclerViewAdapter.ItemClickListener mItemClickListener;
    public AdapterInvitado(Context mCtx, List<Usuarios> productosList2, RecyclerViewAdapter.ItemClickListener itemClickListener){
        this.mCtx=mCtx;
        this.productosList2=productosList2;
        this.mItemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public PlayerViewnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view=inflater.inflate(R.layout.listados_evento,null);

        return new PlayerViewnHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PlayerViewnHolder holder, int position) {
        Usuarios productos=productosList2.get(position);
        Glide.with(mCtx)
                .load(productos.getimagen())
                .into(holder.img);
        holder.tv1.setText(productos.getCategoria());
        holder.tv2.setText(productos.getubicacion());
        holder.itemView.setOnClickListener(view ->  {
            mItemClickListener.onItemClick(productosList2.get(position));
        });
    }



    @Override
    public int getItemCount() {
        return productosList2.size();
    }



    static class PlayerViewnHolder extends  RecyclerView.ViewHolder{
        TextView tv1,tv2;
        ImageView img;
        public PlayerViewnHolder(@NonNull View itemView) {
            super(itemView);

            tv1=itemView.findViewById(R.id.tv1);
            tv2=itemView.findViewById(R.id.tv2);
            img=itemView.findViewById(R.id.img);
        }
    }
}