package com.dimoybiyca.bot.attendancebot.messages.Procesor;

import com.dimoybiyca.bot.attendancebot.messages.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


@Component
public class RegexpProcessor {

    private final MessageSender messageSender;

    private final StartMessage startMessage;

    @Autowired
    public RegexpProcessor(MessageSender messageSender, StartMessage startMessage) {
        this.messageSender = messageSender;
        this.startMessage = startMessage;
    }

    public void process(Message message) {
        String NEW_REGEX = "^[0-9]{1,2}(\\s[А-ЯІ][а-яі]{1,30}){3}$";
        if(message.getText().matches(NEW_REGEX)) {

            startMessage.createNew(message);
        } else {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("Некоректний ввід");
            sendMessage.setChatId(message.getChatId());

            messageSender.sendMessage(sendMessage);
        }
    }
}
