package ru.vsueducation.server.utils;
import spark.Response;

public class ResponseUtils {
    public static void setResponseJson(final Response response) {
        response.type("application/json");
        response.status(200);
    }
}
