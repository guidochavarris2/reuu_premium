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

public class Adapter  extends RecyclerView.Adapter<Adapter.PlayerViewnHolder> {

    private Context mCtx;
    private List<Usuarios>productosList;
    private RecyclerViewAdapter.ItemClickListener mItemClickListener;
    public Adapter(Context mCtx, List<Usuarios> productosList, RecyclerViewAdapter.ItemClickListener itemClickListener){
        this.mCtx=mCtx;
        this.productosList=productosList;
        this.mItemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public PlayerViewnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view=inflater.inflate(R.layout.listados_home,null);

        return new PlayerViewnHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PlayerViewnHolder holder, int position) {
        Usuarios productos=productosList.get(position);
        Glide.with(mCtx)
                .load(productos.getimagen())
                .into(holder.img);
        holder.tv1.setText(productos.getCategoria());
        holder.tv2.setText(productos.getubicacion());
        holder.itemView.setOnClickListener(view ->  {
            mItemClickListener.onItemClick(productosList.get(position));
        });
    }



    @Override
    public int getItemCount() {
        return productosList.size();
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