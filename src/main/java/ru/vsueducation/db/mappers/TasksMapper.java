package ru.vsueducation.db.mappers;
import org.apache.ibatis.annotations.Select;
import ru.vsueducation.db.models.Task;

import java.util.List;

public interface TasksMapper {
    @Select("SELECT * FROM tasks WHERE type_id = #{type_id}")
    List<Task> getTasks(int type_id);
}
