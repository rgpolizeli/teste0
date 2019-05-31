package com.rgp.goomerlistarango.web;

import com.rgp.goomerlistarango.models.Item;
import com.rgp.goomerlistarango.models.Restaurant;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Implements call operations' signatures to web api.
 */

public interface WebRxApi {

    @GET("restaurants/")
    Observable<Restaurant[]> restaurantsArray();

    @GET("restaurants/{id}/menu")
    Observable<Item[]> itemsArray(@Path("id") int restaurantId);
}
