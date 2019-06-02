package com.rgp.goomerlistarango.models;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent each option of a menu.
 */
public class Item {
    private int restaurantId;
    private String name;
    private String image;
    private double price;
    private String group;
    private List<Sale> sales;

    public Item() {
        this.sales = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getImage() {
        return image;
    }

    public void setImage(@Nullable String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

    public void addSale(Sale sale) {
        this.sales.add(sale);
    }
}
