package com.dimoybiyca.bot.attendancebot.messages;

import com.dimoybiyca.bot.attendancebot.AttendanceBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

@Component
public class MessageSenderImpl implements MessageSender{

    private AttendanceBot attendanceBot;


    public void sendMessage(SendMessage sendMessage) {
        try {
            attendanceBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(message.getChatId());

        this.sendMessage(sendMessage);
    }
    public void sendMessage(long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);

        this.sendMessage(sendMessage);
    }

    public void sendMessageWithKeyboard(Message message, String text) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(message.getChatId());

        var markup = new ReplyKeyboardMarkup();
        var keyboardRows = new ArrayList<KeyboardRow>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        row1.add("✙");
        row2.add("-");

        keyboardRows.add(row1);
        keyboardRows.add(row2);

        markup.setKeyboard(keyboardRows);
        markup.setResizeKeyboard(true);

        sendMessage.setReplyMarkup(markup);

        sendMessage(sendMessage);
    }

    public void sendMessageWithKeyboard(long chatId, String text) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);

        var markup = new ReplyKeyboardMarkup();
        var keyboardRows = new ArrayList<KeyboardRow>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        row1.add("✙");
        row2.add("-");

        keyboardRows.add(row1);
        keyboardRows.add(row2);

        markup.setKeyboard(keyboardRows);
        markup.setResizeKeyboard(true);

        sendMessage.setReplyMarkup(markup);

        sendMessage(sendMessage);
    }

    @Override
    public void sendMessageWithKeyboardMono(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setParseMode("HTML");
        sendMessage.setChatId(message.getChatId());

        var markup = new ReplyKeyboardMarkup();
        var keyboardRows = new ArrayList<KeyboardRow>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        row1.add("✙");
        row2.add("-");

        keyboardRows.add(row1);
        keyboardRows.add(row2);

        markup.setKeyboard(keyboardRows);
        markup.setResizeKeyboard(true);

        sendMessage.setReplyMarkup(markup);

        sendMessage(sendMessage);
    }


    @Autowired
    public void setAttendanceBot(AttendanceBot attendanceBot) {
        this.attendanceBot = attendanceBot;
    }
}
