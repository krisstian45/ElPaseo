package com.example.elpaseov4.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elpaseov4.R;
import com.example.elpaseov4.model.Producer;
import com.example.elpaseov4.model.Product;


public class DetailProducerFragment extends Fragment {
    private ImageView imageViewDetail;
    private TextView textViewName;
    private TextView textViewDescripcion;
    private TextView textViewOrigin;
    private Product productDetail;

    public DetailProducerFragment() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_producer, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Producer producerDetail = (Producer)bundle.getSerializable("productor");
            if (producerDetail.getImages().size() != 0 ){
                this.imageViewDetail = view.findViewById(R.id.imageViewDetailProducer);
                this.imageViewDetail.setImageBitmap(obtenerImagen(producerDetail));
            }
            textViewName = view.findViewById(R.id.textViewDetailProducerName);
            textViewOrigin = view.findViewById(R.id.textViewDetailProducerOrigin);
            textViewDescripcion = view.findViewById(R.id.textViewDetailProducerDescription);

            textViewName.setText(producerDetail.getName());
            textViewDescripcion.setText(producerDetail.getDescription());
            textViewOrigin.setText(producerDetail.getOrigen());

        }

        return view;
    }

    private static Bitmap obtenerImagen(Producer p) {

        String base64String = p.getImages().get(0).getValue();
        String base64Image = base64String.split(",")[1];

        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);

        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}