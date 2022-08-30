package com.dimoybiyca.bot.attendancebot.service;

import com.dimoybiyca.bot.attendancebot.model.Attendance;
import com.dimoybiyca.bot.attendancebot.model.Subject;
import com.dimoybiyca.bot.attendancebot.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public List<Attendance> findBySubject(Subject subject) {
        return attendanceRepository.findBySubject1OrSubject2OrderById(subject, subject);
    }

    public Attendance create(Attendance attendance) {
        try {
            return attendanceRepository.save(attendance);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public void update(Attendance attendance) {
        if (attendance != null) {
            Attendance oldAttendance = getLast();
            if (oldAttendance != null) {
                attendanceRepository.save(attendance);
            }
        }
    }

    public Attendance getLast() {
        return attendanceRepository.findFirstByOrderByIdDesc();
    }
}
