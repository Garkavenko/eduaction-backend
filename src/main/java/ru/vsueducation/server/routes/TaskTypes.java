package ru.vsueducation.server.routes;
import com.google.gson.Gson;
import ru.vsueducation.db.dao.TaskTypesDao;
import ru.vsueducation.db.models.TaskType;
import spark.Request;
import spark.Response;
import spark.Route;
import java.util.List;

public class TaskTypes implements Route {

    final private TaskTypesDao taskTypesDAO = new TaskTypesDao();

    @Override
    public Object handle(Request request, Response response) {
        response.type("application/json");
        response.status(200);
        final List<TaskType> taskTypes = taskTypesDAO.getTaskTypes();
        final Gson gson = new Gson();
        return gson.toJson(taskTypes);
    }
}
