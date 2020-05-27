package ru.vsueducation.server.servlets;

import com.google.gson.Gson;
import ru.vsueducation.resolvers.HungarianAlgorithm;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AssignmentProblemServlet extends HttpServlet {

    public static class Body {
        public int x;
        public int y;
        public Boolean isMax = null;
        public double[][] table;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        final Gson gson = new Gson();
        final Body body = gson.fromJson(req.getReader(), Body.class);
        try {
            final double[][] matrix = body.table;
            final int[][] assignment = HungarianAlgorithm.hgAlgorithm(matrix, body.isMax == null || !body.isMax ? "min" : "max");
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println(HungarianAlgorithm.getJsonResult(matrix, assignment));
            resp.getWriter().close();
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().close();
        }
    }
}
