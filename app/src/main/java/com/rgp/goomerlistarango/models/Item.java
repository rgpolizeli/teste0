package com.rgp.goomerlistarango.models;

import android.net.Uri;

public class Item {
    private final String name;
    private Uri image;
    private double price;
    private String group;
    private Sales sales;

    public Item(String name, Uri image, double price, String group, Sales sales) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.group = group;
        this.sales = sales;
    }
}
