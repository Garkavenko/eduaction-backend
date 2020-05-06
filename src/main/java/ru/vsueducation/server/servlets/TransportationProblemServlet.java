package ru.vsueducation.server.servlets;

import com.google.gson.Gson;
import ru.vsueducation.resolvers.TransportationProblem;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TransportationProblemServlet extends HttpServlet {

    public static class Body {
        public int x;
        public int y;
        public List<Integer> posts;
        public List<Integer> shops;
        public List<List<Double>> table;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        final Gson gson = new Gson();
        final Body body = gson.fromJson(req.getReader(), Body.class);
        final TransportationProblem transportationProblem = new TransportationProblem(body);
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(transportationProblem.getJsonResult());
        resp.getWriter().close();
    }
}
