package com.dimoybiyca.bot.attendancebot.repository;

import com.dimoybiyca.bot.attendancebot.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Attendance findFirstByOrderByIdDesc();
}
