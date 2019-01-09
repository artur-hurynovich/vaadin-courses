package com.gpsolutions.vaadincourses.form;

import com.gpsolutions.vaadincourses.entity.EmailEntity;
import com.gpsolutions.vaadincourses.custom_field.LocalDateField;
import com.gpsolutions.vaadincourses.custom_field.StringListField;
import com.gpsolutions.vaadincourses.repository.EmailRepository;
import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private final List<AbstractField> fields;

    public EmailForm(final EmailEntity emailEntity, final EmailRepository emailRepository,
                     final Runnable onSave, final Runnable onDiscard) {
        this.emailRepository = emailRepository;
        nameField.setNullRepresentation("");
        nameField.addTextChangeListener(textChangeEvent -> nameField.setValue(textChangeEvent.getText()));
        textField.setNullRepresentation("");
        textField.addTextChangeListener(textChangeEvent -> textField.setValue(textChangeEvent.getText()));
        final VerticalLayout layout = new VerticalLayout();
        final HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(getSaveButton(emailEntity, onSave), getCancelButton(onDiscard));
        emailFieldGroup.setItemDataSource(emailEntity);
        emailFieldGroup.bindMemberFields(this);
        fields = new ArrayList<>();
        fields.addAll(Arrays.asList(nameField, textField, dateField, recipientsField));
        setValidators();
        layout.addComponents(nameField, textField, recipientsField, dateField, buttonsLayout);
        setContent(layout);
    }

    private Button getSaveButton(final EmailEntity oldEmailEntity, final Runnable onSave) {
        final Button saveButton = new Button("Save");
        saveButton.addClickListener(clickEvent -> {
            try {
                setValidatorsVisibility();
                emailFieldGroup.commit();
                final EmailEntity newEmail = emailFieldGroup.getItemDataSource().getBean();
                BeanUtils.copyProperties(newEmail, oldEmailEntity);
                emailRepository.save(oldEmailEntity);
                onSave.run();
            } catch (FieldGroup.CommitException e) {
                Notification.show("Warning!", "Form is not valid!", Notification.Type.WARNING_MESSAGE);
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

    @SuppressWarnings("unchecked")
    private void setValidators() {
        nameField.addValidator(value -> {
            if (nameField.getValue() == null || nameField.getValue().length() < 1 || nameField.getValue().length() > 10) {
                throw new Validator.InvalidValueException("The name length should be between 1 and 10 characters");
            }
        });
        textField.addValidator(value -> {
            if (textField.getValue() == null || textField.getValue().length() < 1 || textField.getValue().length() > 30) {
                throw new Validator.InvalidValueException("The message length should be between 1 and 30 characters");
            }
        });
        dateField.addValidator(value -> {
            if (value == null) {
                throw new Validator.InvalidValueException("The date is empty");
            }
            if (((LocalDate) value).isBefore(LocalDate.now())) {
                throw new Validator.InvalidValueException("The date is before today");
            }
        });
        recipientsField.addValidator(value -> {
            final List<String> recipients = (List<String>) value;
            if (recipients.size() > 2) {
                throw new Validator.InvalidValueException("There should be a maximum of 2 recipients");
            }
            for (String recipient : (recipients)) {
                if (recipient.isEmpty()) {
                    throw new Validator.InvalidValueException("There should be no recipients without a name");
                }
            }
        });
        fields.forEach(field -> field.setValidationVisible(false));
    }

    private void setValidatorsVisibility() {
        fields.forEach(field -> {
            if (field.isValid()) {
                field.setValidationVisible(false);
            } else {
                field.setValidationVisible(true);
            }
        });
    }

}
