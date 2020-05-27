package ru.vsueducation.db.models;

public class Task {
    private int id;
    private int type_id;
    private String name;
    private String description;
    private String input_schema;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
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
