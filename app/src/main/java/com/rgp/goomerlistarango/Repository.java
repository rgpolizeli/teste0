package com.rgp.goomerlistarango;


import com.rgp.goomerlistarango.models.Item;
import com.rgp.goomerlistarango.models.Restaurant;
import com.rgp.goomerlistarango.web.WebRxService;

import io.reactivex.Observable;

public class Repository {

    private static Repository repositoryInstance;
    private final WebRxService webRxService;

    private Repository() {
        webRxService = new WebRxService();
    }

    public static Repository getInstance() {
        if (repositoryInstance == null) {
            repositoryInstance = new Repository();
        }
        return repositoryInstance;
    }

    public Observable<Restaurant[]> getAllRestaurants() {
        return webRxService.getAllRestaurants();
    }

    public Observable<Item[]> getItemsByRestaurantId(int restaurantId) {
        return webRxService.getItemsByRestaurantId(restaurantId);
    }

}
