package com.gpsolutions.vaadincourses.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.CustomComponent;

@SpringView(name = NotAvailableView.NAME)
public class NotAvailableView extends CustomComponent implements View {

    public final static String NAME = "restricted";

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
