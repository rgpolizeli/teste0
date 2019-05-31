package com.rgp.goomerlistarango.models;

/**
 * This class represents a restaurant. The id field does not change.
 */

public class Restaurant {
    private final int id;
    private String name;
    private String address;
    private BusinessHours businessHours;

    public Restaurant(int id, String name, String address, BusinessHours businessHours) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.businessHours = businessHours;
    }



}
