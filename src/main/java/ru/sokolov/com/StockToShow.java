package ru.sokolov.com;

import ru.sokolov.models.Stock;

import java.util.List;

public class StockToShow {
    String subject;
    String supplierArticle;
    int quantity;
    int quantityFull;
    int inWayFromClient;
    String color;
    List<Stock> stocks;

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantityFull() {
        return quantityFull;
    }

    public void setQuantityFull(int quantityFull) {
        this.quantityFull = quantityFull;
    }

    public int getInWayFromClient() {
        return inWayFromClient;
    }

    public void setInWayFromClient(int inWayFromClient) {
        this.inWayFromClient = inWayFromClient;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public StockToShow(String subject, String supplierArticle, int quantity, int quantityFull, int inWayFromClient, int color, List<Stock> stocks) {
        this.subject = subject;
        this.supplierArticle = supplierArticle;
        this.quantity = quantity;
        this.quantityFull = quantityFull;
        this.inWayFromClient = inWayFromClient;
        this.stocks = stocks;
        if (color == 0) this.color = "black"; else this.color = "white";
    }
}
