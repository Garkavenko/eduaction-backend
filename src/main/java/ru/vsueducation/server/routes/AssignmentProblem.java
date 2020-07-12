package ru.vsueducation.server.routes;

import com.google.gson.Gson;
import ru.vsueducation.resolvers.HungarianAlgorithm;
import spark.Request;
import spark.Response;
import spark.Route;

public class AssignmentProblem implements Route {
    public static class Body {
        public Boolean isMax = null;
        public double[][] table;
    }

    @Override
    public Object handle(Request request, Response response) {
        final Gson gson = new Gson();
        final Body body = gson.fromJson(request.body(), Body.class);
        final double[][] matrix = body.table;
        final int[][] assignment = HungarianAlgorithm.hgAlgorithm(matrix, body.isMax == null || !body.isMax ? "min" : "max");
        response.status(200);
        response.type("application/json");
        return HungarianAlgorithm.getJsonResult(matrix, assignment);
    }
}
