package com.example.elpaseov4.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elpaseov4.R;
import com.example.elpaseov4.model.Category;
import com.example.elpaseov4.model.Imagen;
import com.example.elpaseov4.model.Product;

import java.util.List;

public class ProductCartAdapter extends RecyclerView.Adapter<ProductCartAdapter.ViewHolder> {
    private List<Product> productsCart;
    public int layout;
    private ProductCartAdapter.OnItemClickListener itemClickListener;

    public ProductCartAdapter(List<Product> cart, int layout, ProductCartAdapter.OnItemClickListener listener) {
        this.productsCart = cart;
        this.layout = layout;
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public ProductCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent,false);
        ProductCartAdapter.ViewHolder viewHolder = new ProductCartAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCartAdapter.ViewHolder holder, int position) {
        holder.bind(productsCart.get(position) , itemClickListener);
    }


    @Override
    public int getItemCount() {
        return productsCart.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageProductCart;
        public TextView nameProductCart ;
        public TextView priceProductCart;
        public ImageButton mDeleteImage;

        public ViewHolder(View view){
            super(view);
            this.imageProductCart = view.findViewById(R.id.imageViewProductCart);
            this.nameProductCart =  view.findViewById(R.id.texViewProductCart);
            this.priceProductCart = view.findViewById(R.id.textViewProductCartPrice);
            this.mDeleteImage = view.findViewById(R.id.btnDeleteProductCart);


        }

        public void bind(final Product c, final ProductCartAdapter.OnItemClickListener listener){
            this.nameProductCart.setText(c.getTitle());
            this.priceProductCart.setText(c.getPrice().toString());
            this.imageProductCart.setImageBitmap(setImagen(c));

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteClick(getAdapterPosition());
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(c,getAdapterPosition());
                }
            });
        }

        private Bitmap setImagen(Product c) {

            List<Imagen> temp = c.getImages();
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
    }

    public interface OnItemClickListener{
        void onItemClick(Product name, int position);
        void onDeleteClick(int position);
    }
}
