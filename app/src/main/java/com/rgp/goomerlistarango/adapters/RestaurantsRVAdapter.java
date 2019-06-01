package com.rgp.goomerlistarango.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rgp.goomerlistarango.R;
import com.rgp.goomerlistarango.interfaces.I_RestaurantsRVAdapter;
import com.rgp.goomerlistarango.listeners.OnItemClickListener;
import com.rgp.goomerlistarango.models.Restaurant;

import java.util.List;

public class RestaurantsRVAdapter extends RecyclerView.Adapter<RestaurantsRVAdapter.ViewHolder> implements I_RestaurantsRVAdapter {
    private @NonNull
    final OnItemClickListener onClickListItemListener;
    private @NonNull
    List<Restaurant> restaurants;

    public RestaurantsRVAdapter(@NonNull final List<Restaurant> restaurants, @NonNull final OnItemClickListener onItemClickListener) {
        this.restaurants = restaurants;
        this.onClickListItemListener = onItemClickListener;
    }

    @Override
    public void updateRestaurants(@NonNull final List<Restaurant> restaurants) {
        this.restaurants = restaurants;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RestaurantsRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                              int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurants_recycler_view, parent, false);
        v.setOnClickListener(this.onClickListItemListener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imageUri = this.restaurants.get(position).getImage();
        Glide.with(holder.itemView.getContext()).load(imageUri).into(holder.restaurantImageView);
        holder.restaurantNameTextView.setText(this.restaurants.get(position).getName());
        holder.restaurantAddressTextView.setText(this.restaurants.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public Restaurant getItem(int position) {
        return restaurants.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView restaurantImageView;
        public TextView restaurantNameTextView;
        public TextView restaurantAddressTextView;
        public TextView restaurantStateTextView;

        public ViewHolder(View v) {
            super(v);
            restaurantImageView = v.findViewById(R.id.restaurantImageView);
            restaurantNameTextView = v.findViewById(R.id.restaurantNameTextView);
            restaurantAddressTextView = v.findViewById(R.id.restaurantAddressTextView);
            restaurantStateTextView = v.findViewById(R.id.restaurantStateTextView);
        }
    }

}
