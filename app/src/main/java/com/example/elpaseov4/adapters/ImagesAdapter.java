package com.example.elpaseov4.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.elpaseov4.R;
import com.example.elpaseov4.model.Imagen;

import java.util.ArrayList;
import java.util.List;

public class ImagesAdapter extends BaseAdapter {
    private List<Imagen> images;
    private Context context;

    public ImagesAdapter(List<Imagen> images, Context context) {
        this.images = images;
        this.context = context;
    }

    private static Bitmap obtenerImagen(Imagen p) {

        String base64String = p.getValue();
        String base64Image = base64String.split(",")[1];

        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);

        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Imagen imagen=(Imagen) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_product_imagen_list_horizontal,null);
        ImageView imageView = convertView.findViewById(R.id.imageViewProductImagenDetailList);

        imageView.setImageBitmap(obtenerImagen(imagen));
        return convertView;
    }
}
