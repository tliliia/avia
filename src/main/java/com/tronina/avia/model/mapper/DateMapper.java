package com.tronina.avia.model.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateMapper {

    public static final DateTimeFormatter ISO_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public String asString(LocalDateTime dateTime) {
        return dateTime.format(ISO_DATE_FORMATTER);
    }

    public LocalDateTime asDate(String date) {
        return LocalDateTime.parse(date, ISO_DATE_FORMATTER);

    }
}
