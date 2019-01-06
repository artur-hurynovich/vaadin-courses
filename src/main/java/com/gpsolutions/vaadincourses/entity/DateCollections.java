package com.gpsolutions.vaadincourses.entity;

import java.util.ArrayList;
import java.util.List;

public class DateCollections {

    public List<Integer> getDaysCollection() {
        final List<Integer> daysCollection = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            daysCollection.add(i);
        }
        return daysCollection;
    }

    public List<Integer> getMonthsCollection() {
        final List<Integer> monthsCollection = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            monthsCollection.add(i);
        }
        return monthsCollection;
    }

}
