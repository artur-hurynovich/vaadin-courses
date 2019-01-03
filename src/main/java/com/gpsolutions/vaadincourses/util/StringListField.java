package com.gpsolutions.vaadincourses.util;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringListField extends CustomField<List<String>> {
    private final VerticalLayout layout;

    public StringListField() {
        layout = new VerticalLayout();
    }

    @Override
    protected Component initContent() {
        return layout;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends List<String>> getType() {
        return (Class<List<String>>) Arrays.stream(ArrayList.class.getInterfaces()).
                filter(a -> a.isAssignableFrom(List.class)).findAny().orElseThrow(RuntimeException::new);
    }

    @Override
    public List<String> getValue() {
        return super.getValue();
    }

    @Override
    public void setValue(final List<String> recipients) throws ReadOnlyException, Converter.ConversionException {
        layout.removeAllComponents();
        recipients.forEach(recipient -> {
            final HorizontalLayout horizontalLayout = new HorizontalLayout();
            final TextField textField = new TextField(recipient);
            textField.addTextChangeListener(textChangeEvent -> fireValueChange(true));
            final Button removeButton = new Button("Remove");
            removeButton.addClickListener(clickEvent -> fireValueChange(true));
            horizontalLayout.addComponents(textField, removeButton);
            layout.addComponent(horizontalLayout);
        });
        final Button addRecipientButton = new Button("Add Recipient");
        layout.addComponent(addRecipientButton);
    }
}
