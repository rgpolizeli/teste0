package com.rgp.goomerlistarango.viewmodels;

import androidx.lifecycle.ViewModel;

import com.rgp.goomerlistarango.Repository;
import com.rgp.goomerlistarango.models.Item;

import io.reactivex.Observable;

public class ItemsViewModel extends ViewModel {
    private Repository repository = Repository.getInstance();

    public Observable<Item[]> getItemsByRestaurantId(int restaurantId) {
        return repository.getItemsByRestaurantId(restaurantId);
    }
}
