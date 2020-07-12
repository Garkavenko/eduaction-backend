package ru.vsueducation.db.models;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    private int id;
    private String name;
    private String description;
    private String input_schema;
    private String url;
    private int type_id;

    public Task() {}

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

    public String getInput_schema() {
        return input_schema;
    }

    public void setInput_schema(String input_schema) {
        this.input_schema = input_schema;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
