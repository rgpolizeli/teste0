package com.rgp.goomerlistarango.models;

import android.net.Uri;

/**
 * This class represents a menu. It is assumed that each restaurant has only one menu and each menu has n items.
 */
public class Menu {
    private final int restaurantId;
    private Items items;

    public Menu(int restaurantId, Items items) {
        this.restaurantId = restaurantId;
        this.items = items;
    }
}
