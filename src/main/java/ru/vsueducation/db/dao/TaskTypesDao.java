package ru.vsueducation.db.dao;

import org.hibernate.Session;
import ru.vsueducation.db.configs.HibernateConfig;
import ru.vsueducation.db.models.TaskType;

import java.util.List;

public class TaskTypesDao {

    public List<TaskType> getTaskTypes() {
        final Session sqlSession = HibernateConfig.getSessionFactory().openSession();
        final List<TaskType> attestation = sqlSession.createQuery(" from TaskType").list();
        sqlSession.close();
        return attestation;
    }
}
