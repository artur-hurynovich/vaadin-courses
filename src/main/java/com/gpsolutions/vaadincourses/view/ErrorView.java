package com.gpsolutions.vaadincourses.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;

public class ErrorView extends CustomComponent implements View {

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        final VerticalLayout layout = new VerticalLayout();
        layout.addComponent(new Image("Where do we go, Susanin?",
                new ExternalResource("https://s11.stc.all.kpcdn.net/share/i/12/10402606/inx960x640.jpg")));
        setCompositionRoot(layout);
    }
}
