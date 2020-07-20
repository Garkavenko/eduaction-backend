package ru.vsueducation.server.routes;

import com.google.gson.Gson;
import ru.vsueducation.db.dao.CourseWorkTemplateDao;
import ru.vsueducation.server.context.Context;
import spark.Request;
import spark.Response;

public class RemoveCourseTemplate implements RouteWithContext {
    private static class Body {
        int id;
    }
    @Override
    public Object handleWithContext(Request request, Response response, Context context) {
        final Body body = new Gson().fromJson(request.body(), Body.class);
        new CourseWorkTemplateDao().removeCourseWork(body.id);
        response.status(200);
        return "";
    }
}
