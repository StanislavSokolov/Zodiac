package ru.sokolov.com;

import ru.sokolov.models.Stock;

import java.util.*;

public class StockToShow {
    String subject;
    String supplierArticle;
    int quantity;
    int quantityFull;
    int inWayFromClient;
    String color;
    List<Stock> stocks;

    ArrayList<Stock> stocksAll;

    public boolean isCoincidence() {
        return coincidence;
    }

    public void setCoincidence(boolean coincidence) {
        this.coincidence = coincidence;
    }

    boolean coincidence = false;

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

    public void setColor(int color) {
        if (color == 0) this.color = "black"; else this.color = "white";
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public ArrayList<Stock> getStocksAll() {
        return stocksAll;
    }

    public void setStocksAll(ArrayList<Stock> stocksAll) {
        this.stocksAll = stocksAll;
    }

    public StockToShow(String subject, String supplierArticle, int quantity, int quantityFull, int inWayFromClient, int color, List<Stock> stocks) {
        this.subject = subject;
        this.supplierArticle = supplierArticle;
        this.quantity = quantity;
        this.quantityFull = quantityFull;
        this.inWayFromClient = inWayFromClient;
        this.stocks = stocks;
        this.stocksAll = new ArrayList<Stock>();
        if (color == 0) this.color = "black"; else this.color = "white";
    }
}
