package com.gpsolutions.vaadincourses.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = AccessDeniedView.NAME)
public class AccessDeniedView extends CustomComponent implements View {

    public final static String NAME = "access denied";

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        final VerticalLayout layout = new VerticalLayout();
        final Image image = new Image("Access denied!",
                new ExternalResource("https://img-fotki.yandex.ru/get/106972/97833783.15a2/0_1cea96_9c7c332d_orig.jpg"));
        image.setHeight("750");
        image.setWidthUndefined();
        layout.addComponent(image);
        setCompositionRoot(layout);
    }

}
