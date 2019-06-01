package com.rgp.goomerlistarango.observers;

import com.rgp.goomerlistarango.interfaces.I_RestaurantsRVAdapter;
import com.rgp.goomerlistarango.models.Restaurant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class RestaurantsObserver extends DisposableObserver<Restaurant[]> {

    private I_RestaurantsRVAdapter restaurantsRVAdapter;

    public RestaurantsObserver(I_RestaurantsRVAdapter restaurantsRVAdapter) {
        this.restaurantsRVAdapter = restaurantsRVAdapter;
    }

    @Override
    public void onNext(Restaurant[] restaurants) {
        if (restaurants != null) {
            List<Restaurant> restaurantsList = new ArrayList<>();
            Collections.addAll(restaurantsList, restaurants);
            restaurantsRVAdapter.updateRestaurants(restaurantsList);
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onComplete() {
    }
}
