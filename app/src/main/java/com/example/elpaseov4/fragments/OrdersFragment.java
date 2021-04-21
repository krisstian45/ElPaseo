package com.example.elpaseov4.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.elpaseov4.R;
import com.example.elpaseov4.activities.MainActivity;
import com.example.elpaseov4.model.User;
import com.example.elpaseov4.network.CartPostResponse;
import com.example.elpaseov4.network.RetrofitClientInstance;
import com.example.elpaseov4.network.ServiceRetrofit;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrdersFragment extends Fragment {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private boolean sesion;
    private ServiceRetrofit service;

    private TextView textView;
    private String properties = "[{\"key\": \"history\", \"value\": true}]";
        private String range = "1,20";
        private String fieldsTosort = "id,desc";

    public OrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        inicializar(view);
        if (sesion){
            textView.setText("tenemos sesion");

            service.getCart("Bearer "+obtenerToken().trim(), range, fieldsTosort, properties).enqueue(new Callback<List<CartPostResponse>>() {
                @Override
                public void onResponse(Call<List<CartPostResponse>> call, Response<List<CartPostResponse>> response) {
                    response.body();
                }

                @Override
                public void onFailure(Call<List<CartPostResponse>> call, Throwable t) {

                }
            });


        }else {
            textView.setText("no tenemos sesion");
        }
        return view;
    }

    private void inicializar( View view){
        service = RetrofitClientInstance.getRetrofitInstance().create(ServiceRetrofit.class);
        textView = view.findViewById(R.id.textViewOrders);
        sesion = sesion();
    }
    private String obtenerToken(){
        preferences =  this.getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String value = preferences.getString("value",null);
        return value;
    }

    private boolean sesion(){
        preferences =  this.getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String mail = preferences.getString("value",null);
        //String mail = "juan pedro";
        boolean local = false;
        if (mail != null ){
            local =  true;
        }
        return local;
    }
}