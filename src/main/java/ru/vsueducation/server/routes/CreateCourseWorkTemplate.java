package ru.vsueducation.server.routes;

import com.google.gson.Gson;
import ru.vsueducation.db.dao.CourseWorkTemplateDao;
import ru.vsueducation.server.context.Context;
import ru.vsueducation.server.utils.ResponseUtils;
import spark.Request;
import spark.Response;

public class CreateCourseWorkTemplate implements RouteWithContext {

    private static class Body {
        int disciplineId;
        int seasonId;
        int profileId;
        int year;
    }

    @Override
    public Object handleWithContext(Request request, Response response, Context context) {
        final Gson gson = new Gson();
        final Body body = gson.fromJson(request.body(), Body.class);
        ResponseUtils.setResponseJson(response);
        return gson.toJson(new CourseWorkTemplateDao().createCourseWork(
            body.disciplineId,
            body.seasonId,
            body.profileId,
            body.year,
            context.getUser().getId()
        ));
    }
}
