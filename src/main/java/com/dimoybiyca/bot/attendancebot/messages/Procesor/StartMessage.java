package com.dimoybiyca.bot.attendancebot.messages.Procesor;

import com.dimoybiyca.bot.attendancebot.messages.MessageSender;
import com.dimoybiyca.bot.attendancebot.model.User;
import com.dimoybiyca.bot.attendancebot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Component
public class StartMessage {

    private MessageSender messageSender;

    private final UserService userService;


    @Autowired
    public StartMessage(UserService userService) {
        this.userService = userService;
    }


    @Autowired
    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void startMes(Message message){
        Optional<User> sender = Optional.ofNullable(userService.readByChatId(message.getChatId()));

        if(sender.isPresent()) {
            messageSender.sendMessage(message, "Ви уже зареєстровані");
        } else {
            messageSender.sendMessage(message,
                    "Введіть свій номер у групі і повне імя у форматі:\n" +
                            "<номер> <Прізвище> <Ім'я> <По батькові>");
        }
    }

    public boolean createNew(Message message) {
        Optional<User> sender = Optional.ofNullable(userService.readByChatId(message.getChatId()));

        if(sender.isEmpty()) {
            String[] text = message.getText().split(" ");

            int variant = 0;

            String name = text[1] + " " + text[2] + " " + text[3];

            try {
                variant = Integer.parseInt(text[0]);

                if(variant > 27 || variant < 0) {
                    messageSender.sendMessage(message, "Студента з таким номером не існує");

                    return false;
                }

            } catch (Exception e) {
                messageSender.sendMessage(message, "Некоректний ввід");

                e.printStackTrace();
            }

            if (variant != 0) {
                User newUser = new User(variant, name, message.getChatId());

                User created = userService.create(newUser);

                if (created != null) {
                    messageSender.sendMessageWithKeyboard(message, "Успішно створено користувача\n" +
                            name + "\nНомер у групі: " + variant);

                    return true;
                } else {
                    messageSender.sendMessage(message, "Помилка створення користувача");
                }
            } else {
                messageSender.sendMessage(message, "Неправильно вказано номер у групі");
            }
        } else {
            messageSender.sendMessageWithKeyboard(message, "Ви уже зареєстровані");
        }

        return false;
    }
}
