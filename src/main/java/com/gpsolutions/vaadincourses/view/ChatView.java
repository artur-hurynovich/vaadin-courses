package com.gpsolutions.vaadincourses.view;

import com.gpsolutions.vaadincourses.broadcast.impl.MessageBroadcast;
import com.gpsolutions.vaadincourses.entity.EmailEntity;
import com.gpsolutions.vaadincourses.repository.EmailRepository;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.function.Consumer;

@SpringView(name = ChatView.NAME)
public class ChatView extends CustomComponent implements View, Consumer<EmailEntity> {

    public final static String NAME = "chat";

    private final VerticalLayout parentLayout;

    private final VerticalLayout messageLayout;

    private final HorizontalLayout registerLayout;

    private final MessageBroadcast messageBroadcast;

    private final EmailRepository emailRepository;

    private String userName;

    @Autowired
    public ChatView(final MessageBroadcast messageBroadcast, final EmailRepository emailRepository) {
        parentLayout = new VerticalLayout();
        messageLayout = new VerticalLayout();
        registerLayout = new HorizontalLayout();
        initParentLayout();
        initMessageLayout();
        initRegisterLayout();
        this.messageBroadcast = messageBroadcast;
        this.emailRepository = emailRepository;
    }

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        setCompositionRoot(parentLayout);
        messageBroadcast.register(this);
    }

    @Override
    public void detach() {
        messageBroadcast.unregister(this);
        super.detach();
    }

    @Override
    public void accept(final EmailEntity emailEntity) {
        getUI().access(() ->  {
            final Label emailLabel = new Label();
            emailLabel.setValue(emailEntity.getName() + ": " + emailEntity.getMessage());
            messageLayout.addComponent(emailLabel);
        });
    }

    private void initParentLayout() {
        parentLayout.addComponents(messageLayout, registerLayout);
    }

    private void initMessageLayout() {
        final HorizontalLayout sendLayout = new HorizontalLayout();
        final TextField textField = new TextField();
        final Button sendButton = new Button("Send");
        textField.addTextChangeListener(textChangeEvent -> {
            final String text = textChangeEvent.getText();
            if (text.isEmpty()) {
                sendButton.setEnabled(false);
            } else {
                sendButton.setEnabled(true);
            }
        });
        sendButton.setEnabled(false);
        sendButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        sendButton.addClickListener(clickEvent -> {
            final EmailEntity emailEntity = new EmailEntity();
            emailEntity.setName(userName);
            emailEntity.setMessage(textField.getValue());
            emailEntity.setDate(LocalDate.now(ZoneId.systemDefault()));
            messageBroadcast.broadcast(emailEntity);
            emailRepository.save(emailEntity);
            textField.clear();
            sendButton.setEnabled(false);
        });
        messageLayout.setVisible(false);
        sendLayout.addComponents(textField, sendButton);
        messageLayout.addComponents(sendLayout);
    }

    private void initRegisterLayout() {
        final Label label = new Label("Please, enter your name: ");
        final TextField textField = new TextField();
        final Button joinChatButton = new Button("Join Chat");
        textField.addTextChangeListener(textChangeEvent -> {
            final String text = textChangeEvent.getText();
            if (text.isEmpty()) {
                joinChatButton.setEnabled(false);
            } else {
                joinChatButton.setEnabled(true);
            }
        });
        joinChatButton.setEnabled(false);
        joinChatButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        joinChatButton.addClickListener(clickEvent -> {
            userName = textField.getValue();
            registerLayout.setVisible(false);
            messageLayout.setVisible(true);
            messageLayout.setCaption(textField.getValue());
        });
        registerLayout.addComponents(label, textField, joinChatButton);
        registerLayout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
    }

}
