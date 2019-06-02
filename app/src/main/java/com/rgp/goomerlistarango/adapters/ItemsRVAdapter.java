package com.rgp.goomerlistarango.adapters;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.rgp.goomerlistarango.R;
import com.rgp.goomerlistarango.interfaces.I_ItemsRecyclerViewAdapter;
import com.rgp.goomerlistarango.listeners.OnItemClickListener;
import com.rgp.goomerlistarango.models.Item;

import java.util.List;

public class ItemsRVAdapter extends RecyclerView.Adapter<ItemsRVAdapter.ViewHolder> implements I_ItemsRecyclerViewAdapter {
    private @NonNull
    final OnItemClickListener onClickListItemListener;
    private @NonNull
    List<Item> items;

    public ItemsRVAdapter(@NonNull final List<Item> items, @NonNull final OnItemClickListener onItemClickListener) {
        this.items = items;
        this.onClickListItemListener = onItemClickListener;
    }

    @Override
    public void updateItems(@NonNull final List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemsRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                        int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_items_recycler_view, parent, false);
        v.setOnClickListener(this.onClickListItemListener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //itemImageView
        String imageUri = this.items.get(position).getImage();

        Glide
                .with(holder.itemView.getContext())
                .load(imageUri)
                .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(4)))
                .fallback(new ColorDrawable(Color.GRAY))
                .into(holder.itemImageView);

        //itemNameTextView
        holder.itemNameTextView.setText(this.items.get(position).getName());

        //currentItemPriceTextView
        String price = holder.itemView.getContext().getResources().getString(R.string.price_unit);
        if (this.items.get(position).getPrice() > 0) {
            price = price + " " + this.items.get(position).getPrice();
        } else {
            price = price + " " + "-";
        }


        holder.currentItemPriceTextView.setText(price);

        holder.itemView.setTag(this.items.get(position).getGroup().toUpperCase());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Item getItem(int position) {
        return items.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView itemImageView;
        public TextView itemNameTextView;
        public TextView currentItemPriceTextView;

        public ViewHolder(View v) {
            super(v);
            itemImageView = v.findViewById(R.id.itemImageView);
            itemNameTextView = v.findViewById(R.id.itemNameTextView);
            currentItemPriceTextView = v.findViewById(R.id.currentItemPriceTextView);
        }
    }

}
