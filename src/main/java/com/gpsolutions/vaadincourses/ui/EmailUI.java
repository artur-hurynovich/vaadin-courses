package com.gpsolutions.vaadincourses.ui;

import com.gpsolutions.vaadincourses.util.Email;
import com.gpsolutions.vaadincourses.util.EmailGenerator;
import com.gpsolutions.vaadincourses.util.StringListField;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@SpringUI(path = "email")
@Theme("valo")
public class EmailUI extends UI {

    private final EmailGenerator generator;

    private final Grid grid;

    private final Button removeButton;

    private List<Email> emails;

    @Autowired
    public EmailUI(final EmailGenerator generator, final StringListField stringListField) {
        this.generator = generator;
        grid = new Grid();
        initGrid(grid);
        removeButton = new Button("Remove");
        initRemoveButton(removeButton);
    }

    @Override
    protected void init(final VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.addComponents(grid, removeButton);
        setContent(layout);
    }

    private void initGrid(final Grid grid) {
        emails = generator.getEmailList();
        final BeanItemContainer<Email> container = new BeanItemContainer<>(Email.class, emails);
        grid.setContainerDataSource(container);
        grid.setColumnOrder("name", "text", "recipients");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addSelectionListener(selectionEvent -> {
            final Set<Object> selectedEmails = selectionEvent.getSelected();
            if (selectedEmails.size() == 0) {
                removeButton.setEnabled(false);
            } else {
                removeButton.setEnabled(true);
            }
        });
    }

    private void initRemoveButton(final Button button) {
        button.setEnabled(false);
        button.addClickListener(clickEvent -> {
            final Collection<Object> selectedRows = grid.getSelectionModel().getSelectedRows();
            if (selectedRows.size() > 0) {
                selectedRows.forEach(row -> {
                    final Email email = (Email) row;
                    emails.remove(email);
                    grid.getContainerDataSource().removeItem(row);
                });
            }
            button.setEnabled(false);
        });
    }
}
