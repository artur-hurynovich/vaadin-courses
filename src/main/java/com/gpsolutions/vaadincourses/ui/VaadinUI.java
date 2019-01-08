package com.gpsolutions.vaadincourses.ui;

import com.gpsolutions.vaadincourses.view.AccessDeniedView;
import com.gpsolutions.vaadincourses.view.ErrorView;
import com.gpsolutions.vaadincourses.view.MainView;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.access.ViewAccessControl;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;

@SpringUI(path = "vaadin")
@Theme("mytheme")
@Push
public class VaadinUI extends UI implements ViewAccessControl {

    private final SpringViewProvider viewProvider;

    public VaadinUI(final SpringViewProvider viewProvider) {
        this.viewProvider = viewProvider;
    }

    @Override
    protected void init(final VaadinRequest vaadinRequest) {
        final Navigator navigator = new Navigator(this, this);
        navigator.addProvider(viewProvider);
        navigator.navigateTo(MainView.NAME);
        navigator.setErrorView(new ErrorView());
        viewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
    }

    @Override
    public boolean isAccessGranted(final UI ui, final String beanName) {
        return !beanName.equals("notAvailableView");
    }
}
