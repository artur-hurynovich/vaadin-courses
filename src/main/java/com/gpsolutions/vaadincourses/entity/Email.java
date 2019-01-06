package com.gpsolutions.vaadincourses.entity;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Email {

    private String name;

    private String text;

    private List<String> recipients;

    public Email() {
    }

    public Email(final String name, final String text, final List<String> recipients) {
        this.name = name;
        this.text = text;
        this.recipients = recipients;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(final List<String> recipients) {
        this.recipients = recipients;
    }

}
