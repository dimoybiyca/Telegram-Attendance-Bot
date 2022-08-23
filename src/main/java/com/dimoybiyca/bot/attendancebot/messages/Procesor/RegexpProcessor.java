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

    private final AdminMessage adminMessage;

    @Autowired
    public RegexpProcessor(MessageSender messageSender,
                           StartMessage startMessage,
                           AdminMessage adminMessage) {
        this.messageSender = messageSender;
        this.startMessage = startMessage;
        this.adminMessage = adminMessage;
    }

    public void process(Message message) {
        String NEW_REGEX = "^[0-9]{1,2}(\\s[А-ЯІ][а-яі]{1,30}){3}$";

        String JOURNAL_SUBJECT_REGEX = "^[А-ЯІ]{2,4}$";
        String JOURNAL_SUBJECT_TYPE_REGEX = "^[А-ЯІ]{2,4}\\s([А-ЯІ][а-яі]*)$";

        String messageText = message.getText();
        if(messageText.matches(NEW_REGEX)) {

            startMessage.createNew(message);
        } else if (messageText.matches(JOURNAL_SUBJECT_REGEX)) {
            adminMessage.getJournalBySubject(message);

        } else if (messageText.matches(JOURNAL_SUBJECT_TYPE_REGEX)) {


        } else {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("Некоректний ввід");
            sendMessage.setChatId(message.getChatId());

            messageSender.sendMessage(sendMessage);
        }
    }
}
