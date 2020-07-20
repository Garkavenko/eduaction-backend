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
        post("/assignment-problem", new AssignmentProblem());
        get("/auth", new Authorization());
        get("/user-info", new AuthorizedRoute(new UserInfo()));
        get("/disciplines", new AuthorizedRoute(new Disciplines()));
        get("/all-course-work-templates", new AuthorizedRoute(new GetAllCourseWorkTemplate(), "teacher"));
        get("/course-work", new AuthorizedRoute(new CourseWorkById(), "teacher"));
        get("/tasks-search", new AuthorizedRoute(new TasksSearch()));
        get("/all-seasons", new AuthorizedRoute(new AllSeasons(), "teacher"));
        get("/task", new AuthorizedRoute(new TaskById()));

        post("/add-task-to-template", new AuthorizedRoute(new AddTaskToTemplate(), "teacher"));
        post("/transportation_problem", new TransportationProblem());
        post("/create-course-work-template", new AuthorizedRoute(new CreateCourseWorkTemplate(), "teacher"));
        post("/remove-course-work-template", new AuthorizedRoute(new RemoveCourseTemplate(), "teacher"));
        post("/remove-tasks-from-template", new AuthorizedRoute(new RemoveTasksFromTemplate(), "teacher"));
    }
}
