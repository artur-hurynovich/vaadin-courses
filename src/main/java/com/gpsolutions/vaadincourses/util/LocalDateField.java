package com.gpsolutions.vaadincourses.util;

import com.gpsolutions.vaadincourses.entity.DateCollections;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.time.LocalDate;

public class LocalDateField extends CustomField<LocalDate> {

    private VerticalLayout parentLayout;

    private ComboBox dayField;

    private ComboBox monthField;

    private TextField yearField;

    private final DateCollections dateCollections = new DateCollections();

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
            parentLayout.addComponents(getDayField(), getMonthField(), getYearField());
        }
        return parentLayout;
    }

    private Field getDayField() {
        if (dayField == null) {
            dayField = new ComboBox("Day", dateCollections.getDaysCollection());
            dayField.setNullSelectionAllowed(false);
            dayField.setValue(getValue().getDayOfMonth());
            dayField.addValueChangeListener(valueChangeEvent -> {
                final LocalDate date = getValue();
                final LocalDate newDate = date.withDayOfMonth((Integer) dayField.getValue());
                setValue(newDate);
            });
        }
        return dayField;
    }

    private Field getMonthField() {
        if (monthField == null) {
            monthField = new ComboBox("Month", dateCollections.getMonthsCollection());
            monthField.setNullSelectionAllowed(false);
            monthField.setValue(getValue().getMonthValue());
            monthField.addValueChangeListener(valueChangeEvent -> {
                final LocalDate date = getValue();
                final LocalDate newDate = date.withMonth((Integer) monthField.getValue());
                setValue(newDate);
            });
        }
        return monthField;
    }

    private Field getYearField() {
        if (yearField == null) {
            yearField = new TextField("Year");
            yearField.setNullSettingAllowed(false);
            yearField.setValue(String.valueOf(getValue().getYear()));
            yearField.addValueChangeListener(valueChangeEvent -> {
                final LocalDate date = getValue();
                final LocalDate newDate = date.withYear(Integer.valueOf(yearField.getValue()));
                setValue(newDate);
            });
        }
        return yearField;
    }

}
