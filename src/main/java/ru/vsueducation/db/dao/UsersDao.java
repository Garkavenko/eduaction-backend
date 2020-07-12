package ru.vsueducation.db.dao;

import org.hibernate.query.Query;
import ru.vsueducation.db.models.User;
import ru.vsueducation.db.utils.SessionProvider;

public class UsersDao {
    public User getUserByLogin(final String login) throws Exception {
        return SessionProvider.runSession(session -> {
            final Query<User> query = session.createQuery("from User WHERE login = :l");
            query.setParameter("l", login);
            return query.list().get(0);
        });
    }

    public User getUserById(final int id) throws Exception {
        return SessionProvider.runSession(session -> {
            final Query<User> query = session.createQuery("from User WHERE id = :i");
            query.setParameter("i", id);
            return query.list().get(0);
        });
    }
}
