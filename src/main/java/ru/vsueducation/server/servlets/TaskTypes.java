package ru.vsueducation.server.servlets;

import com.google.gson.Gson;
import ru.vsueducation.db.dao.TaskTypesDAO;
import ru.vsueducation.db.dao.TasksDAO;
import ru.vsueducation.db.models.Task;
import ru.vsueducation.db.models.TaskType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TaskTypes extends HttpServlet {

    final private TaskTypesDAO taskTypesDAO = new TaskTypesDAO();
    final private TasksDAO tasksDAO = new TasksDAO();


    private static class TaskTypeWithTasks {
        int id;
        String name;
        List<Task> tasks;
    }

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        final Gson gson = new Gson();

        final List<TaskType> taskTypes = taskTypesDAO.getTaskTypes();
        final List<TaskTypeWithTasks> result = taskTypes.stream()
                .map((taskType -> {
                    final TaskTypeWithTasks taskTypeWithTasks = new TaskTypeWithTasks();
                    taskTypeWithTasks.id = taskType.getId();
                    taskTypeWithTasks.name = taskType.getName();
                    taskTypeWithTasks.tasks = tasksDAO.getTasks(taskType.getId());
                    return taskTypeWithTasks;
                })).collect(Collectors.toList());
        final String resultJson = gson.toJson(result);
        response.getWriter().println(resultJson);
        response.getWriter().close();
    }
}
