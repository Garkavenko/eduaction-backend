package ru.vsueducation.db.utils;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class QueryUtils {
    public static void addAndParameter(final StringBuilder stringBuilder, final boolean printAND, final String filedName, final String alias) {
        if (printAND) stringBuilder.append(" AND ");
        stringBuilder.append(filedName).append(" = ").append(":").append(alias);
    }

    public static class QuerySelectBuilder {

        private final Session session;
        private final String entryName;
        private final StringBuilder stringBuilder = new StringBuilder();
        private final Map<String, Object> values = new HashMap<>();

        public QuerySelectBuilder(final Session session, final String entryName) {
            this.session = session;
            this.entryName = entryName;
            stringBuilder.append("from ").append(entryName);
        }

        public <T> QuerySelectBuilder addAndParameter(final String name, final String alias, final T value) {
            if (value != null) {
                if (stringBuilder.indexOf(" where ") == -1) {
                    stringBuilder.append(" where ").append(name).append(" = ").append(":").append(alias);
                } else {
                    stringBuilder.append(" AND ").append(name).append(" = ").append(":").append(alias);
                }
                values.put(alias, value);
            }
            return this;
        }

        public Query build() {
            final Query query = session.createQuery(stringBuilder.toString());
            values.forEach(query::setParameter);
            return query;
        }

    }
}
