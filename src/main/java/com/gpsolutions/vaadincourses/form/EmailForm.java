package com.gpsolutions.vaadincourses.form;

import com.gpsolutions.vaadincourses.entity.Email;
import com.gpsolutions.vaadincourses.util.StringListField;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class EmailForm extends Panel {

    @PropertyId("name")
    private final TextField nameTextField = new TextField("Name");

    @PropertyId("text")
    private final TextArea messageTextArea = new TextArea("Message");

    @PropertyId("recipients")
    private final StringListField recipients = new StringListField("Recipients");

    private final BeanFieldGroup<Email> emailFieldGroup = new BeanFieldGroup<>(Email.class);

    public EmailForm(final Email email, final Runnable onSaveOrDiscard) {
        nameTextField.setNullRepresentation("");
        messageTextArea.setNullRepresentation("");
        final VerticalLayout layout = new VerticalLayout();
        final HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(getSaveButton(onSaveOrDiscard), getCancelButton(onSaveOrDiscard));
        emailFieldGroup.setItemDataSource(email);
        emailFieldGroup.bindMemberFields(this);
        layout.addComponents(nameTextField, messageTextArea, recipients, buttonsLayout);
        setContent(layout);
    }

    private Button getSaveButton(final Runnable onSaveOrDiscard) {
        final Button saveButton = new Button("Save");
        saveButton.addClickListener(clickEvent -> {
            try {
                emailFieldGroup.commit();
                onSaveOrDiscard.run();
            } catch (FieldGroup.CommitException e) {
                Notification.show("Error!", "Saving failed!", Notification.Type.ERROR_MESSAGE);
            }
        });

        return saveButton;
    }

    private Button getCancelButton(final Runnable onSaveOrDiscard) {
        final Button cancelButton = new Button("Cancel");
        cancelButton.addClickListener(clickEvent -> {
            emailFieldGroup.discard();
            onSaveOrDiscard.run();
        });
        return cancelButton;
    }

}
