package com.dimoybiyca.bot.attendancebot.repository;

import com.dimoybiyca.bot.attendancebot.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    List<Subject> findByName(String name);

    Subject findByNameAndType(String name, String type);
}
