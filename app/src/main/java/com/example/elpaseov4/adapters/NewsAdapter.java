package com.example.elpaseov4.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elpaseov4.R;
import com.example.elpaseov4.model.Category;
import com.example.elpaseov4.model.Imagen;
import com.example.elpaseov4.model.News;

import java.net.URI;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<News> news;
    public int layout;
    private NewsAdapter.OnItemClickListener itemClickListener;

    public NewsAdapter(List<News> news, int layout, OnItemClickListener itemClickListener) {
        this.news = news;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent,false);
        NewsAdapter.ViewHolder viewHolder = new NewsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        holder.bind(news.get(position) , itemClickListener);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri link = Uri.parse(news.get(position).getUrl());
                Intent i = new Intent(Intent.ACTION_VIEW,link);
                v.getContext().startActivity(i);
                Log.d("click","ok");
            }
        });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
       // public TextView nameCategory ;
        //public TextView idCategory;
        public ImageView imageNews;

        public ViewHolder(View view){
            super(view);
           // this.nameCategory =  view.findViewById(R.id.textViewCategoryName);
            //this.idCategory = view.findViewById(R.id.textViewIdCategory);
            this.imageNews = view.findViewById(R.id.imageNewsList);


        }

        public void bind(final News c, final NewsAdapter.OnItemClickListener listener){
           // this.nameCategory.setText(c.getName());
           // this.idCategory.setText(c.getId().toString());
            this.imageNews.setImageBitmap(setImagen(c));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(c,getAdapterPosition());
                }
            });
        }

        private Bitmap setImagen(News c) {

            String base64String = c.getImage().getValue();
            String base64Image = base64String.split(",")[1];

            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);

            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(News name, int position);
    }
}
