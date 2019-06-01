package com.rgp.goomerlistarango.web;


import com.rgp.goomerlistarango.models.Item;
import com.rgp.goomerlistarango.models.Restaurant;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Build the api.
 */

public class WebRxService {
    private WebRxApi webRxApi;

    public WebRxService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://challange.goomer.com.br/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        webRxApi = retrofit.create(WebRxApi.class);
    }

    public Observable<Restaurant[]> getAllRestaurants() {
        return webRxApi.getAllRestaurants();
    }

    public Observable<Item[]> getItemsByRestaurantId(int restaurantId) {
        return webRxApi.getItemsByRestaurantId(restaurantId);
    }

}
