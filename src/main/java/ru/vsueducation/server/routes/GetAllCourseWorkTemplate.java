package ru.vsueducation.server.routes;

import com.google.gson.Gson;
import ru.vsueducation.db.dao.CourseWorkTemplateDao;
import ru.vsueducation.server.context.Context;
import ru.vsueducation.server.utils.ResponseUtils;
import spark.Request;
import spark.Response;

public class GetAllCourseWorkTemplate extends RouteWithContext {

    @Override
    Object handleWithContext(Request request, Response response, Context context) throws Exception {
        ResponseUtils.setResponseJson(response);
        return new Gson().toJson(new CourseWorkTemplateDao().getAllCourseWorks());
    }
}
