package com.example.elpaseov4.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.elpaseov4.R;
import com.example.elpaseov4.adapters.CategoryAdapter;
import com.example.elpaseov4.adapters.NewsAdapter;
import com.example.elpaseov4.adapters.ProducerAdapter;
import com.example.elpaseov4.adapters.ProductAdapter;
import com.example.elpaseov4.model.Category;
import com.example.elpaseov4.model.News;
import com.example.elpaseov4.model.Producer;
import com.example.elpaseov4.model.Product;
import com.example.elpaseov4.network.Pagination;
import com.example.elpaseov4.network.PaginationNews;
import com.example.elpaseov4.network.RetrofitClientInstance;
import com.example.elpaseov4.network.ServiceRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainHomeFragment extends Fragment {

    private RecyclerView mRecyclerViewNews;
    private RecyclerView mRecyclerViewProducer;
    private RecyclerView.LayoutManager mLayoutManagerNews;
    private RecyclerView.LayoutManager mLayoutManagerProducer;
    private RecyclerView.Adapter mAdapterNews;
    private RecyclerView.Adapter mAdapterProducer;
    private ServiceRetrofit service;
    private ImageButton facebook;
    private ImageButton instagram;

    public MainHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);

        service = RetrofitClientInstance.getRetrofitInstance().create(ServiceRetrofit.class);
        System.out.println("onCreatedView");
        cargarNoticias(service,view);
        cargatProducers(service, view);

        facebook = view.findViewById(R.id.imageButtonFacebook);
        instagram = view.findViewById(R.id.imageButtonInstagram);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "abrir Facebook" ,Toast.LENGTH_LONG).show();
                Uri link = Uri.parse("https://www.facebook.com/paseo.unlp");
                Intent i = new Intent(Intent.ACTION_VIEW,link);
                v.getContext().startActivity(i);
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "abrir Instagram" ,Toast.LENGTH_LONG).show();
                Uri link = Uri.parse("https://www.instagram.com/paseo.unlp/");
                Intent i = new Intent(Intent.ACTION_VIEW,link);
                v.getContext().startActivity(i);
            }
        });

        return view;
    }

    private void cargarNoticias(ServiceRetrofit service, View view) {
        mRecyclerViewNews = view.findViewById(R.id.recyclerViewNews);

        mLayoutManagerNews = new LinearLayoutManager(this.getActivity(), RecyclerView.HORIZONTAL,false);
        mRecyclerViewNews.setLayoutManager(mLayoutManagerNews);
        mRecyclerViewNews.setItemAnimator(new DefaultItemAnimator());

        service.getNews().enqueue(new Callback<PaginationNews>() {
            @Override
            public void onResponse(Call<PaginationNews> call, Response<PaginationNews> response) {
                //Toast.makeText(view.getContext(), "Traje las noticiasasss" ,Toast.LENGTH_LONG).show();
                mAdapterNews = new NewsAdapter(response.body().getPage(), R.layout.recycler_view_item_news_list_horizontal, new NewsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(final News p, int position) {

                    }
                });
                mRecyclerViewNews.setAdapter(mAdapterNews);
            }

            @Override
            public void onFailure(Call<PaginationNews> call, Throwable t) {
                Toast.makeText(view.getContext(), "no traje las noticiasasssssss" ,Toast.LENGTH_LONG).show();

            }
        });
    }


    private void cargatProducers(ServiceRetrofit service, View view){
        mRecyclerViewProducer = view.findViewById(R.id.recyclerViewProducers);

        mLayoutManagerProducer = new LinearLayoutManager(this.getActivity(), RecyclerView.HORIZONTAL,false);
        mRecyclerViewProducer.setLayoutManager(mLayoutManagerProducer);
        mRecyclerViewProducer.setItemAnimator(new DefaultItemAnimator());

        service.getProducers().enqueue(new Callback<List<Producer>>() {
            @Override
            public void onResponse(Call<List<Producer>> call, Response<List<Producer>> response) {
                Toast.makeText(view.getContext(), "Traje los productores" ,Toast.LENGTH_LONG).show();
                mAdapterProducer = new ProducerAdapter(response.body(), getContext(), R.layout.recycler_view_item_producer_list_horizontal, new ProducerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Producer name, int position) {

                    }
                });
                mRecyclerViewProducer.setAdapter(mAdapterProducer);
            }

            @Override
            public void onFailure(Call<List<Producer>> call, Throwable t) {
                Toast.makeText(view.getContext(), "no Traje los productores" ,Toast.LENGTH_LONG).show();

            }
        });

    }
}