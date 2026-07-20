package dev.tharvbytes.tasks.model;

public class Task {
    private Long id;
    private String title;
    private boolean done;
    private String ownerEmail;

    public Task() {
    }

    public Task(Long id, String title, boolean done, String ownerEmail) {
        this.id = id;
        this.title = title;
        this.done = done;
        this.ownerEmail = ownerEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }
}
