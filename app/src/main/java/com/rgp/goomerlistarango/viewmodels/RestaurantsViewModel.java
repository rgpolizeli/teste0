package com.rgp.goomerlistarango.viewmodels;

import androidx.lifecycle.ViewModel;

import com.rgp.goomerlistarango.Repository;
import com.rgp.goomerlistarango.models.Item;
import com.rgp.goomerlistarango.models.Restaurant;

import io.reactivex.Observable;

public class RestaurantsViewModel extends ViewModel {
    private Repository repository = Repository.getInstance();

    public Observable<Restaurant[]> getAllRestaurants() {
        return repository.getAllRestaurants();
    }

    public Observable<Item[]> getItemsByRestaurantId(int restaurantId) {
        return repository.getItemsByRestaurantId(restaurantId);
    }


}
