package com.dimoybiyca.bot.attendancebot.service;

import com.dimoybiyca.bot.attendancebot.model.Attendance;
import com.dimoybiyca.bot.attendancebot.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }


    public Attendance create(Attendance attendance) {
        try {
            return attendanceRepository.save(attendance);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public Attendance update(Attendance attendance) {
        if (attendance != null) {
            Attendance oldAttendance = getLast();
            if (oldAttendance != null) {
                return attendanceRepository.save(attendance);
            }
        }
        return null;
    }

    public Attendance getLast() {
        return attendanceRepository.findFirstByOrderByIdDesc();
    }
}
