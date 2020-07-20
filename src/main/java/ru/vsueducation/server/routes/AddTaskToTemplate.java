package ru.vsueducation.server.routes;

import com.google.gson.Gson;
import ru.vsueducation.db.dao.CourseWorkTemplateDao;
import ru.vsueducation.server.context.Context;
import spark.Request;
import spark.Response;

public class AddTaskToTemplate implements RouteWithContext {
    final CourseWorkTemplateDao courseWorkTemplateDao = new CourseWorkTemplateDao();
    final Gson gson = new Gson();

    private static class Body {
        int taskId;
        int templateId;
    }

    @Override
    public Object handleWithContext(Request request, Response response, Context context) {
        final String bodyString = request.body();
        final Body body = gson.fromJson(bodyString, Body.class);
        courseWorkTemplateDao.addTaskToTemplate(body.taskId, body.templateId);
        return "";
    }
}
