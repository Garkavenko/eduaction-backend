package ru.vsueducation.server.routes;

import com.google.gson.Gson;
import ru.vsueducation.server.context.Context;
import spark.Request;
import spark.Response;

import static ru.vsueducation.server.utils.ResponseUtils.setResponseJson;

public class UserInfo extends RouteWithContext {
    @Override
    Object handleWithContext(final Request request, final Response response, final Context context) {
        setResponseJson(response);
        return new Gson().toJson(context.getUser());
    }
}
