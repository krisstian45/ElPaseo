package com.example.elpaseov4.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elpaseov4.R;
import com.example.elpaseov4.activities.DetailProductActivity;
import com.example.elpaseov4.activities.MainActivity;
import com.example.elpaseov4.fragments.DetailProductFragment;
import com.example.elpaseov4.model.Imagen;
import com.example.elpaseov4.model.Product;
import com.example.elpaseov4.repository.CartLocal;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> products;
    public int layout;
    private ProductAdapter.OnItemClickListener itemClickListener;
    private List<Product> cart= CartLocal.getInstance();
    private Context context;
    private boolean sesion;
    //private List<Product> cart= Cart.getInstance();

    public ProductAdapter(List<Product> products, Context context,boolean sesion,int layout, ProductAdapter.OnItemClickListener listener){

        this.products = products;
        this.context = context;
        this.layout = layout;
        this.sesion = sesion;
        this.itemClickListener = listener;
    }



    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent,false);
        ProductAdapter.ViewHolder viewHolder = new ProductAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        holder.bind(products.get(position) , itemClickListener);
        holder.itemView.findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Producto Agregado",Toast.LENGTH_LONG).show();
                cart.add(products.get(position));
                Toast.makeText(v.getContext(),"Cantidad de  " + cart.size(),Toast.LENGTH_LONG).show();

            }
        });
        if (sesion){
            holder.itemView.findViewById(R.id.btnAdd).setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment detailProductFragment = new DetailProductFragment();

                Bundle bundle = new Bundle();
                bundle.putSerializable("producto", products.get(position));
                detailProductFragment.setArguments(bundle);

                FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.container,detailProductFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title ;
        public TextView descripcion;
        public TextView precio;
        public ImageView image;

        public ViewHolder(View view){
            super(view);

            this.title =  view.findViewById(R.id.texViewTitle);
            this.descripcion = view.findViewById(R.id.textViewDescription);
            this.precio = view.findViewById(R.id.textViewPrice);
            this.image = view.findViewById(R.id.imageViewProductList);

        }

        public void bind(final Product p, final OnItemClickListener listener){
            this.title.setText(p.getTitle());
            this.descripcion.setText(p.getDescription());
            this.precio.setText(p.getPrice().toString());
            this.image.setImageBitmap(obtenerImagen(p));
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onViewClick(p,getAdapterPosition());
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(p,getAdapterPosition());

                }
            });
        }

    }

    public interface OnItemClickListener{

        void onItemClick(Product name, int position);
        void onViewClick(Product name, int position);
    }

    private static Bitmap obtenerImagen(Product p) {
        List<Imagen> temp = p.getImages();
        Imagen local =null ;
        for (Imagen image: temp ) {
            if (image.getIsMain() != null) {
                if (image.getIsMain() == "true" ){
                    local = image;
                }
            }
        }
                
        String base64String = local.getValue();
        String base64Image = base64String.split(",")[1];

        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);

        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
    

}
