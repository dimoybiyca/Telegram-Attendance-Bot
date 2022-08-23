package com.dimoybiyca.bot.attendancebot.service;

import com.dimoybiyca.bot.attendancebot.model.Schedule;
import com.dimoybiyca.bot.attendancebot.repository.ScheduleRepository;
import com.dimoybiyca.bot.attendancebot.util.LessonCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private LessonCheck lessonCheck;


    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Autowired
    public void setLessonCheck(LessonCheck lessonCheck) {
        this.lessonCheck = lessonCheck;
    }


    public List<Schedule> readByDay(String day) {
        return scheduleRepository.findAllByDayIgnoreCase(day);
    }

    public List<Schedule> readByDayAndHour(String day, int hour){
        return scheduleRepository.findByDayAndStartHour(day.toUpperCase(), hour);
    }

    public List<Schedule> getLessonsNow() {
        LocalDateTime current = LocalDateTime.now();
        int currentLessonHour = lessonCheck.getLessonHour(LocalTime.now());
        List<Schedule> result = new ArrayList<>();

        if(currentLessonHour != -1) {

            List<Schedule> today = readByDay(current.getDayOfWeek().toString());

            for (Schedule subject : today) {
                if (subject.getStartHour() == currentLessonHour) {
                    result.add(subject);
                }
            }
        }

        return result;
    }

    public Schedule create(Schedule schedule) {
        try {
            return scheduleRepository.save(schedule);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
