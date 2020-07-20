package ru.vsueducation.db.models;

import javax.persistence.*;

@Entity
@Table(name = "tasks_to_course_work_template")
public class TaskToCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int task_id;
    private int course_work_template_id;

    public TaskToCourse() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public int getCourse_work_template_id() {
        return course_work_template_id;
    }

    public void setCourse_work_template_id(int course_work_template_id) {
        this.course_work_template_id = course_work_template_id;
    }
}
