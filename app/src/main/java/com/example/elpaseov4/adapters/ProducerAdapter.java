package com.example.elpaseov4.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elpaseov4.R;
import com.example.elpaseov4.activities.DetailProducerFragment;
import com.example.elpaseov4.fragments.DetailProductFragment;
import com.example.elpaseov4.model.Producer;

import java.util.List;

public class ProducerAdapter extends RecyclerView.Adapter<ProducerAdapter.ViewHolder>{
    private List<Producer> producers;
    public int layout;
    private ProducerAdapter.OnItemClickListener itemClickListener;
    private Context context;

    public ProducerAdapter(List<Producer> producer, Context context,int layout, ProducerAdapter.OnItemClickListener listener){

        this.producers = producer;
        this.context = context;
        this.layout = layout;
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public ProducerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent,false);
        ProducerAdapter.ViewHolder viewHolder = new ProducerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProducerAdapter.ViewHolder holder, int position) {
        holder.bind(producers.get(position) , itemClickListener);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment detailProducerFragment = new DetailProducerFragment();

                Bundle bundle = new Bundle();
                bundle.putSerializable("productor", producers.get(position));
                detailProducerFragment.setArguments(bundle);

                FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.container,detailProducerFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return producers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name ;
        public ImageView imageList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name =  itemView.findViewById(R.id.textViewProducer);
            this.imageList = itemView.findViewById(R.id.imageProducerList);
        }

        public void bind(final Producer p, final ProducerAdapter.OnItemClickListener listener){
            this.name.setText(p.getName());
            if (p.getImages().size() != 0 ){
                this.imageList.setImageBitmap(obtenerImagen(p));
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(p,getAdapterPosition());

                }
            });
        }

    }

    public interface OnItemClickListener{
        void onItemClick(Producer name, int position);
    }

    private static Bitmap obtenerImagen(Producer p) {

        String base64String = p.getImages().get(0).getValue();
        String base64Image = base64String.split(",")[1];

        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);

        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}
