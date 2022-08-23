package com.dimoybiyca.bot.attendancebot.repository;

import com.dimoybiyca.bot.attendancebot.model.Attendance;
import com.dimoybiyca.bot.attendancebot.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Attendance findFirstByOrderByIdDesc();

    List<Attendance> findBySubject1OrSubject2OrderById(Subject subject1, Subject subject2);
}
