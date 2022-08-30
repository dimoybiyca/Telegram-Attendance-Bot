package com.dimoybiyca.bot.attendancebot.util;

import org.springframework.stereotype.Component;

@Component
public class AbbreviationDecoder {

    public String decodeSubject(String abbreviation) {
        return switch (abbreviation) {
            case "СП" -> "Системне програмування";
            case "АК" -> "Архітектура комп'ютерів";
            case "РК" -> "Реконфігуровані комп'ютери";
            case "ІПЗ" -> "Інженерія програмного забезпечення";
            default -> null;
        };
    }

    public String decodeType(String abbreviation) {
        return switch (abbreviation) {
            case "Л" -> "Лекція";
            case "Лаб", "Лб" -> "Лабораторна";
            case "П" -> "Практична";
            default -> null;
        };
    }
}
