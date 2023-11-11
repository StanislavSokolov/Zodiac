package ru.sokolov.com;

public class DayToShow {
    int ordered;
    int sold;
    int cancelled;
    int profit;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String date;
    String year;
    String month;
    String day;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getOrdered() {
        return ordered;
    }

    public void setOrdered(int ordered) {
        this.ordered = ordered;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public int getCancelled() {
        return cancelled;
    }

    public void setCancelled(int cancelled) {
        this.cancelled = cancelled;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public DayToShow(int ordered, int sold, int cancelled, int profit, String date) {
        this.ordered = ordered;
        this.sold = sold;
        this.cancelled = cancelled;
        this.profit = profit;
        this.date = date;

        String[] subStr;
        String delimeter = "-"; // Разделитель
        subStr = date.split(delimeter); // Разделения строки str с помощью метода split()
        day = subStr[2];
        month = String.valueOf(Integer.parseInt(subStr[1]) - 1);
        year = subStr[0];
    }
}
