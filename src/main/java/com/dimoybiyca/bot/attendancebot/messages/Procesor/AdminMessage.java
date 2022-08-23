package com.dimoybiyca.bot.attendancebot.messages.Procesor;

import com.dimoybiyca.bot.attendancebot.messages.MessageSender;
import com.dimoybiyca.bot.attendancebot.model.Attendance;
import com.dimoybiyca.bot.attendancebot.model.Subject;
import com.dimoybiyca.bot.attendancebot.model.User;
import com.dimoybiyca.bot.attendancebot.service.AttendanceService;
import com.dimoybiyca.bot.attendancebot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class AdminMessage {

    @Value("${admin.chat.id}")
    private long adminChatId;

    @Value("${owner.chat.id}")
    private long ownerChatId;

    private final MessageSender messageSender;

    private final UserService userService;

    private final AttendanceService attendanceService;

    @Autowired
    public AdminMessage(MessageSender messageSender, UserService userService, AttendanceService attendanceService) {
        this.messageSender = messageSender;
        this.userService = userService;
        this.attendanceService = attendanceService;
    }

    public void getJournal(Message message) {
        if(message.getChatId() == adminChatId
                || message.getChatId() == ownerChatId) {

            Attendance last = attendanceService.getLast();
            Subject subject1 = last.getSubject1();
            Subject subject2 = last.getSubject2();

            StringBuilder adminMessage = new StringBuilder(subject1.getName());

            int[] attendance = last.getAttendance();

            for(int i = 0; i < attendance.length; i++) {

                if(i == 14) {
                    if(!subject1.equals(subject2)){
                        adminMessage.append("\n\n").append(subject2.getName());
                    }
                }

                int variant = i + 1;

                User student = userService.readByVariant(variant);

                if(student != null) {
                    adminMessage.append("\n");

                    String status = "-";
                    if(attendance[i] == 1) {
                        status = "+";
                    }

                    adminMessage.append(variant).append("\t ").append(student.getName()).append("\t ").append(status);
                }
            }

            messageSender.sendMessageWithKeyboard(message, adminMessage.toString());
        } else {
            messageSender.sendMessageWithKeyboard(message, "Ви не можете виконати цю команду");
        }
    }
}
