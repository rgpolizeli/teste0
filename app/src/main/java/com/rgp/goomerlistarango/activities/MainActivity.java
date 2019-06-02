package com.rgp.goomerlistarango.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.rgp.goomerlistarango.R;
import com.rgp.goomerlistarango.adapters.RestaurantsRVAdapter;
import com.rgp.goomerlistarango.listeners.OnItemClickListener;
import com.rgp.goomerlistarango.models.Restaurant;
import com.rgp.goomerlistarango.observers.RestaurantsObserver;
import com.rgp.goomerlistarango.viewmodels.RestaurantsViewModel;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RestaurantsViewModel restaurantsViewModel;
    private RecyclerView restaurantsRecyclerView;
    private RestaurantsRVAdapter restaurantsRVAdapter;
    public static final String RESTAURANT_JSON = "RESTAURANT_JSON";

    private Observable<Restaurant[]> restaurantsObservable;

    private RestaurantsObserver restaurantsObserver;
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add this to fix landscape orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        //remove title from SupportActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        this.restaurantsViewModel = ViewModelProviders.of(this).get(RestaurantsViewModel.class);

        restaurantsRecyclerView = this.findViewById(R.id.restaurantsRecyclerView);
        int numberOfColumns = 3;
        RecyclerView.LayoutManager restaurantsRecyclerViewLayoutManager = new GridLayoutManager(this, numberOfColumns);
        restaurantsRecyclerView.setLayoutManager(restaurantsRecyclerViewLayoutManager);
        OnItemClickListener onItemClickListener = new OnItemClickListener();
        restaurantsRVAdapter = new RestaurantsRVAdapter(new ArrayList<>(), onItemClickListener);
        restaurantsRecyclerView.setAdapter(restaurantsRVAdapter);

        onItemClickListener.getClickedView().observe(this, view -> {
            int position = 0;
            if (view != null) {
                position = restaurantsRecyclerView.getChildAdapterPosition(view);
            }
            Restaurant clickedRestaurant = restaurantsRVAdapter.getItem(position);
            startItemsActivity(clickedRestaurant);
        });

        //create observables
        restaurantsObservable = this.restaurantsViewModel.getAllRestaurants();

        //create observers
        this.restaurantsObserver = new RestaurantsObserver(this.restaurantsRVAdapter);

    }

    private void startItemsActivity(Restaurant targetRestaurant) {
        Intent intent = new Intent(this, ItemsActivity.class);

        String restaurantJson = gson.toJson(targetRestaurant);
        intent.putExtra(MainActivity.RESTAURANT_JSON, restaurantJson);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //subscribe to observables
        this.restaurantsObservable
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this.restaurantsObserver);

    }

    protected void onStop() {
        super.onStop();
        //dispose observables
        this.restaurantsObserver.dispose();
    }


}
