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
        return date.getDayOfYear();
    }

    @Override
    public long longValue() {
        return date.getDayOfYear();
    }

    @Override
    public float floatValue() {
        return date.getDayOfYear();
    }

    @Override
    public double doubleValue() {return date.getDayOfYear();
    }

    public LocalDate getDate() {
        return date;
    }
}
