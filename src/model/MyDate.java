package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MyDate extends Number {

    protected LocalDate date;

    public MyDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public int intValue() {
        return date.getDayOfYear() +365*(date.getYear()-2018);
    }

    @Override
    public long longValue() {
        return date.getDayOfYear() +365*(date.getYear()-2018);
    }

    @Override
    public float floatValue() {
        return date.getDayOfYear() +365*(date.getYear()-2018);
    }

    @Override
    public double doubleValue() {
        return date.getDayOfYear() +365*(date.getYear()-2018);
    }

    public LocalDate getDate() {
        return date;
    }
}
