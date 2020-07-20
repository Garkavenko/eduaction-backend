package ru.vsueducation.server.routes;

import com.google.gson.Gson;
import ru.vsueducation.db.dao.TasksDao;
import ru.vsueducation.server.context.Context;
import ru.vsueducation.server.utils.ResponseUtils;
import spark.Request;
import spark.Response;

public class TasksSearch implements RouteWithContext {

    private final TasksDao tasksDao = new TasksDao();
    private final Gson gson = new Gson();

    @Override
    public Object handleWithContext(Request request, Response response, Context context) throws Exception {
        String search = request.queryParams("search");
        if (search == null) search = "";
        ResponseUtils.setResponseJson(response);
        return gson.toJson(tasksDao.getTasksBySearch(search));
    }
}
