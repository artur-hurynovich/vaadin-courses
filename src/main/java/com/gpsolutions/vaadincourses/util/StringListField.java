package com.gpsolutions.vaadincourses.util;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.Alignment;
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

    private VerticalLayout layout;

    public StringListField(String caption) {
        setCaption(caption);
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
        final List<String> recipients = new ArrayList<>();
        layout.iterator().forEachRemaining(component -> {
            if (component instanceof HorizontalLayout) {
                final TextField textField = (TextField) ((HorizontalLayout) component).getComponent(0);
                recipients.add(textField.getValue());
            }
        });
        return recipients;
    }

    @Override
    public void setValue(final List<String> recipients) throws ReadOnlyException, Converter.ConversionException {
        if (layout == null) {
            layout = new VerticalLayout();
        } else {
            layout.removeAllComponents();
        }

        recipients.forEach(recipient -> {
            final HorizontalLayout horizontalLayout = new HorizontalLayout();
            final TextField textField = new TextField();
            textField.setValue(recipient);
            final Button removeButton = new Button("Remove");
            textField.addTextChangeListener(textChangeEvent -> fireValueChange(false));
            removeButton.addClickListener(clickEvent -> {
                horizontalLayout.removeAllComponents();
                fireValueChange(false);
            });
            horizontalLayout.addComponents(textField, removeButton);
            horizontalLayout.setComponentAlignment(textField, Alignment.BOTTOM_CENTER);
            horizontalLayout.setComponentAlignment(removeButton, Alignment.BOTTOM_CENTER);
            layout.addComponent(horizontalLayout);
        });

        final Button addRecipientButton = new Button("Add Recipient");
        layout.addComponent(addRecipientButton);
    }

}
