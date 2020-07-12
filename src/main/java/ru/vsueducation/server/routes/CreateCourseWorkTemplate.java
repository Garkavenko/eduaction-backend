package ru.vsueducation.server.routes;

import com.google.gson.Gson;
import ru.vsueducation.db.dao.CourseWorkTemplateDao;
import ru.vsueducation.server.context.Context;
import spark.Request;
import spark.Response;

public class CreateCourseWorkTemplate extends RouteWithContext {

    private static class Body {
        int discipline_id;
        int season_id;
        int discipline_profile_id;
        int year_number;
    }

    @Override
    Object handleWithContext(Request request, Response response, Context context) {
        final Gson gson = new Gson();
        final Body body = gson.fromJson(request.body(), Body.class);
        new CourseWorkTemplateDao().createCourseWork(
            body.discipline_id,
            body.season_id,
            body.discipline_profile_id,
            body.year_number,
            context.getUser().getId()
        );
        response.status(200);
        return "";
    }
}
