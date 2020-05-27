package ru.vsueducation.server.core;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import ru.vsueducation.server.servlets.AssignmentProblemServlet;
import ru.vsueducation.server.servlets.TaskTypes;
import ru.vsueducation.server.servlets.TransportationProblemServlet;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class JettyServer {
    private Server server;

    public void start() throws Exception {
        server = new Server();
        ServerConnector connector = new ServerConnector(server);
        final ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        FilterHolder cors = contextHandler.addFilter(CrossOriginFilter.class,"/*", EnumSet.of(DispatcherType.REQUEST));
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,POST,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin");

// Use a DefaultServlet to serve static files.
// Alternate Holder technique, prepare then add.
// DefaultServlet should be named 'default'
        ServletHolder def = new ServletHolder("default", DefaultServlet.class);
        def.setInitParameter("resourceBase","./http/");
        def.setInitParameter("dirAllowed","false");
        connector.setPort(8090);
       contextHandler.setContextPath("/");
        contextHandler.addServlet(def,"/");
        contextHandler.addServlet(TaskTypes.class, "/task-types");
        contextHandler.addServlet(TransportationProblemServlet.class, "/transportation_problem");
        contextHandler.addServlet(AssignmentProblemServlet.class, "/assignment-problem");
        server.setHandler(contextHandler);
        server.setConnectors(new Connector[]{connector});
        server.start();
    }
}
