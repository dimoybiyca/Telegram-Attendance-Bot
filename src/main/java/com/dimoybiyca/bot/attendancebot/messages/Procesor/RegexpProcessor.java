package com.dimoybiyca.bot.attendancebot.messages.Procesor;

import com.dimoybiyca.bot.attendancebot.messages.MessageSender;
import com.dimoybiyca.bot.attendancebot.util.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


@Component
public class RegexpProcessor {

    private final MessageSender messageSender;

    private final StartMessage startMessage;

    private final AdminMessage adminMessage;

    private final Notification notification;


    @Autowired
    public RegexpProcessor(MessageSender messageSender,
                           StartMessage startMessage,
                           AdminMessage adminMessage, Notification notification) {
        this.messageSender = messageSender;
        this.startMessage = startMessage;
        this.adminMessage = adminMessage;
        this.notification = notification;
    }

    public void process(Message message) {
        String NEW_REGEX = "^[0-9]{1,2}(\\s[А-ЯІ][а-яі]{1,30}){3}$";

        String JOURNAL_SUBJECT_REGEX = "^[А-ЯІ]{2,4}$";
        String JOURNAL_SUBJECT_TYPE_REGEX = "^[А-ЯІ]{2,4}\\s([А-ЯІ][а-яі]*)$";

        String NOTIFY_REGEX = "^\\/all\\s\\X+$";

        String messageText = message.getText();
        if(messageText.matches(NEW_REGEX)) {

            startMessage.createNew(message);

        } else if (messageText.matches(JOURNAL_SUBJECT_REGEX)) {

            adminMessage.getJournalBySubject(message);

        } else if (messageText.matches(JOURNAL_SUBJECT_TYPE_REGEX)) {

            adminMessage.getJournalBySubjectAndType(message);

        } else if (messageText.matches(NOTIFY_REGEX)) {

            String text = messageText.substring(5);

            notification.notifyAllUsers(text, message);
        }else {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("Некоректний ввід");
            sendMessage.setChatId(message.getChatId());

            messageSender.sendMessage(sendMessage);
        }
    }
}
