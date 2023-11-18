package ru.sokolov.com;

import ru.sokolov.models.Stock;

import java.util.ArrayList;
import java.util.List;

public class ItemToShow {
    String subject;
    String supplierArticle;
    int ordered;
    int sold;
    int cancelled;
    String color;
    ArrayList<WareHouse> wareHouses;

    ArrayList<WareHouse> wareHousesAll;

    public boolean isCoincidence() {
        return coincidence;
    }

    public void setCoincidence(boolean coincidence) {
        this.coincidence = coincidence;
    }

    boolean coincidence = false;

    public  ArrayList<WareHouse> getWareHouses() {
        return wareHouses;
    }

    public void setWareHouses(ArrayList<WareHouse> wareHouses) {
        this.wareHouses = wareHouses;
    }

    public ArrayList<WareHouse> getWareHousesAll() {
        return wareHousesAll;
    }

    public void setWareHousesAll(ArrayList<WareHouse> wareHousesAll) {
        this.wareHousesAll = wareHousesAll;
    }

    public String getColor() {
        return color;
    }

    public void setColor(int color) {
        if (color == 0) this.color = "black"; else this.color = "white";
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSupplierArticle() {
        return supplierArticle;
    }

    public void setSupplierArticle(String supplierArticle) {
        this.supplierArticle = supplierArticle;
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

    public ItemToShow(String subject, ArrayList<WareHouse> wareHousesAll) {
        this.subject = subject;
        this.wareHousesAll = wareHousesAll;
    }

    public ItemToShow(String subject, String supplierArticle, int ordered, int sold, int cancelled, int color, ArrayList<WareHouse> wareHouses) {
        this.subject = subject;
        this.supplierArticle = supplierArticle;
        this.ordered = ordered;
        this.sold = sold;
        this.cancelled = cancelled;
        this.wareHouses = wareHouses;
//        this.wareHouses = new ArrayList<>();
        if (color == 0) this.color = "black"; else this.color = "white";
    }
}
