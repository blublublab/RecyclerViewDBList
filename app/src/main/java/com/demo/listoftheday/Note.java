package com.demo.listoftheday;

public class Note {
    private String title;
    private String description;
    private String dayOfTheWeek;
    private int priority;

    public int getId() {
        return id;
    }

    private int id;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public int getPriority() {
        return priority;
    }

    public Note(String title, String description, String dayOfTheWeek, int priority, int id) {
        this.title = title;
        this.description = description;
        this.dayOfTheWeek = dayOfTheWeek;
        this.priority = priority;
        this.id = id;
    }
}
