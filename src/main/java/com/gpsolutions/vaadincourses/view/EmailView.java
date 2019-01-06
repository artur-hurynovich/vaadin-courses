package com.gpsolutions.vaadincourses.view;


import com.gpsolutions.vaadincourses.entity.Email;
import com.gpsolutions.vaadincourses.entity.EmailGenerator;
import com.gpsolutions.vaadincourses.form.EmailForm;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@SpringView(name = EmailView.NAME)
public class EmailView extends CustomComponent implements View {

    public final static String NAME = "email";

    private final EmailGenerator generator;

    private final Grid grid;

    private final HorizontalLayout buttonsLayout;

    private final Button addButton;

    private final Button editButton;

    private final Button removeButton;

    private final Button closeButton;

    private List<Email> emails;

    @Autowired
    public EmailView(final EmailGenerator generator) {
        this.generator = generator;
        grid = new Grid();
        initGrid();
        addButton = new Button("Add");
        editButton = new Button("Edit");
        removeButton = new Button("Remove");
        closeButton = new Button("Close");
        buttonsLayout = new HorizontalLayout();
        initButtonsLayout();
    }

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        final VerticalLayout layout = new VerticalLayout();
        layout.addComponents(grid, buttonsLayout);
        setCompositionRoot(layout);
    }

    private void initGrid() {
        emails = generator.getEmailList();
        final BeanItemContainer<Email> container = new BeanItemContainer<>(Email.class, emails);
        grid.setContainerDataSource(container);
        grid.setColumnOrder("name", "text", "recipients", "date");
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
        initCloseButton();
        buttonsLayout.addComponents(addButton, editButton, removeButton, closeButton);
    }

    private void initAddButton() {
        addButton.addClickListener(clickEvent -> {
            final Email email = new Email();
            email.setRecipients(new ArrayList<>());
            email.setDate(LocalDate.now());
            grid.getContainerDataSource().addItem(email);
            grid.refreshAllRows();
            final Window emailWindow = new Window();
            final EmailForm emailForm = new EmailForm(email, emailWindow::close);
            emailWindow.setCaption("New email");
            emailWindow.setContent(emailForm);
            emailWindow.addCloseListener(closeEvent -> grid.refreshAllRows());
            UI.getCurrent().addWindow(emailWindow);
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
            UI.getCurrent().addWindow(emailWindow);
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

    private void initCloseButton() {
        closeButton.addClickListener(clickEvent -> getUI().getNavigator().navigateTo(MainView.NAME));
    }

}
