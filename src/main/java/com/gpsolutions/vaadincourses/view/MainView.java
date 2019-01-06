package com.gpsolutions.vaadincourses.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;

@SpringView(name = MainView.NAME)
public class MainView extends CustomComponent implements View {

    public final static String NAME = "main";

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        final MenuBar menuBar = new MenuBar();
        menuBar.addItem("Email",
                (MenuBar.Command) selectedItem ->
                        getUI().getNavigator().navigateTo(EmailView.NAME));
        menuBar.addItem("Error",
                (MenuBar.Command) selectedItem ->
                        getUI().getNavigator().navigateTo(EmailView.NAME + "error"));
        setCompositionRoot(menuBar);
    }
}
