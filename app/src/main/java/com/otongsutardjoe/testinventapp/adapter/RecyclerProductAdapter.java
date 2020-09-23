package com.otongsutardjoe.testinventapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.otongsutardjoe.testinventapp.databinding.ListProductItemBinding;
import com.otongsutardjoe.testinventapp.model.ProductAndPriceModel;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class RecyclerProductAdapter extends RecyclerView.Adapter<RecyclerProductAdapter.ViewHolder> {
    private Context context;
    private List<ProductAndPriceModel.DataValue> searchResultList;

    public RecyclerProductAdapter(Context context, List<ProductAndPriceModel.DataValue> searchResultList) {
        this.context = context;
        this.searchResultList = searchResultList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ListProductItemBinding itemListBinding = ListProductItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(itemListBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemListBinding.textProductName.setText(searchResultList.get(position).getNama_barang().toUpperCase());
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        holder.itemListBinding.textProductPrice.setText(formatRupiah.format(searchResultList.get(position).getHarga_barang()).replace("Rp", "Rp "));
        holder.itemListBinding.textProductBranch.setText("Cabang " + searchResultList.get(position).getCabang().toUpperCase());
        Picasso.get().load(searchResultList.get(position).getImage()).into(holder.itemListBinding.imageProduct);
    }

    @Override
    public int getItemCount() {
        return searchResultList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ListProductItemBinding itemListBinding;

        public ViewHolder(final ListProductItemBinding itemView) {
            super(itemView.getRoot());
            this.itemListBinding = itemView;
        }

    }

}
