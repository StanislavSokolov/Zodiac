package ru.sokolov.com;

public class StockToShow {
    String subject;
    String supplierArticle;
    int quantity;
    int quantityFull;
    int inWayFromClient;
    String color;

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

    public StockToShow(String subject, String supplierArticle, int quantity, int quantityFull, int inWayFromClient, int color) {
        this.subject = subject;
        this.supplierArticle = supplierArticle;
        this.quantity = quantity;
        this.quantityFull = quantityFull;
        this.inWayFromClient = inWayFromClient;
        if (color == 0) this.color = "black"; else this.color = "white";
    }
}
