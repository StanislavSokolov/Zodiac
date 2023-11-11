package ru.sokolov.models;

import javax.persistence.*;

@Entity
@Table(name = "year2023")
public class Year {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "cdate")
    private String cdate;

    @Column(name = "csum")
    private int csum;

    @Column(name = "sales")
    private int sales;

    public int getId() {
        return id;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
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

    public int getCsum() {
        return csum;
    }

    public void setCsum(int csum) {
        this.csum = csum;
    }

    public Year(String cdate, int csum, int sales) {
        this.cdate = cdate;
        this.csum = csum;
        this.sales = sales;
    }

    public Year() {
    }
}
