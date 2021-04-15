package com.example.timetable;

public class Schedule {
    private int id;
    private String subject;
    private String day;
    private String time;

    public Schedule(int id, String subject, String day, String time) {
        this.id = id;
        this.subject = subject;
        this.day = day;
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public int getId() { return id; }
}
