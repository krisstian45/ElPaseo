package com.example.elpaseov4.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elpaseov4.R;
import com.example.elpaseov4.activities.DetailProductActivity;
import com.example.elpaseov4.activities.MainActivity;
import com.example.elpaseov4.adapters.CategoryAdapter;
import com.example.elpaseov4.adapters.ProductAdapter;
import com.example.elpaseov4.model.Category;
import com.example.elpaseov4.model.Product;
import com.example.elpaseov4.network.Pagination;
import com.example.elpaseov4.network.RetrofitClientInstance;
import com.example.elpaseov4.network.ServiceRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerViewCategories;
    private RecyclerView.LayoutManager mLayoutManagerCategories;
    private ServiceRetrofit service;
    private Long categoryIdToSearch;
    private boolean sesion;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private String patronBusqueda;

    String properties = "[{\"key\": \"categories.id\", \"value\": ? }]";
    String range = "1,20";
    String fieldsTosort = "id,desc";
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        obtenerPreferenciasDeBusqueda();
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sesion = revisarSesion();
        service = RetrofitClientInstance.getRetrofitInstance().create(ServiceRetrofit.class);

        cargarCategories(service,view );


        mRecyclerView = this.getView().findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        if (patronBusqueda == null){
            //Toast.makeText(view.getContext(), " patron de busqueda en null" ,Toast.LENGTH_LONG).show();
            service.getProducts().enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    System.out.println(response.code());
                    mAdapter = new ProductAdapter(response.body(), getContext(), sesion, R.layout.recycler_view_item_product_list, new ProductAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(final Product p, int position) {

                        }

                        @Override
                        public void onViewClick(Product name, int position) {
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            //fragmentTransaction.add(R.id.container,new DetailProductFragment());
                            fragmentTransaction.replace(R.id.container,new DetailProductFragment());
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            Log.d("que onda","ok");
                        }
                    });
                    mRecyclerView.setAdapter(mAdapter);
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {
                    System.out.println("Falla");
                    System.out.println(t.getMessage());
                }
            });

        }else {
            Toast.makeText(view.getContext(), " patron de busqueda con datos " + patronBusqueda ,Toast.LENGTH_LONG).show();
            properties = properties.replace("?",patronBusqueda);
          //  limpiarPreferenciaDeBusqueda();
            guardarPreferenciaDeBusqueda();
            service.getProducts(range,fieldsTosort,properties).enqueue(new Callback<Pagination>() {
                @Override
                public void onResponse(Call<Pagination> call, Response<Pagination> response) {
                    Toast.makeText(getContext(), " Traigo " + properties,Toast.LENGTH_LONG).show();
                    if (response.body().getPage().size() > 0){
                        mAdapter = new ProductAdapter(response.body().getPage(),getContext(),sesion,R.layout.recycler_view_item_product_list, new ProductAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Product name, int position) {

                            }

                            @Override
                            public void onViewClick(Product name, int position) {

                            }
                        });
                        mRecyclerView.setAdapter(mAdapter);
                    }else {
                        TextView textView = getView().findViewById(R.id.textViewBusquedaSearch);
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("Sin resultados en tu busqueda");
                    }
                }

                @Override
                public void onFailure(Call<Pagination> call, Throwable t) {
                    Toast.makeText(getContext(), " No traigo  " + properties,Toast.LENGTH_LONG).show();
                    System.out.println(t.getMessage());
                }
            });
        }
    }

    public void guardarPreferenciaDeBusqueda(){
        preferences = getActivity().getSharedPreferences("busqueda", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("search",null);
        editor.apply();

    }
    public void limpiarPreferenciaDeBusqueda(){
        preferences = getActivity().getSharedPreferences("busqueda", Context.MODE_PRIVATE);
        editor = preferences.edit();
        //editor.remove("busqueda");
        editor.clear().apply();

    }

    public String obtenerPreferenciasDeBusqueda(){
        preferences = this.getActivity().getSharedPreferences("busqueda", Context.MODE_PRIVATE);
        patronBusqueda  = preferences.getString("search",null);
        return  patronBusqueda;
    }

    public void cargarCategories(ServiceRetrofit service, View view){

        mRecyclerViewCategories = this.getView().findViewById(R.id.recyclerViewCategories);

        mLayoutManagerCategories = new LinearLayoutManager(this.getActivity(),RecyclerView.HORIZONTAL,false);
        mRecyclerViewCategories.setLayoutManager(mLayoutManagerCategories);
        mRecyclerViewCategories.setItemAnimator(new DefaultItemAnimator());

        service.getCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                //Toast.makeText(view.getContext(), "Traje las categorias" ,Toast.LENGTH_LONG).show();
                System.out.println(response.code());
                mAdapter = new CategoryAdapter(response.body(), R.layout.recycler_view_item_category_list_horizontal, new CategoryAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(final Category name, int position) {
                        //Toast.makeText(view.getContext(), "miProducto" + name,Toast.LENGTH_LONG).show();
                        //guardarPreferenciaDeBusqueda(name.getId().toString());
                    }
                });
                mRecyclerViewCategories.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });


    }

    private boolean revisarSesion(){
        preferences = this.getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String mail = preferences.getString("value",null);
        //String mail = "juan pedro";
        boolean local = false;
        if (mail != null ){
            local =  true;
        }
        return local;
    }
}