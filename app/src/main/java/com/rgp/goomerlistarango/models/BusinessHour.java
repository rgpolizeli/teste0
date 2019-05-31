package com.rgp.goomerlistarango.models;

import android.net.Uri;

/**
 * This class represents the opening hours of the restaurant. The days of the week are represented from 1 to 7.
 */

public class BusinessHour {
    private int weekday;
    private Periods periods;
    private Uri image;

    public BusinessHour(int weekday, Periods periods, Uri image) {
        this.weekday = weekday;
        this.periods = periods;
        this.image = image;
    }
}
