package com.rgp.goomerlistarango.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.rgp.goomerlistarango.R;
import com.rgp.goomerlistarango.adapters.ItemsRVAdapter;
import com.rgp.goomerlistarango.interfaces.I_DispatcherItemsToRespectiveAdapter;
import com.rgp.goomerlistarango.listeners.OnItemClickListener;
import com.rgp.goomerlistarango.models.Hour;
import com.rgp.goomerlistarango.models.Item;
import com.rgp.goomerlistarango.models.Restaurant;
import com.rgp.goomerlistarango.observers.ItemsObserver;
import com.rgp.goomerlistarango.viewmodels.ItemsViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ItemsActivity extends AppCompatActivity implements I_DispatcherItemsToRespectiveAdapter {

    //Gson to deserialize restaurant object from intent
    private final Gson gson = new Gson();
    private Restaurant restaurant;
    private ItemsViewModel itemsViewModel;

    private int maxNumberOfItemsGroups = 4;

    private Map<Integer, RecyclerView> recyclerViewsMap;

    private Observable<Item[]> itemsObservable;
    private ItemsObserver itemsObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        setupActionBar();
        this.itemsViewModel = ViewModelProviders.of(this).get(ItemsViewModel.class);
        getRestaurantDataFromIntent();
        setRestaurantData();
        setupItemsRecyclerView();
        //create observables
        itemsObservable = this.itemsViewModel.getItemsByRestaurantId(this.restaurant.getId());

        //create observers
        this.itemsObserver = new ItemsObserver(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //subscribe to observables
        this.itemsObservable
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this.itemsObserver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //dispose observables
        this.itemsObserver.dispose();
    }

    /**
     * Configure ActionBar.
     */
    private void setupActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //remove title from SupportActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Configure Restaurants Recycler View and set its onItemClickListener.
     */
    private void setupItemsRecyclerView() {
        this.recyclerViewsMap = new HashMap<>();

        int numberOfColumns = 2;

        RecyclerView recyclerView;
        ItemsRVAdapter adapter;
        OnItemClickListener onItemClickListener = buildOnItemClickListener();

        for (int groupId = 0; groupId < this.maxNumberOfItemsGroups; groupId++) {
            recyclerView = getRecyclerViewByGroupId(groupId);
            recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
            this.recyclerViewsMap.put(groupId, recyclerView);

            adapter = new ItemsRVAdapter(new ArrayList<>(), onItemClickListener);
            recyclerView.setAdapter(adapter);
        }
    }

    /**
     * Creates and register listener to handle click on any item of any recycler view.
     *
     * @return the onItemClickListener.
     */

    private OnItemClickListener buildOnItemClickListener() {
        OnItemClickListener onItemClickListener = new OnItemClickListener();
        onItemClickListener.getClickedView().observe(this, view -> {
            int position = -1;
            int groupIdOfClickedView = -1;

            if (view != null) {
                groupIdOfClickedView = findGroupIdOfClickedView(view);
                if (groupIdOfClickedView != -1 && this.recyclerViewsMap.containsKey(groupIdOfClickedView)) {
                    position = this.recyclerViewsMap.get(groupIdOfClickedView).getChildAdapterPosition(view);
                    ItemsRVAdapter itemsRVAdapter = (ItemsRVAdapter) this.recyclerViewsMap.get(groupIdOfClickedView).getAdapter();
                    Item clickedItem = itemsRVAdapter.getItem(position);
                    showItemDialog(clickedItem);
                }
            }
        });
        return onItemClickListener;
    }

    /**
     * Show the dialog with clicked item' information.
     *
     * @param clickedItem
     */
    private void showItemDialog(Item clickedItem) {
    }

    /**
     * Get the recycler view of the items group.
     *
     * @param groupId
     * @return a recycler view of the recyclerViewsMap.
     */

    private RecyclerView getRecyclerViewByGroupId(int groupId) {
        RecyclerView recyclerView = null;
        switch (groupId) {
            case 0:
                recyclerView = findViewById(R.id.dinnerRecyclerView);
                break;
            case 1:
                recyclerView = findViewById(R.id.drinkRecyclerView);
                break;
            case 2:
                recyclerView = findViewById(R.id.dessertRecyclerView);
                break;
            case 3:
                recyclerView = findViewById(R.id.accompanimentsRecyclerView);
                break;
        }
        return recyclerView;
    }

    private int findGroupIdOfClickedView(View view) {
        for (int groupId = 0; groupId < this.maxNumberOfItemsGroups; groupId++) {
            if (this.recyclerViewsMap.containsKey(groupId)) {
                if (this.recyclerViewsMap.get(groupId).getChildAdapterPosition(view) != RecyclerView.NO_POSITION) {
                    return groupId;
                }
            } else {
                return -1;
            }
        }
        return -1;
    }


    /**
     * Get the restaurant object from intent via gson or finish activity in this attempt.
     */
    private void getRestaurantDataFromIntent() {
        String restaurantJson = getIntent().getStringExtra(MainActivity.RESTAURANT_JSON);
        if (restaurantJson == null || restaurantJson.isEmpty()) {
            finish();
        } else {
            restaurant = gson.fromJson(restaurantJson, Restaurant.class);
        }
    }

    /**
     * Fill information of restaurant.
     */
    private void setRestaurantData() {
        TextView restaurantNameTextView = findViewById(R.id.restaurantNameTextView);
        restaurantNameTextView.setText(this.restaurant.getName());

        TextView restaurantAddressTextView = findViewById(R.id.restaurantAddressTextView);
        restaurantAddressTextView.setText(this.restaurant.getAddress());

        ImageView restaurantImageView = findViewById(R.id.restaurantImageView);
        Glide
                .with(this)
                .load(this.restaurant.getImage())
                .apply(new RequestOptions().transform(new CenterCrop()))
                .into(restaurantImageView);

        setHours();
    }

    /**
     * Fill initially the weekdays' textviews with respective hour.
     */
    private void setHours() {
        TextView hourTextView = null;
        String completeHour;

        for (Hour hour : this.restaurant.getHours()) {
            completeHour =
                    hour.getFrom()
                            + " "
                            + getResources().getString(R.string.hour_separator)
                            + " "
                            + hour.getTo()
            ;

            for (Integer weekday : hour.getDays()) {

                if (weekday == 1) {
                    hourTextView = findViewById(R.id.hourMondayTextView);
                } else if (weekday == 2) {
                    hourTextView = findViewById(R.id.hourSundayTextView);
                } else if (weekday == 3) {
                    hourTextView = findViewById(R.id.hourTuesdayTextView);
                } else if (weekday == 4) {
                    hourTextView = findViewById(R.id.hourWednesdayTextView);
                } else if (weekday == 5) {
                    hourTextView = findViewById(R.id.hourThursdayTextView);
                } else if (weekday == 6) {
                    hourTextView = findViewById(R.id.hourFridayTextView);
                } else if (weekday == 7) {
                    hourTextView = findViewById(R.id.hourSaturdayTextView);
                }

                if (hourTextView != null) {
                    hourTextView.setText(completeHour);
                }
            }
        }
    }

    /**
     * Handle click in dinnerGroupHeaderConstraintLayout.
     *
     * @param view the dinnerGroupHeaderConstraintLayout.
     */
    public void dinnerGroupHeaderOnClick(View view) {
        if (this.recyclerViewsMap.containsKey(0)) {
            RecyclerView recyclerView = this.recyclerViewsMap.get(0);
            ImageView expandableImageView;
            if (recyclerView.getVisibility() == View.GONE) { //fechar todas as outras abertas.
                recyclerView.setVisibility(View.VISIBLE);
                expandableImageView = view.findViewById(R.id.dinnerGroupExpandableImageView);
                expandableImageView.setImageResource(R.drawable.ic_expanded);
                hideAllOtherGroups(0);
            }
        }
    }

    /**
     * Handle click in drinkGroupHeaderConstraintLayout.
     *
     * @param view the drinkGroupHeaderConstraintLayout.
     */
    public void drinkGroupHeaderOnClick(View view) {
        if (this.recyclerViewsMap.containsKey(1)) {
            RecyclerView recyclerView = this.recyclerViewsMap.get(1);
            ImageView expandableImageView;
            if (recyclerView.getVisibility() == View.GONE) {
                recyclerView.setVisibility(View.VISIBLE);
                expandableImageView = view.findViewById(R.id.drinkGroupExpandableImageView);
                expandableImageView.setImageResource(R.drawable.ic_expanded);
                hideAllOtherGroups(1);
            }
        }
    }

    /**
     * Handle click in dessertGroupHeaderConstraintLayout.
     *
     * @param view the dessertGroupHeaderConstraintLayout.
     */
    public void dessertGroupHeaderOnClick(View view) {
        if (this.recyclerViewsMap.containsKey(2)) {
            RecyclerView recyclerView = this.recyclerViewsMap.get(2);
            ImageView expandableImageView;
            if (recyclerView.getVisibility() == View.GONE) {
                recyclerView.setVisibility(View.VISIBLE);
                expandableImageView = view.findViewById(R.id.dessertGroupExpandableImageView);
                expandableImageView.setImageResource(R.drawable.ic_expanded);
                hideAllOtherGroups(2);
            }
        }
    }

    /**
     * Handle click in accompanimentsGroupHeaderConstraintLayout.
     *
     * @param view the accompanimentsGroupHeaderConstraintLayout.
     */
    public void accompanimentsGroupHeaderOnClick(View view) {
        if (this.recyclerViewsMap.containsKey(3)) {
            RecyclerView recyclerView = this.recyclerViewsMap.get(3);
            ImageView expandableImageView;
            if (recyclerView.getVisibility() == View.GONE) {
                recyclerView.setVisibility(View.VISIBLE);
                expandableImageView = view.findViewById(R.id.accompanimentsGroupExpandableImageView);
                expandableImageView.setImageResource(R.drawable.ic_expanded);
                hideAllOtherGroups(3);
            }
        }
    }

    /**
     * Hide all other groups
     */
    private void hideAllOtherGroups(int groupId) {
        RecyclerView recyclerView;
        ImageView expandableImageView;
        for (int id = 0; id < this.maxNumberOfItemsGroups; id++) {
            if (id != groupId) {
                recyclerView = this.recyclerViewsMap.get(id);
                recyclerView.setVisibility(View.GONE);
                if (recyclerView.getVisibility() == View.VISIBLE) {
                    switch (groupId) {
                        case 0:

                            expandableImageView = findViewById(R.id.dinnerGroupExpandableImageView);
                            expandableImageView.setImageResource(R.drawable.ic_expanded);
                            break;
                        case 1:
                            expandableImageView = findViewById(R.id.drinkGroupExpandableImageView);
                            expandableImageView.setImageResource(R.drawable.ic_expanded);
                            break;
                        case 2:
                            expandableImageView = findViewById(R.id.dessertGroupExpandableImageView);
                            expandableImageView.setImageResource(R.drawable.ic_expanded);
                            break;
                        case 3:
                            expandableImageView = findViewById(R.id.accompanimentsGroupExpandableImageView);
                            expandableImageView.setImageResource(R.drawable.ic_expanded);
                            break;
                    }
                }
            }
        }
    }

    /**
     * Get the items grouped by menu group and dispatch to respective group's adapter.
     *
     * @param itemsByGroup
     */
    @Override
    public void dispatchItemsToRespectiveAdapter(@NonNull Map<String, List<Item>> itemsByGroup) {
        int groupId = 0;
        ItemsRVAdapter adapter;
        for (String groupName : itemsByGroup.keySet()) {
            adapter = (ItemsRVAdapter) this.recyclerViewsMap.get(groupId).getAdapter();
            adapter.updateItems(itemsByGroup.get(groupName));
            setupGroupHeaderUI(groupId, formatGroupName(groupName));
            groupId++;
        }
    }

    /**
     * Transform uppercase string in a string with the first letter capitalize.
     *
     * @param groupNameAllHighCase
     * @return
     */
    private String formatGroupName(String groupNameAllHighCase) {
        String groupName = groupNameAllHighCase.toLowerCase();
        return groupName.substring(0, 1).toUpperCase() + groupName.substring(1);
    }

    private void setupGroupHeaderUI(int groupId, String groupName) {
        View view = null;
        TextView textView = null;
        switch (groupId) {
            case 0:
                view = findViewById(R.id.dinnerGroupHeaderConstraintLayout);
                textView = view.findViewById(R.id.dinnerTextView);
                textView.setText(groupName);
                view.setVisibility(View.VISIBLE);
                break;
            case 1:
                view = findViewById(R.id.drinkGroupHeaderConstraintLayout);
                textView = view.findViewById(R.id.drinkTextView);
                textView.setText(groupName);
                view.setVisibility(View.VISIBLE);
                break;
            case 2:
                view = findViewById(R.id.dessertGroupHeaderConstraintLayout);
                textView = view.findViewById(R.id.dessertTextView);
                textView.setText(groupName);
                view.setVisibility(View.VISIBLE);
                break;
            case 3:
                view = findViewById(R.id.accompanimentsGroupHeaderConstraintLayout);
                textView = view.findViewById(R.id.accompanimentsTextView);
                textView.setText(groupName);
                view.setVisibility(View.VISIBLE);
                break;
        }
    }

}
