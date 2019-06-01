package com.rgp.goomerlistarango.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private Observable<Restaurant[]> restaurantsObservable;

    private RestaurantsObserver restaurantsObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add this to fix landscape orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

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
            //startItemsActivity(clickedRestaurant.getId());
        });

        //create observables
        restaurantsObservable = this.restaurantsViewModel.getAllRestaurants();

        //create observers
        this.restaurantsObserver = new RestaurantsObserver(this.restaurantsRVAdapter);

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
