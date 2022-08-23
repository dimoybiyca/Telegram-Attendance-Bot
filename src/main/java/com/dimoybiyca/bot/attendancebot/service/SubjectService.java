package com.dimoybiyca.bot.attendancebot.service;

import com.dimoybiyca.bot.attendancebot.model.Subject;
import com.dimoybiyca.bot.attendancebot.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;


    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }


    public List<Subject> findByName(String name) {
        return subjectRepository.findByName(name);
    }

    public Subject findByNameAndType(String name, String type) {
        return subjectRepository.findByNameAndType(name, type);
    }
}
