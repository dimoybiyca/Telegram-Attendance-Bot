package com.dimoybiyca.bot.attendancebot.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @NotNull
    @NotBlank
    @Column(name = "day")
    private String day;

    @Column(name = "start_hour")
    @Size(max = 12)
    private int startHour;

    @Column(name = "start_minute")
    @Max(value = 60)
    @Min(value = 0)
    private int startMinute;

    @Column(name = "subgroup")
    @Max(value = 2)
    @Min(value = 0)
    private int group;

    @Column(name = "week")
    @Max(value = 2)
    @Min(value = 0)
    private int week;

    
    public Schedule() {
    }

    public Schedule(Subject subject, String day, int startHour, int startMinute, int group, int week) {
        this.subject = subject;
        this.day = day;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.group = group;
        this.week = week;
    }

    public Schedule(long id, Subject subject, String day, int startHour, int startMinute, int group, int week) {
        this.id = id;
        this.subject = subject;
        this.day = day;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.group = group;
        this.week = week;
    }

    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day;
    }

    public int getStartHour() {
        return startHour;
    }
    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }
    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getGroup() {
        return group;
    }
    public void setGroup(int group) {
        this.group = group;
    }

    public int getWeek() {
        return week;
    }
    public void setWeek(int week) {
        this.week = week;
    }
}
