package com.rgp.goomerlistarango.models;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a restaurant. The id field does not change.
 */

public class Restaurant {
    private int id;
    private String name;
    private String address;
    private List<Hour> hours;
    private String image;

    public Restaurant() {
        this.hours = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Hour> getHours() {
        return hours;
    }

    public void setHours(List<Hour> hours) {
        this.hours = hours;
    }

    public void addHour(Hour hour) {
        this.hours.add(hour);
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
