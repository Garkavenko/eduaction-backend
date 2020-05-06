package ru.vsueducation.db.mappers;
import org.apache.ibatis.annotations.Select;
import ru.vsueducation.db.models.TaskType;

import java.util.List;

public interface TaskTypesMapper {
    @Select("SELECT tt.id, tt.name FROM task_types tt")
    List<TaskType> getTaskTypes();
}
