package ru.vsueducation.server.routes;

import ru.vsueducation.server.context.Context;
import spark.Request;
import spark.Response;

public abstract class RouteWithContext {
    abstract Object handleWithContext(final Request request, final Response response, final Context context) throws Exception;
}
