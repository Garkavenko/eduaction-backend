package ru.vsueducation.server.routes;

import com.google.gson.Gson;
import ru.vsueducation.db.dao.CourseWorkTemplateDao;
import ru.vsueducation.server.context.Context;
import ru.vsueducation.server.utils.ResponseUtils;
import spark.Request;
import spark.Response;

public class GetAllCourseWorkTemplate implements RouteWithContext {

    @Override
    public Object handleWithContext(Request request, Response response, Context context) {
        ResponseUtils.setResponseJson(response);
        Integer disciplineId = null;
        Integer profileId = null;
        Integer seasonId = null;
        Integer year = null;
        if (request.queryParams("disciplineId") != null) disciplineId = Integer.parseInt(request.queryParams("disciplineId"));
        if (request.queryParams("profileId") != null) profileId = Integer.parseInt(request.queryParams("profileId"));
        if (request.queryParams("seasonId") != null) seasonId = Integer.parseInt(request.queryParams("seasonId"));
        if (request.queryParams("year") != null) year = Integer.parseInt(request.queryParams("year"));
        return new Gson().toJson(new CourseWorkTemplateDao().getAllCourseWorks(disciplineId, profileId, seasonId, year));
    }
}
