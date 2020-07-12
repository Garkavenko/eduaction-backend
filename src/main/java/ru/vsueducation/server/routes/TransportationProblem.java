package ru.vsueducation.server.routes;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;
import java.util.List;

public class TransportationProblem implements Route {
    public static class Body {
        public int x;
        public int y;
        public List<Integer> posts;
        public List<Integer> shops;
        public List<List<Double>> table;
    }

    @Override
    public Object handle(Request request, Response response) {
        final Gson gson = new Gson();
        final Body body = gson.fromJson(request.body(), Body.class);
        final ru.vsueducation.resolvers.TransportationProblem transportationProblem = new ru.vsueducation.resolvers.TransportationProblem(body);
        response.type("application/json");
        response.status(200);
        return transportationProblem.getJsonResult();
    }
}
