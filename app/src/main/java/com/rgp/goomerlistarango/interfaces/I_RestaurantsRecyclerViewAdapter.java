package com.rgp.goomerlistarango.interfaces;

import androidx.annotation.NonNull;

import com.rgp.goomerlistarango.models.Restaurant;

import java.util.List;

public interface I_RestaurantsRecyclerViewAdapter {
    void updateRestaurants(@NonNull final List<Restaurant> restaurants);
}

