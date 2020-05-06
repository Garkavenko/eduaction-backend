package ru.vsueducation.server.servlets;

import com.google.gson.Gson;
import ru.vsueducation.resolvers.AssignmentProblem;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AssignmentProblemServlet extends HttpServlet {

    public static class Body {
        public int x;
        public int y;
        public int[][] table;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        final Gson gson = new Gson();
        final Body body = gson.fromJson(req.getReader(), Body.class);
        try {
            final AssignmentProblem assignmentProblem = new AssignmentProblem(body.table);
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println(assignmentProblem.getJsonResult());
            resp.getWriter().close();
        } catch (IllegalAccessException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().close();
        }
    }
}
