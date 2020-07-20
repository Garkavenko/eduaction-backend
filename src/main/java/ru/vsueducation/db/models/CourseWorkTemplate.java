package ru.vsueducation.db.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "course_work_template")
public class CourseWorkTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(cascade = CascadeType.DETACH, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;
    @OneToOne(cascade = CascadeType.DETACH, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "discipline_profile_id")
    private DisciplineProfile disciplineProfile;
    private Integer year_number;
    @OneToOne(cascade = CascadeType.DETACH, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "season_id")
    private Season season;
    @OneToOne(cascade = CascadeType.DETACH, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "tasks_to_course_work_template",
        joinColumns = { @JoinColumn(name = "course_work_template_id") },
        inverseJoinColumns = { @JoinColumn(name = "task_id") }
    )
    Set<Task> taskList;

    public CourseWorkTemplate() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear_number() {
        return year_number;
    }

    public void setYear_number(int year_number) {
        this.year_number = year_number;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public DisciplineProfile getDisciplineProfile() {
        return disciplineProfile;
    }

    public void setDisciplineProfile(DisciplineProfile disciplineProfile) {
        this.disciplineProfile = disciplineProfile;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(Set<Task> taskList) {
        this.taskList = taskList;
    }
}
