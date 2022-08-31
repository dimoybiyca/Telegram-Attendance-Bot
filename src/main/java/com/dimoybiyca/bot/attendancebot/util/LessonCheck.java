package com.dimoybiyca.bot.attendancebot.util;

import com.dimoybiyca.bot.attendancebot.model.Schedule;
import com.dimoybiyca.bot.attendancebot.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
public class LessonCheck {

    private ScheduleService scheduleService;

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    public int getNumberNow(LocalTime localTime) {
        if(localTime.isAfter(LocalTime.of(8, 30))
                && localTime.isBefore(LocalTime.of(10,5))) {
            return 1;
        } else if (localTime.isAfter(LocalTime.of(10, 20))
                && localTime.isBefore(LocalTime.of(11,55))) {
            return 2;
        } else if (localTime.isAfter(LocalTime.of(12, 10))
                && localTime.isBefore(LocalTime.of(13,45))) {
            return 3;
        } else if (localTime.isAfter(LocalTime.of(14, 15))
                && localTime.isBefore(LocalTime.of(15,50))) {
            return 4;
        } else if (localTime.isAfter(LocalTime.of(16,0))
                && localTime.isBefore(LocalTime.of(17,35))) {
            return 5;
        } else if (localTime.isAfter(LocalTime.of(17, 40))
                && localTime.isBefore(LocalTime.of(19,15))) {
            return 6;
        } else if (localTime.isAfter(LocalTime.of(19, 20))
                && localTime.isBefore(LocalTime.of(20,55))) {
            return 7;
        } else if (localTime.isAfter(LocalTime.of(21, 0))
                && localTime.isBefore(LocalTime.of(22,35))) {
            return 8;
        }

        return 0;
    }

    public int getLessonHour(int number) {
        if(number > 0 && number < 9) {
            switch (number) {
                case 1:
                    return 8;
                case 2:
                    return 10;
                case 3:
                    return 12;
                case 4:
                    return 14;
                case 5:
                    return 16;
                case 6:
                    return 17;
                case 7:
                    return 19;
                case 8:
                    return 21;
            }
        }

        return -1;
    }

    public int getLessonHour(LocalTime localTime) {
        return getLessonHour(getNumberNow(localTime));
    }

    public boolean isLessonNow() {
        LocalDateTime current = LocalDateTime.now();

        int dayInYear = current.getDayOfYear();
        int odd = 0;
        if(dayInYear > 1) {
            odd = (current.getDayOfYear() - 1) / 7;
        }

        List<Schedule> now = scheduleService.getLessonsNow();

        boolean result = false;

        if(now.size() > 0) {
            for (Schedule subject : now) {
                if (subject.getWeek() == 0) {
                    return true;
                } else {
                     result = (subject.getWeek() - 1) == odd % 2;

                     if(result == true) {
                         return result;
                     }
                }
            }
        }

        return false;
    }

    public boolean isLessonInSubGroup(int subGroup) {
        if(subGroup == 1 || subGroup == 2) {

            List<Schedule> today = scheduleService.getLessonsNow();

            boolean result;

            if(today.size() == 1) {
                if(today.get(0).getGroup() == 0) {
                    return true;
                }

                return subGroup == today.get(0).getGroup();
            } else{
                for (Schedule schedule : today) {
                    if(schedule.getGroup() == subGroup || schedule.getGroup() == 0) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}