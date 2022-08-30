package com.dimoybiyca.bot.attendancebot.messages.Procesor;

import com.dimoybiyca.bot.attendancebot.messages.MessageSender;
import com.dimoybiyca.bot.attendancebot.model.Attendance;
import com.dimoybiyca.bot.attendancebot.model.Schedule;
import com.dimoybiyca.bot.attendancebot.model.Subject;
import com.dimoybiyca.bot.attendancebot.model.User;
import com.dimoybiyca.bot.attendancebot.service.AttendanceService;
import com.dimoybiyca.bot.attendancebot.service.ScheduleService;
import com.dimoybiyca.bot.attendancebot.service.UserService;
import com.dimoybiyca.bot.attendancebot.util.LessonCheck;
import com.dimoybiyca.bot.attendancebot.util.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Component
public class AttendanceMessage {

    private final MessageSender messageSender;

    private final UserService userService;

    private final LessonCheck lessonCheck;

    private final AttendanceService attendanceService;

    private final ScheduleService scheduleService;

    private final Notification notification;


    @Autowired
    public AttendanceMessage(MessageSender messageSender,
                             UserService userService,
                             LessonCheck lessonCheck,
                             AttendanceService attendanceService,
                             ScheduleService scheduleService, Notification notification) {
        this.messageSender = messageSender;
        this.userService = userService;
        this.lessonCheck = lessonCheck;
        this.attendanceService = attendanceService;
        this.scheduleService = scheduleService;
        this.notification = notification;
    }


    public void setAttend(Message message, int value) {

        User sender = userService.readByChatId(message.getChatId());

        if(sender == null) {
            messageSender.sendMessageWithKeyboard(message, "Ви не зареєстровані, введіть /start щоб зареєструватись");

            return;
        }

        if(!lessonCheck.isLessonNow()) {
            messageSender.sendMessageWithKeyboard(message, "Зараз немає пари");

            return;
        }

        if(!lessonCheck.isLessonInSubGroup(sender.getSubGroup())) {
            messageSender.sendMessageWithKeyboard(message, "Зараз немає пари у "
                    + sender.getSubGroup() + " підгрупи");

            return;
        }


        Date dateToCheck = new Date();

        Attendance last = attendanceService.getLast();

        int currentSubjectNumber = lessonCheck.getNumberNow(LocalTime.now());

        List<Schedule> today = scheduleService.getLessonsNow();

        if(last == null
                || last.getDate().getDay() != dateToCheck.getDay()
                || last.getNumber() != currentSubjectNumber) {

            Subject currentSubject1 = null;
            Subject currentSubject2 = null;

            if(today.size() > 1) {
                for (Schedule schedule : today) {
                    if(schedule.getGroup() == 0) {
                        currentSubject1 = schedule.getSubject();
                        currentSubject2 = schedule.getSubject();
                        break;
                    } else if (schedule.getGroup() == 1) {
                        currentSubject1 = schedule.getSubject();
                    } else if (schedule.getGroup() == 2) {
                        currentSubject2 = schedule.getSubject();
                    }
                }
            } else {
                currentSubject1 = today.get(0).getSubject();
                currentSubject2 = today.get(0).getSubject();
            }

            if(currentSubject1 != null && currentSubject2 != null) {
                Attendance newAttendance = new Attendance(new Date(),
                        currentSubject1,
                        currentSubject2,
                        currentSubjectNumber);
                attendanceService.create(newAttendance);

                notification.notifyAllUsersAboutLes(currentSubject1, currentSubject2);
            }
        }
        Attendance currentAttendance = attendanceService.getLast();

        currentAttendance.getAttendance()[sender.getVariant() - 1] = value;

        attendanceService.update(currentAttendance);


        Subject subject = null;
        if(today.size() == 1) {
            subject = today.get(0).getSubject();
        } else {
            for (Schedule schedule : today) {
                if(schedule.getGroup() == sender.getSubGroup()) {
                    subject = schedule.getSubject();
                    break;
                }
            }
        }

        String status = "відсутній";
        if(value == 1) {
            status = "присутній";
        }
        messageSender.sendMessageWithKeyboard(message, "Встановлено значення: " + status
        + "\nдля " + sender.getName()
        + "\nна " + subject.getName()
        + "\n(" + subject.getType() + ")");

    }
}
