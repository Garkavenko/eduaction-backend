package ru.vsueducation.server.routes;

import com.google.gson.Gson;
import ru.vsueducation.db.dao.CourseWorkTemplateDao;
import ru.vsueducation.server.context.Context;
import ru.vsueducation.server.utils.ResponseUtils;
import spark.Request;
import spark.Response;

public class AllSeasons implements RouteWithContext {

    private final CourseWorkTemplateDao courseWorkTemplateDao = new CourseWorkTemplateDao();
    private final Gson gson = new Gson();

    @Override
    public Object handleWithContext(Request request, Response response, Context context) throws Exception {
        ResponseUtils.setResponseJson(response);
        return gson.toJson(courseWorkTemplateDao.getAllSeasons());
    }
}
