package com.dimoybiyca.bot.attendancebot.messages;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageSender {

    public void sendMessage(SendMessage sendMessage);

    public void sendMessage(Message message, String text);

    public void sendMessage(long chatId, String text);

    public void sendMessageWithKeyboard(Message message, String text);

    public void sendMessageWithKeyboard(long chatId, String text);

    public void sendMessageWithKeyboardMono(Message message, String text);
}
