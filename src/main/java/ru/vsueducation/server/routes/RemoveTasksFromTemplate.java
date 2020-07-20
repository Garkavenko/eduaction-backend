package ru.vsueducation.server.routes;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import ru.vsueducation.db.dao.CourseWorkTemplateDao;
import ru.vsueducation.server.context.Context;
import ru.vsueducation.server.utils.ResponseUtils;
import spark.Request;
import spark.Response;

import java.util.List;

public class RemoveTasksFromTemplate implements RouteWithContext {

    private static class Body {
        List<Integer> taskIds;
        Integer templateId;
    }

    private final Gson gson = new Gson();
    private final CourseWorkTemplateDao courseWorkTemplateDao = new CourseWorkTemplateDao();

    @Override
    public Object handleWithContext(Request request, Response response, Context context) {
        final Body body = gson.fromJson(request.body(), Body.class);
        courseWorkTemplateDao.removeTasksFromTemplate(body.taskIds, body.templateId);
        ResponseUtils.setResponseJson(response);
        return new JsonObject().toString();
    }
}
