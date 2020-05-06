package ru.vsueducation.db.dao;

import org.apache.ibatis.session.SqlSession;
import ru.vsueducation.db.mappers.TasksMapper;
import ru.vsueducation.db.models.Task;

import java.util.List;

public class TasksDAO {
    public List<Task> getTasks(int type_id) {
        SqlSession sqlSession = MyBatisConfigs.getSqlSessionFactory().openSession();
        TasksMapper mapper = sqlSession.getMapper(TasksMapper.class);
        List<Task> tasks = mapper.getTasks(type_id);
        sqlSession.commit();
        sqlSession.close();
        return tasks;
    }
}
