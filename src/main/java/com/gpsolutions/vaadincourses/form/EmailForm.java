package com.gpsolutions.vaadincourses.form;

import com.gpsolutions.vaadincourses.entity.EmailEntity;
import com.gpsolutions.vaadincourses.custom_field.LocalDateField;
import com.gpsolutions.vaadincourses.custom_field.StringListField;
import com.gpsolutions.vaadincourses.repository.EmailRepository;
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
import org.springframework.beans.BeanUtils;

public class EmailForm extends Panel {

    private final EmailRepository emailRepository;

    @PropertyId("name")
    private final TextField nameField = new TextField("Name");

    @PropertyId("message")
    private final TextArea textField = new TextArea("Message");

    @PropertyId("recipients")
    private final StringListField recipientsField = new StringListField("Recipients");

    @PropertyId("date")
    private final LocalDateField dateField = new LocalDateField("Date");

    private final BeanFieldGroup<EmailEntity> emailFieldGroup = new BeanFieldGroup<>(EmailEntity.class);

    public EmailForm(final EmailEntity emailEntity, final EmailRepository emailRepository,
                     final Runnable onSave, final Runnable onDiscard) {
        this.emailRepository = emailRepository;
        nameField.setNullRepresentation("");
        textField.setNullRepresentation("");
        final VerticalLayout layout = new VerticalLayout();
        final HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(getSaveButton(emailEntity, onSave), getCancelButton(onDiscard));
        emailFieldGroup.setItemDataSource(emailEntity);
        emailFieldGroup.bindMemberFields(this);
        layout.addComponents(nameField, textField, recipientsField, dateField, buttonsLayout);
        setContent(layout);
    }

    private Button getSaveButton(final EmailEntity oldEmailEntity, final Runnable onSave) {
        final Button saveButton = new Button("Save");
        saveButton.addClickListener(clickEvent -> {
            try {
                emailFieldGroup.commit();
                final EmailEntity newEmail = emailFieldGroup.getItemDataSource().getBean();
                BeanUtils.copyProperties(newEmail, oldEmailEntity);
                emailRepository.save(oldEmailEntity);
                onSave.run();
            } catch (FieldGroup.CommitException e) {
                Notification.show("Error!", "Saving failed!", Notification.Type.ERROR_MESSAGE);
            }
        });

        return saveButton;
    }

    private Button getCancelButton(final Runnable onDiscard) {
        final Button cancelButton = new Button("Cancel");
        cancelButton.addClickListener(clickEvent -> {
            emailFieldGroup.discard();
            onDiscard.run();
        });
        return cancelButton;
    }

}
