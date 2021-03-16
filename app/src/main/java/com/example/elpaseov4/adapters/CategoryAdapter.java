package com.example.elpaseov4.adapters;


import android.content.Context;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elpaseov4.R;
import com.example.elpaseov4.activities.MainActivity;
import com.example.elpaseov4.fragments.DetailProductFragment;
import com.example.elpaseov4.fragments.LoginFragment;
import com.example.elpaseov4.fragments.SearchFragment;
import com.example.elpaseov4.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Category> categories;
    public int layout;
    private CategoryAdapter.OnItemClickListener itemClickListener;

    public CategoryAdapter(List<Category> categories, int layout, CategoryAdapter.OnItemClickListener listener){

        this.categories = categories;
        this.layout = layout;
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent,false);
        CategoryAdapter.ViewHolder viewHolder = new CategoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        holder.bind(categories.get(position) , itemClickListener);
        /*holder.itemView.findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               *//* Toast.makeText(v.getContext(),"Producto Agregado",Toast.LENGTH_LONG).show();
                cart.add(products.get(position));
                Toast.makeText(v.getContext(),"Cantidad de  " + cart.size(),Toast.LENGTH_LONG).show();*//*
            }
        });*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(v.getContext(), "miProducto : " + categories.get(position).getId(),Toast.LENGTH_LONG).show();

                Fragment loginFragment = new SearchFragment();


                /*Bundle bundle = new Bundle();
                bundle.putLong("categoriToSearch", categories.get(position).getId());
                loginFragment.setArguments(bundle);*/
                SharedPreferences preferences = MainActivity.getmInstanceActivityMain().getSharedPreferences("busqueda", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("search",categories.get(position).getId().toString());
                editor.apply();

                FragmentManager fragmentManager = MainActivity.getmInstanceActivityMain().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container,loginFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
            return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nameCategory ;
        public TextView idCategory;
        public ImageView imageCategory;

        public ViewHolder(View view){
            super(view);
            this.nameCategory =  view.findViewById(R.id.textViewCategoryName);
            this.idCategory = view.findViewById(R.id.textViewIdCategory);
            this.imageCategory = view.findViewById(R.id.imageCategoryList);


        }

        public void bind(final Category c, final CategoryAdapter.OnItemClickListener listener){
            this.nameCategory.setText(c.getName());
            this.idCategory.setText(c.getId().toString());
            this.imageCategory.setImageBitmap(setImagen(c));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(c,getAdapterPosition());
                }
            });
        }

        private Bitmap setImagen(Category c) {
            String base64String = c.getImage().getValue();
            String base64Image = base64String.split(",")[1];

            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);

            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Category name, int position);
    }
}
