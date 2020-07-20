package ru.vsueducation.db.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.vsueducation.db.configs.HibernateConfig;
import ru.vsueducation.db.models.Task;
import ru.vsueducation.db.models.TaskType;
import ru.vsueducation.db.utils.SessionProvider;

import java.util.List;

public class TasksDao {

    public List<TaskType> getTaskTypes() {
        final Session sqlSession = HibernateConfig.getSessionFactory().openSession();
        final List<TaskType> attestation = sqlSession.createQuery(" from TaskType").list();
        sqlSession.close();
        return attestation;
    }

    public Task getTaskById(final int id) {
        return SessionProvider.getEntryById("Task", id);
    }

    public List<Task> getTasksBySearch(final String search) {
        return SessionProvider.runSession(session -> {
            final Query query = session.createQuery("from Task where lower(name) LIKE :s");
            query.setParameter("s", "%" + search + "%");
            return query.list();
        });
    }
}
