package ru.vsueducation.server.core;
import ru.vsueducation.server.routes.*;

import static spark.Spark.*;

public class Server {
    public void start() {
        port(8090);
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request
                    .headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers",
                        accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request
                    .headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods",
                        accessControlRequestMethod);
            }
            return "OK";
        });

        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

        get("/task-types", new TaskTypes());
        get("/assignment-problem", new AssignmentProblem());
        get("/login", new Authorization());
        get("/user-info", new AuthorizedRoute(new UserInfo()));
        get("/disciplines", new AuthorizedRoute(new Disciplines()));
        get("/all-course-work-templates", new AuthorizedRoute(new GetAllCourseWorkTemplate(), "teacher"));

        post("/transportation_problem", new TransportationProblem());
        post("/create-course-work-template", new AuthorizedRoute(new CreateCourseWorkTemplate(), "teacher"));
    }
}
