package ru.vsueducation.db.dao;

import org.apache.ibatis.session.SqlSession;
import ru.vsueducation.db.mappers.TaskTypesMapper;
import ru.vsueducation.db.models.TaskType;

import java.util.List;

public class TaskTypesDAO {

    public List<TaskType> getTaskTypes() {
        SqlSession sqlSession = MyBatisConfigs.getSqlSessionFactory().openSession();
        TaskTypesMapper mapper = sqlSession.getMapper(TaskTypesMapper.class);
        List<TaskType> attestation = mapper.getTaskTypes();
        sqlSession.commit();
        sqlSession.close();
        return attestation;
    }
}
