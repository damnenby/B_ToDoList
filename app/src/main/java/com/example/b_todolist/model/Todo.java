package com.example.b_todolist.model;

public class Todo {
    private int id;
    private String title;
    private String description;
    private String priority;
    private String categories;
    private boolean done;
    private long dueDateMillis;

    public Todo() {
        this(-1, "", "", "Mittel", "", false, 0L);
    }

    public Todo(int id, String title, String description, String priority,
                String categories, boolean done, long dueDateMillis) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.categories = categories;
        this.done = done;
        this.dueDateMillis = dueDateMillis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public long getDueDateMillis() {
        return dueDateMillis;
    }

    public void setDueDateMillis(long dueDateMillis) {
        this.dueDateMillis = dueDateMillis;
    }
}
