package ru.vsueducation.server.routes;

import com.google.gson.Gson;
import ru.vsueducation.db.dao.TasksDao;
import ru.vsueducation.server.context.Context;
import ru.vsueducation.server.utils.ResponseUtils;
import spark.Request;
import spark.Response;

public class TaskById implements RouteWithContext {
    final TasksDao tasksDao = new TasksDao();
    final Gson gson = new Gson();

    @Override
    public Object handleWithContext(Request request, Response response, Context context) {
        ResponseUtils.setResponseJson(response);
        return gson.toJson(tasksDao.getTaskById(Integer.parseInt(request.queryParams("id"))));
    }
}
