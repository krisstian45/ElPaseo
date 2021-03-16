package com.example.elpaseov4.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.elpaseov4.R;
import com.example.elpaseov4.adapters.ProductAdapter;
import com.example.elpaseov4.adapters.ProductCartAdapter;
import com.example.elpaseov4.model.Product;
import com.example.elpaseov4.repository.CartLocal;

import java.util.List;


public class CartFragment extends Fragment {
    private List<Product> cart= CartLocal.getInstance();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    public TextView textViewSubPrice;
    public TextView textViewPriceTotal;
    public TextView textViewAddPropina;
    public EditText editTextPropina;


    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        cargarCarrito(view);
        textViewSubPrice = view.findViewById(R.id.textViewPriceCartSubTotal);
        textViewPriceTotal = view.findViewById(R.id.textViewPriceCartTotal);
        textViewAddPropina = view.findViewById(R.id.textViewAddPropina);

        editTextPropina = view.findViewById(R.id.editTextPropina);
        textViewSubPrice.setText(calcularTotal());
        textViewAddPropina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPropina();
            }
        });
        return view;
    }

    private void cargarCarrito(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerViewCart);

        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ProductCartAdapter(cart, R.layout.recycler_view_item_cart, new ProductCartAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final Product p, int position) {

            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
                textViewSubPrice.setText(calcularTotal());
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    public void removeItem(int position){
        cart.remove(position);
        mAdapter.notifyItemRemoved(position);
    }
    public void addPropina(){
        Integer temp1 = Integer.parseInt(calcularTotal());
        Integer temp2 = Integer.parseInt(editTextPropina.getText().toString());
        Integer valor = temp1 + temp2 ;
        textViewPriceTotal.setText(valor.toString());
    }

    private String calcularTotal(){
        Long total=new Long(0);
        for (Product p : cart
             ) {
            total += p.getPrice();
        }

        return total.toString();
    }
}