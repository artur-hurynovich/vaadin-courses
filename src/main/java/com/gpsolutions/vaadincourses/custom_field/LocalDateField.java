package com.gpsolutions.vaadincourses.custom_field;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.DateField;
import com.vaadin.ui.VerticalLayout;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class LocalDateField extends CustomField<LocalDate> {

    private VerticalLayout parentLayout;

    private DateField dateField;

    public LocalDateField(String caption) {
        setCaption(caption);
    }

    @Override
    protected Component initContent() {
        return getParentLayout();
    }

    @Override
    public LocalDate getValue() {
        return super.getValue();
    }

    @Override
    public void setValue(final LocalDate newFieldValue) throws ReadOnlyException, Converter.ConversionException {
        super.setValue(newFieldValue);
    }

    @Override
    public Class<? extends LocalDate> getType() {
        return LocalDate.class;
    }

    private VerticalLayout getParentLayout() {
        if (parentLayout == null) {
            parentLayout = new VerticalLayout();
            parentLayout.addComponents(getDateField());
        }
        return parentLayout;
    }

    private DateField getDateField() {
        dateField = new DateField();
        dateField.setValue(convertToDate(getValue()));
        dateField.addValueChangeListener(valueChangeEvent -> setValue(convertToLocalDate(dateField.getValue())));
        return dateField;
    }

    private Date convertToDate(final LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
