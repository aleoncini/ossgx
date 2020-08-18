package org.ossg.store.model;

import java.util.Calendar;
import java.util.Objects;

public class DayOfEvent {
    private int year;
    private int month;
    private int day;

    public DayOfEvent today() {
        this.day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        this.month = Calendar.getInstance().get(Calendar.MONTH) +1;
        this.year = Calendar.getInstance().get(Calendar.YEAR);
        return this;
    }

    public int getYear() {
        return year;
    }

    public DayOfEvent setYear(int year) {
        this.year = year;
        return this;
    }

    public int getMonth() {
        return month;
    }

    public DayOfEvent setMonth(int month) {
        this.month = month;
        return this;
    }

    public int getDay() {
        return day;
    }

    public DayOfEvent setDay(int day) {
        this.day = day;
        return this;
    }

    public boolean isEqual(DayOfEvent other){
        return ((this.year == other.year) && (this.month == other.month) && (this.day == other.day));
    }

    @Override
    public String toString() {
        return new StringBuffer().append(day).append(" ").append(getMonthName()).append(" ").append(year).toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DayOfEvent)) {
            return false;
        }

        DayOfEvent other = (DayOfEvent) obj;

        return Objects.equals(other.day, this.day)
        && (Objects.equals(other.month, this.month))
        && (Objects.equals(other.year, this.year));
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }

    private String getMonthName() {
        switch(month) {
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            default:
                return "Dic";
        }
    }

}