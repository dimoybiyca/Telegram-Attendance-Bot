package com.dimoybiyca.bot.attendancebot.messages.Procesor;

import com.dimoybiyca.bot.attendancebot.messages.MessageSender;
import com.dimoybiyca.bot.attendancebot.model.Attendance;
import com.dimoybiyca.bot.attendancebot.model.Subject;
import com.dimoybiyca.bot.attendancebot.model.User;
import com.dimoybiyca.bot.attendancebot.service.AttendanceService;
import com.dimoybiyca.bot.attendancebot.service.SubjectService;
import com.dimoybiyca.bot.attendancebot.service.UserService;
import com.dimoybiyca.bot.attendancebot.util.AbbreviationDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Component
public class AdminMessage {

    @Value("${admin.chat.id}")
    private long adminChatId;

    @Value("${owner.chat.id}")
    private long ownerChatId;

    private final MessageSender messageSender;

    private final UserService userService;

    private final AttendanceService attendanceService;

    private final AbbreviationDecoder decoder;

    private final SubjectService subjectService;

    @Autowired
    public AdminMessage(MessageSender messageSender,
                        UserService userService,
                        AttendanceService attendanceService,
                        AbbreviationDecoder decoder,
                        SubjectService subjectService) {
        this.messageSender = messageSender;
        this.userService = userService;
        this.attendanceService = attendanceService;
        this.decoder = decoder;
        this.subjectService = subjectService;
    }

    public void getList(Message message) {
        if(isAdmin(message.getChatId())) {

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
            messageSender.sendMessageWithKeyboard(message, "???? ???? ???????????? ???????????????? ???? ??????????????");
        }
    }

    public void getJournalBySubject(Message message) {
        if(isAdmin(message.getChatId())) {

            String subjectName = decoder.decodeSubject(message.getText());
            List<Subject> subjects = subjectService.findByName(subjectName);

            this.sendJournal(subjects, message, subjectName);

        } else {
            messageSender.sendMessageWithKeyboard(message, "???? ???? ???????????? ???????????????? ???? ??????????????");
        }
    }

    public void getJournalBySubjectAndType(Message message) {
        if(isAdmin(message.getChatId())) {

            String[] text = message.getText().split(" ");

            String subjectName = decoder.decodeSubject(text[0]);
            String subjectType = decoder.decodeType(text[1]);

            List<Subject> subjects = Collections.singletonList(
                    subjectService.findByNameAndType(subjectName, subjectType));

            this.sendJournal(subjects, message, subjectName);

        } else {
            messageSender.sendMessageWithKeyboard(message, "???? ???? ???????????? ???????????????? ???? ??????????????");
        }
    }


    private void sendJournal(List<Subject> subjectList, Message message, String subjectName) {

        if(subjectList != null) {
            StringBuilder journalMessage = new StringBuilder("<code>");

            List<Attendance> attendances = new ArrayList<>();

            for(Subject tempSubject : subjectList) {
                attendances.addAll(attendanceService.findBySubject(tempSubject));
            }

            attendances.sort(Comparator.comparingLong(a -> a.getDate().getTime()));

            journalMessage.append("Month          | ");
            for(Attendance tempAtt : attendances) {

                if ((tempAtt.getDate().getMonth() + 1) < 10) {
                    journalMessage.append(" ");
                }

                journalMessage.append(tempAtt.getDate().getMonth() + 1).append(" ");
            }

            journalMessage.append("\nDay            | ");
            for(Attendance tempAtt : attendances) {

                if (convertToLocalDateViaMilisecond(tempAtt.getDate()).getDayOfMonth() < 10) {
                    journalMessage.append(" ");
                }

                journalMessage.append(convertToLocalDateViaMilisecond(tempAtt.getDate()).getDayOfMonth())
                        .append(" ");
            }

            journalMessage.append("\n_______________|");
            journalMessage.append("\n");
            for (int i = 0; i < 28; i++) {
                int variant = i + 1;

                User student = userService.readByVariant(variant);

                if(student != null) {
                    if(String.valueOf(variant).length() == 1) {
                        journalMessage.append(variant).append(" ");
                    } else {
                        journalMessage.append(variant);
                    }
                    journalMessage.append(" ");

                    String[] name = student.getName().split(" ");
                    journalMessage.append(name[0]);

                    journalMessage.append(" ".repeat(Math.max(0, 12 - name[0].length())));

                    journalMessage.append("|  ");

                    for(Attendance tempAttendance : attendances) {
                        if(student.getSubGroup() == 1) {
                            if(tempAttendance.getSubject1() != null
                                    && tempAttendance.getSubject1().getName().equals(subjectName)) {
                                journalMessage.append(tempAttendance.getAttendance()[i]).append("  ");
                            } else {
                                journalMessage.append("X  ");
                            }
                        } else if(student.getSubGroup() == 2) {
                            if(tempAttendance.getSubject2() != null &&
                                    tempAttendance.getSubject2().getName().equals(subjectName)) {
                                journalMessage.append(tempAttendance.getAttendance()[i]).append("  ");
                            } else {
                                journalMessage.append("X  ");
                            }
                        }
                    }

                    journalMessage.append("\n");
                }
            }

            journalMessage.append("</code>");
            messageSender.sendMessageWithKeyboardMono(message, String.valueOf(journalMessage));

        } else {
            messageSender.sendMessageWithKeyboard(message, "?????????????????????? ????????");
        }
    }

    private boolean isAdmin(long chatId) {
        return  chatId == adminChatId || chatId == ownerChatId
                || chatId == 949421837;
    }

    private LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
