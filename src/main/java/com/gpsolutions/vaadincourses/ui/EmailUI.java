package com.gpsolutions.vaadincourses.ui;

import com.gpsolutions.vaadincourses.util.Email;
import com.gpsolutions.vaadincourses.util.EmailGenerator;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringUI(path = "email")
@Theme("valo")
public class EmailUI extends UI {

    private final EmailGenerator generator;

    private final Grid grid;

    private final Button removeButton;

    @Autowired
    public EmailUI(final EmailGenerator generator) {
        this.generator = generator;
        grid = new Grid();
        initGrid(grid);
        removeButton = new Button("Remove");
        initRemoveButton(removeButton);
    }

    @Override
    protected void init(final VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        updateList();
        layout.addComponents(grid, removeButton);
        setContent(layout);
    }

    private void initGrid(final Grid grid) {
        grid.setColumnOrder("name", "text", "recipients");
        grid.setSizeFull();
    }

    private void initRemoveButton(final Button button) {
        button.setEnabled(false);
    }

    private void updateList() {
        final List<Email> emails = generator.getEmailList();
        final BeanItemContainer<Email> container = new BeanItemContainer<>(Email.class, emails);
        grid.setContainerDataSource(container);
    }
}
