package com.gpsolutions.vaadincourses.ui;

import com.gpsolutions.vaadincourses.form.EmailForm;
import com.gpsolutions.vaadincourses.entity.Email;
import com.gpsolutions.vaadincourses.entity.EmailGenerator;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@SpringUI(path = "email")
@Theme("valo")
public class EmailUI extends UI {

    private final EmailGenerator generator;

    private final Grid grid;

    private final HorizontalLayout buttonsLayout;

    private final Button addButton;

    private final Button editButton;

    private final Button removeButton;

    private List<Email> emails;

    @Autowired
    public EmailUI(final EmailGenerator generator) {
        this.generator = generator;
        grid = new Grid();
        initGrid();
        addButton = new Button("Add");
        editButton = new Button("Edit");
        removeButton = new Button("Remove");
        buttonsLayout = new HorizontalLayout();
        initButtonsLayout();
    }

    @Override
    protected void init(final VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.addComponents(grid, buttonsLayout);
        setContent(layout);
    }

    private void initGrid() {
        emails = generator.getEmailList();
        final BeanItemContainer<Email> container = new BeanItemContainer<>(Email.class, emails);
        grid.setContainerDataSource(container);
        grid.setColumnOrder("name", "text", "recipients");
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.setSizeFull();
        grid.setHeightMode(HeightMode.ROW);
        grid.addSelectionListener(selectionEvent -> {
            final Set<Object> selectedEmails = selectionEvent.getSelected();
            if (selectedEmails.size() == 0) {
                editButton.setEnabled(false);
                removeButton.setEnabled(false);
            } else if (selectedEmails.size() == 1) {
                editButton.setEnabled(true);
                removeButton.setEnabled(true);
            } else {
                editButton.setEnabled(false);
                removeButton.setEnabled(true);
            }
        });
    }

    private void initButtonsLayout() {
        initAddButton();
        initEditButton();
        initRemoveButton();
        buttonsLayout.addComponents(addButton, editButton, removeButton);
    }

    private void initAddButton() {
        addButton.addClickListener(clickEvent -> {
            final Email email = new Email();
            email.setRecipients(new ArrayList<>());
            grid.getContainerDataSource().addItem(email);
            grid.refreshAllRows();
            final Window emailWindow = new Window();
            final EmailForm emailForm = new EmailForm(email, emailWindow::close);
            emailWindow.setCaption("New email");
            emailWindow.setContent(emailForm);
            emailWindow.addCloseListener(closeEvent -> grid.refreshAllRows());
            addWindow(emailWindow);
        });
    }

    private void initEditButton() {
        editButton.setEnabled(false);
        editButton.addClickListener(clickEvent -> {
            final Email email = (Email) grid.getSelectedRows().iterator().next();
            final Window emailWindow = new Window();
            final EmailForm emailForm = new EmailForm(email, emailWindow::close);
            emailWindow.setCaption("Edit email");
            emailWindow.setContent(emailForm);
            emailWindow.addCloseListener(closeEvent -> grid.refreshAllRows());
            addWindow(emailWindow);
        });
    }

    private void initRemoveButton() {
        removeButton.setEnabled(false);
        removeButton.addClickListener(clickEvent -> {
            final Collection<Object> selectedRows = grid.getSelectionModel().getSelectedRows();
            if (selectedRows.size() > 0) {
                selectedRows.forEach(grid.getContainerDataSource()::removeItem);
            }
            grid.deselectAll();
            editButton.setEnabled(false);
            removeButton.setEnabled(false);
        });
    }
}
