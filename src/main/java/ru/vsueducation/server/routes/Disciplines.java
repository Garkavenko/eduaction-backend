package ru.vsueducation.server.routes;

import com.google.gson.Gson;
import ru.vsueducation.db.dao.EducationTablesDao;
import ru.vsueducation.server.context.Context;
import ru.vsueducation.server.utils.ResponseUtils;
import spark.Request;
import spark.Response;

public class Disciplines extends RouteWithContext {
    @Override
    Object handleWithContext(Request request, Response response, Context context) {
        ResponseUtils.setResponseJson(response);
        return new Gson().toJson(new EducationTablesDao().getAllDisciplines());
    }
}
