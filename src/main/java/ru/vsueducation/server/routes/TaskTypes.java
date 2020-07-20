package ru.vsueducation.server.routes;
import com.google.gson.Gson;
import ru.vsueducation.db.dao.TasksDao;
import ru.vsueducation.db.models.TaskType;
import spark.Request;
import spark.Response;
import spark.Route;
import java.util.List;

public class TaskTypes implements Route {

    final private TasksDao tasksDAO = new TasksDao();

    @Override
    public Object handle(Request request, Response response) {
        response.type("application/json");
        response.status(200);
        final List<TaskType> taskTypes = tasksDAO.getTaskTypes();
        final Gson gson = new Gson();
        return gson.toJson(taskTypes);
    }
}
