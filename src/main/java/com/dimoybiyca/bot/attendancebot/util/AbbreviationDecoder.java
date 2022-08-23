package com.dimoybiyca.bot.attendancebot.util;

import org.springframework.stereotype.Component;

@Component
public class AbbreviationDecoder {

    public String decodeSubject(String abbreviation) {
        if(abbreviation.equals("СП")) {
            return "Системне програмування";
        } else if (abbreviation.equals("АК")) {
            return "Архітектура комп'ютерів";
        } else {
            return null;
        }
    }

    public String decodeType(String abbreviation) {
        if(abbreviation.equals("Л")) {
            return "Лекція";
        } else if (abbreviation.equals("Лаб") || abbreviation.equals("Лб")) {
            return "Лабораторна";
        } else if (abbreviation.equals("П")) {
            return "Практична";
        } else {
            return null;
        }
    }
}
