package com.example.elpaseov4.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elpaseov4.R;
import com.example.elpaseov4.model.Imagen;
import com.example.elpaseov4.model.Product;

import java.util.List;

public class DetailProductActivity extends AppCompatActivity {
    ImageView imageViewDetail;
    TextView textViewDetail;
    TextView textViewDescripcion;
    private Product productDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        initViews();
        initValues();
    }
    private void initViews() {
        imageViewDetail = findViewById(R.id.imgItemDetail);
        textViewDetail = findViewById(R.id.titleDetail);
        textViewDescripcion = findViewById(R.id.descriptionDetail);
    }
    private void initValues() {
        productDetail = (Product) getIntent().getSerializableExtra("productDetail");

        textViewDetail.setText(productDetail.getTitle());
        textViewDescripcion.setText(productDetail.getDescription());
        imageViewDetail.setImageBitmap(obtenerImagen(productDetail));


    }

    private static Bitmap obtenerImagen(Product p) {
        List<Imagen> temp = p.getImages();
        Imagen local =null ;
        for (Imagen image: temp ) {
                if (image.getIsMain() == "true" ){
                    local = image;
                }

        }

        String base64String = local.getValue();
        String base64Image = base64String.split(",")[1];

        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);

        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

}