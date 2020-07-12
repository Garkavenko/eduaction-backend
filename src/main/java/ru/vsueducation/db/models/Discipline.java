package ru.vsueducation.db.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "disciplines")
public class Discipline {
    @Id
    private int id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "discipline_id")
    private List<DisciplineProfile> disciplineProfiles;

    public Discipline() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DisciplineProfile> getDisciplineProfiles() {
        return disciplineProfiles;
    }

    public void setDisciplineProfiles(List<DisciplineProfile> disciplineProfiles) {
        this.disciplineProfiles = disciplineProfiles;
    }
}
