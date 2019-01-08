package com.gpsolutions.vaadincourses.broadcast;

import java.util.function.Consumer;

public interface Broadcast<T> {

    void register(final Consumer<T> listener);

    void unregister(final Consumer<T> listener);

    void broadcast(final T message);

}
