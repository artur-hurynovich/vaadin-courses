package com.gpsolutions.vaadincourses.broadcast.impl;

import com.gpsolutions.vaadincourses.broadcast.Broadcast;
import com.gpsolutions.vaadincourses.entity.EmailEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Service
public class MessageBroadcast implements Broadcast<EmailEntity> {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final List<Consumer<EmailEntity>> listeners = new ArrayList<>();

    @Override
    public synchronized void register(final Consumer<EmailEntity> listener) {
        listeners.add(listener);
    }

    @Override
    public synchronized void unregister(final Consumer<EmailEntity> listener) {
        listeners.remove(listener);
    }

    @Override
    public synchronized void broadcast(final EmailEntity message) {
        for (final Consumer<EmailEntity> listener : listeners) {
            executorService.execute(() -> listener.accept(message));
        }
    }
}
