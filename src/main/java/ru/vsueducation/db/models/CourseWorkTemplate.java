package ru.vsueducation.db.models;

import javax.persistence.*;

@Entity
@Table(name = "course_work_template")
public class CourseWorkTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(cascade = CascadeType.DETACH, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;
    @OneToOne(cascade = CascadeType.DETACH, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "discipline_profile_id")
    private DisciplineProfile disciplineProfile;
    private int year_number;
    @OneToOne(cascade = CascadeType.DETACH, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "season_id")
    private Season season;
    @OneToOne(cascade = CascadeType.DETACH, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

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
}
