package com.betfair.logistics.service;

import com.betfair.logistics.exception.InvalidDateForOrderSearchException;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@Getter
public class CompanyInfo {

    public static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE;
    public static Long MILLIS_IN_A_DAY = 24 * 3600 * 1000L;

    private Long currentDate = LocalDate.of(2021, 12, 14).toEpochDay() * MILLIS_IN_A_DAY;
    private Long companyProfit = 0L;

    public Long advanceCurrentDate() {
        currentDate += MILLIS_IN_A_DAY;
        return currentDate;
    }

    public void increaseProfit(Long delta) {
        companyProfit += delta;
    }

    public String getCurrentDateAsString() {
        return LocalDate.ofEpochDay(currentDate / MILLIS_IN_A_DAY).format(DATE_FORMATTER);
    }

    public Long getDateAsLongFromString(String dateString) throws InvalidDateForOrderSearchException {
        try {
            return LocalDate.parse(dateString, DATE_FORMATTER).toEpochDay() * MILLIS_IN_A_DAY;
        } catch (DateTimeException e) {
            throw new InvalidDateForOrderSearchException("Date format invalid! Expected yyyy-MM-dd");
        }
    }

}
