package com.rgp.goomerlistarango.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.rgp.goomerlistarango.R;
import com.rgp.goomerlistarango.models.Hour;
import com.rgp.goomerlistarango.models.Restaurant;

public class ItemsActivity extends AppCompatActivity {

    //Gson to deserialize restaurant object from intent
    private final Gson gson = new Gson();
    private Restaurant restaurant;

    private RecyclerView dinnerRecyclerView;
    private RecyclerView drinkRecyclerView;
    private RecyclerView dessertRecyclerView;
    private RecyclerView accompanimentsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //remove title from SupportActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        getRestaurantFromIntent();
        setRestaurantData();

        dinnerRecyclerView = findViewById(R.id.dinnerRecyclerView);
        drinkRecyclerView = findViewById(R.id.drinkRecyclerView);
        dessertRecyclerView = findViewById(R.id.dessertRecyclerView);
        accompanimentsRecyclerView = findViewById(R.id.accompanimentsRecyclerView);
    }

    /**
     * Get the restaurant object from intent via gson or finish activity in this attempt.
     */
    private void getRestaurantFromIntent() {
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

    public void groupHeaderOnClick(View view) {
        ImageView expandableImageView;

        switch (view.getId()) {
            case R.id.dinnerGroupHeaderConstraintLayout:
                if (dinnerRecyclerView.getVisibility() == View.GONE) {
                    dinnerRecyclerView.setVisibility(View.VISIBLE);
                    expandableImageView = view.findViewById(R.id.dinnerGroupExpandableImageView);
                    expandableImageView.setImageResource(R.drawable.ic_expanded);
                } else {
                    dinnerRecyclerView.setVisibility(View.GONE);
                    expandableImageView = view.findViewById(R.id.dinnerGroupExpandableImageView);
                    expandableImageView.setImageResource(R.drawable.ic_expandable);
                }
                break;
            case R.id.drinkGroupHeaderConstraintLayout:
                break;
            case R.id.dessertGroupHeaderConstraintLayout:
                break;
            case R.id.accompanimentsGroupHeaderConstraintLayout:
                break;
        }
    }

}
