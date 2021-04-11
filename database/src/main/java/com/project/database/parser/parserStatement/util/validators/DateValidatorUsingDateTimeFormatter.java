package com.project.database.parser.parserStatement.util.validators;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidatorUsingDateTimeFormatter implements DateValidator {
    private final DateTimeFormatter dateFormatter;

    public DateValidatorUsingDateTimeFormatter(DateTimeFormatter dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    @Override
    public boolean isValid(String dateStr) {
        try {
            this.dateFormatter.parse(dateStr);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
