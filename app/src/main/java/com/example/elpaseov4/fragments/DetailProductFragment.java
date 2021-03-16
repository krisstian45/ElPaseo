package com.example.elpaseov4.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elpaseov4.R;
import com.example.elpaseov4.adapters.CategoryAdapter;
import com.example.elpaseov4.adapters.ImagesAdapter;
import com.example.elpaseov4.model.Category;
import com.example.elpaseov4.model.Imagen;
import com.example.elpaseov4.model.Product;
import com.example.elpaseov4.repository.CartLocal;

import java.util.List;

import retrofit2.http.POST;


public class DetailProductFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private ImagesAdapter mAdapter2;
    private ListView mListView;

    private ImageView imageViewDetail;
    private TextView textViewTitle;
    private TextView textViewPrice;
    private TextView textViewBrand;
    private TextView textViewIsPromotion;
    private TextView textViewDescripcion;
    private List<Product> cart= CartLocal.getInstance();
    private Product productDetail;

    public DetailProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_product, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Product productDetail = (Product)bundle.getSerializable("producto");
            imageViewDetail = view.findViewById(R.id.imgItemDetail);
            textViewTitle = view.findViewById(R.id.textViewTitleDetail);
            textViewDescripcion = view.findViewById(R.id.textViewDescriptionDetail);
            textViewPrice = view.findViewById(R.id.textViewItemPrice);
            textViewBrand = view.findViewById(R.id.textViewItemBrand);
            textViewIsPromotion = view.findViewById(R.id.textViewItemIsPromotion);

            imageViewDetail.setImageBitmap(obtenerImagen(productDetail));
            textViewTitle.setText(productDetail.getTitle());
            textViewDescripcion.setText(productDetail.getDescription());
            textViewPrice.setText(productDetail.getPrice().toString()+" $");
            textViewBrand.setText(productDetail.getBrand());
            if (productDetail.isPromotion() ){
                textViewIsPromotion.setText("En Promocion");
            }
            Button button = view.findViewById(R.id.btnAddDetail);
            if (!revisarSesion()){
                button.setVisibility(View.GONE);
            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cart.add(productDetail);
                }
            });


            /*mRecyclerView = view.findViewById(R.id.recyclerViewItemImages);

            mLayoutManager = new LinearLayoutManager(this.getActivity(),RecyclerView.HORIZONTAL,false);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());*/

            /*mListView = (ListView) view.findViewById(R.id.listViewImages);
            mAdapter2 = new ImagesAdapter(productDetail.getImages(),getContext());
            mListView.setAdapter(mAdapter2);*/

        }
        return view;
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

    private boolean revisarSesion(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String mail = preferences.getString("value",null);
        //String mail = "juan pedro";
        boolean local = false;
        if (mail != null ){
            local =  true;
        }
        return local;
    }
}