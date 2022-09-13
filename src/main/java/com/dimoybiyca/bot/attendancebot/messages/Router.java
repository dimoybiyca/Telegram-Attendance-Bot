package com.dimoybiyca.bot.attendancebot.messages;

import com.dimoybiyca.bot.attendancebot.messages.Procesor.AdminMessage;
import com.dimoybiyca.bot.attendancebot.messages.Procesor.AttendanceMessage;
import com.dimoybiyca.bot.attendancebot.messages.Procesor.RegexpProcessor;
import com.dimoybiyca.bot.attendancebot.messages.Procesor.StartMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class Router {

    private final StartMessage startMessage;

    private final RegexpProcessor regexpProcessor;

    private final AttendanceMessage attendanceMessage;

    private final AdminMessage adminMessage;

    @Autowired
    public Router(StartMessage startMessage,
                  RegexpProcessor regexpProcessor,
                  AttendanceMessage attendanceMessage,
                  AdminMessage adminMessage) {
        this.startMessage = startMessage;
        this.regexpProcessor = regexpProcessor;
        this.attendanceMessage = attendanceMessage;
        this.adminMessage = adminMessage;
    }


    public void route(Message message) {
        System.out.println(message.getText());

        switch (message.getText()) {
            case "/start" -> startMessage.startMes(message);
            case "✙", "+" -> attendanceMessage.setAttend(message, 1);
            case "-" -> attendanceMessage.setAttend(message, 0);
            case "/journal" -> adminMessage.getList(message);
            default -> regexpProcessor.process(message);
        }
    }
}
