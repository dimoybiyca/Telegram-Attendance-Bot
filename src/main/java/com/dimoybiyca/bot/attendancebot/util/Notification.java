package com.dimoybiyca.bot.attendancebot.util;

import com.dimoybiyca.bot.attendancebot.messages.MessageSender;
import com.dimoybiyca.bot.attendancebot.model.Subject;
import com.dimoybiyca.bot.attendancebot.model.User;
import com.dimoybiyca.bot.attendancebot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Component
public class Notification {

    @Value("${admin.chat.id}")
    private long adminChatId;

    @Value("${owner.chat.id}")
    private long ownerChatId;

    private final UserService userService;

    private final MessageSender messageSender;

    @Autowired
    public Notification(UserService userService, MessageSender messageSender) {
        this.userService = userService;
        this.messageSender = messageSender;
    }

    public void notifyAllUsersAboutLes(Subject lessonSub1, Subject lessonSub2) {
        List<User> allUsers = userService.readAll();

        for(User user : allUsers) {

            if(user.getVariant() < 14) {
                if(lessonSub1 != null) {
                    messageSender.sendMessageWithKeyboard(user.getChatId(), "Зараз " + lessonSub1.getName());
                }
            } else {
                if (lessonSub2 != null) {
                    messageSender.sendMessageWithKeyboard(user.getChatId(), "Зараз " + lessonSub2.getName());
                }
            }
        }
    }

    public void notifyAllUsers(String text, Message sender) {

        if(sender.getChatId().equals(ownerChatId)
        || sender.getChatId().equals(adminChatId)) {
            List<User> allUsers = userService.readAll();

            for (User user : allUsers) {

                messageSender.sendMessageWithKeyboard(user.getChatId(), text);
            }
        }
    }
}
