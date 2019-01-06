package com.gpsolutions.vaadincourses.util;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringListField extends CustomField<List<String>> {

    private VerticalLayout parentLayout;

    private List<HorizontalLayout> childLayouts;

    private Button addButton;

    public StringListField(String caption) {
        setCaption(caption);
    }

    @Override
    protected Component initContent() {
        final VerticalLayout layout = getParentLayout();
        getChildLayouts().forEach(layout::addComponent);
        layout.addComponent(getAddButton());
        return layout;
    }

    @Override
    public List<String> getValue() {
        return super.getValue();
    }

    @Override
    public void setValue(final List<String> newFieldValue) throws ReadOnlyException, Converter.ConversionException {
        super.setValue(newFieldValue);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends List<String>> getType() {
        return (Class<List<String>>) Arrays.stream(ArrayList.class.getInterfaces())
                .filter(a -> a.isAssignableFrom(List.class)).findAny().orElseThrow(RuntimeException::new);
    }

    private VerticalLayout getParentLayout() {
        if (parentLayout == null) {
            parentLayout = new VerticalLayout();
        }
        return parentLayout;
    }

    private List<HorizontalLayout> getChildLayouts() {
        if (childLayouts == null) {
            childLayouts = new ArrayList<>();
            getValue().forEach(recipient -> {
                final HorizontalLayout childLayout = new HorizontalLayout();
                final TextField textField = new TextField();
                textField.setValue(recipient);
                textField.addValueChangeListener(valueChangeEvent -> {
                    final String newTextFieldValue = textField.getValue();
                    final int index = getChildLayouts().indexOf(childLayout);
                    final List<String> recipients = new ArrayList<>(getValue());
                    recipients.set(index, newTextFieldValue);
                    setValue(recipients);
                });
                final Button removeButton = new Button("Remove");
                removeButton.addClickListener(clickEvent -> {
                    final List<String> recipients = new ArrayList<>(getValue());
                    recipients.remove(recipient);
                    setValue(recipients);
                    getChildLayouts().remove(childLayout);
                    parentLayout.removeComponent(childLayout);
                });
                childLayout.addComponents(textField, removeButton);
                childLayouts.add(childLayout);
            });
        }
        return childLayouts;
    }

    private Button getAddButton() {
        if (addButton == null) {
            addButton = new Button("Add Recipient");
            addButton.addClickListener(clickEvent -> {
                final List<String> recipients = new ArrayList<>(getValue());
                final HorizontalLayout childLayout = new HorizontalLayout();
                final TextField textField = new TextField();
                textField.addValueChangeListener(valueChangeEvent -> {
                    final String newTextFieldValue = textField.getValue();
                    final int index = getChildLayouts().indexOf(childLayout);
                    final List<String> updatedRecipients = new ArrayList<>(getValue());
                    updatedRecipients.set(index, newTextFieldValue);
                    setValue(updatedRecipients);
                });
                final Button removeButton = new Button("Remove");
                childLayout.addComponents(textField, removeButton);
                childLayouts.add(childLayout);
                recipients.add("");
                setValue(recipients);
                parentLayout.removeComponent(addButton);
                parentLayout.addComponent(childLayout);
                parentLayout.addComponent(addButton);
            });
        }
        return addButton;
    }

}
