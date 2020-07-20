package ru.vsueducation.db.utils;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.vsueducation.db.configs.HibernateConfig;

public class SessionProvider {
    public interface Callback<T> {
        T call(Session session);
    }

    public interface StatelessCallback<T> {
        T call(Session session);
    }

    public static <T> T runSession(StatelessCallback<T> callback) {
        final Session sqlSession = HibernateConfig.getSessionFactory().openSession();
        final T result = callback.call(sqlSession);
        sqlSession.close();
        return result;
    }

    public static <T> T runTransaction(Callback<T> callback) {
        final Session sqlSession = HibernateConfig.getSessionFactory().openSession();
        final Transaction tx = sqlSession.beginTransaction();
        final T result = callback.call(sqlSession);
        tx.commit();
        sqlSession.close();
        return result;
    }

    public static <T> T getEntryById(final String entryName, final int id) {
        return runSession(session -> {
            final Query<T> query = session.createQuery("from " + entryName + " where id = :i");
            query.setParameter("i", id);
            return query.getSingleResult();
        });
    }
}
