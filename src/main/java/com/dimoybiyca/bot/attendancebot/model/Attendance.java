package com.dimoybiyca.bot.attendancebot.model;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

@Entity
@Table(name = "attendance")
@TypeDef(
        name = "int-array",
        typeClass = IntArrayType.class
)
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "number")
    @Min(value = 1)
    @Max(value = 8)
    private int number;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "subject_id_1")
    private Subject subject1;

    @ManyToOne
    @JoinColumn(name = "subject_id_2")
    private Subject subject2;

    @Type( type = "int-array")
    @Column( name = "attendance",
    columnDefinition = "integer[]")
    private int[] attendance;


    public Attendance() {
    }

    public Attendance(Date date, Subject subject1, Subject subject2, int number) {
        this.date = date;
        this.number = number;
        this.subject1 = subject1;
        this.subject2 = subject2;
        this.attendance = new int[27];
        for(int status : attendance) {
            status = 0;
        }
    }

    public Attendance(long id, Date date, Subject subject1, Subject subject2) {
        this.id = id;
        this.date = date;
        this.subject1 = subject1;
        this.subject2 = subject2;
    }


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public Subject getSubject1() {
        return subject1;
    }
    public void setSubject1(Subject subject) {
        this.subject1 = subject;
    }

    public Subject getSubject2() {
        return subject2;
    }
    public void setSubject2(Subject subject) {
        this.subject2 = subject;
    }

    public int[] getAttendance() {
        return attendance;
    }
}
