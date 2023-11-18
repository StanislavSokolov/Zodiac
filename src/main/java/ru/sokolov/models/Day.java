package ru.sokolov.models;

import javax.persistence.*;

@Entity
@Table(name = "day")
public class Day {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "cdate")
    private String cdate;

    @Column(name = "ordered")
    private int ordered;

    @Column(name = "sold")
    private int sold;

    @Column(name = "cancelled")
    private int cancelled;

    public Day(String cdate, int ordered, int sold, int cancelled, int profit) {
        this.cdate = cdate;
        this.ordered = ordered;
        this.sold = sold;
        this.cancelled = cancelled;
        this.profit = profit;
    }

    @Column(name = "profit")
    private int profit;

    public Day() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
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
}
