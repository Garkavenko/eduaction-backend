package ru.vsueducation.server.context;

import ru.vsueducation.db.models.User;

public class Context {
    final User user;

    public Context(final User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
