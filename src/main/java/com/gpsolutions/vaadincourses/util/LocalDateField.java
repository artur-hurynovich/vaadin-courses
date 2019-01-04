package com.gpsolutions.vaadincourses.util;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class LocalDateField extends CustomField<LocalDate> {

    private VerticalLayout layout;

    public LocalDateField(String caption) {
        setCaption(caption);
    }

    @Override
    protected Component initContent() {
        return layout;
    }

    @Override
    public Class<? extends LocalDate> getType() {
        return LocalDate.class;
    }

    @Override
    public LocalDate getValue() {
        final Date date = ((DateField) layout.getComponent(0)).getValue();
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    public void setValue(final LocalDate date) throws ReadOnlyException, Converter.ConversionException {
        if (layout == null) {
            layout = new VerticalLayout();
        } else {
            layout.removeAllComponents();
        }

        final DateField dateField = new DateField();
        if (date != null) {
            dateField.setValue(Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        }
        dateField.setDateFormat("dd-MM-yyyy");
        layout.addComponent(dateField);
    }

}
