package com.dimoybiyca.bot.attendancebot.repository;

import com.dimoybiyca.bot.attendancebot.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

}
