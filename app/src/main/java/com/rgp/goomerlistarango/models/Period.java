package com.rgp.goomerlistarango.models;

import android.text.format.DateFormat;

/**
 * This class is used to record a period in which the restaurant is open. With it, it is possible that the restaurant has more than one opening and closing time.
 */

public class Period {
    private DateFormat startHour;
    private DateFormat endHour;

    public Period(DateFormat startHour, DateFormat endHour) {
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public DateFormat getStartHour() {
        return startHour;
    }

    public void setStartHour(DateFormat startHour) {
        this.startHour = startHour;
    }

    public DateFormat getEndHour() {
        return endHour;
    }

    public void setEndHour(DateFormat endHour) {
        this.endHour = endHour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Period period = (Period) o;

        if (getStartHour() != null ? !getStartHour().equals(period.getStartHour()) : period.getStartHour() != null)
            return false;
        return getEndHour() != null ? getEndHour().equals(period.getEndHour()) : period.getEndHour() == null;
    }

    @Override
    public int hashCode() {
        int result = getStartHour() != null ? getStartHour().hashCode() : 0;
        result = 31 * result + (getEndHour() != null ? getEndHour().hashCode() : 0);
        return result;
    }
}
