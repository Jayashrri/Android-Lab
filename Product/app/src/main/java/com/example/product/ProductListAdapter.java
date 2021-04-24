package com.example.product;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private List<Product> list;
    private Context ctx;

    public ProductListAdapter(List<Product> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProductListAdapter.ViewHolder holder, int position) {
        Product product = list.get(position);
        holder.nameView.setText(product.getName());
        holder.idView.setText(String.valueOf(product.getId()));
        holder.mrpView.setText(String.format("%.0f", product.getMrp()));
        holder.priceView.setText(String.format("%.0f", product.getPrice()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameView;
        public TextView idView;
        public TextView mrpView;
        public TextView priceView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameView = itemView.findViewById(R.id.productName);
            idView = itemView.findViewById(R.id.productId);
            mrpView = itemView.findViewById(R.id.productMrp);
            priceView = itemView.findViewById(R.id.productPrice);
        }
    }
}
