package ru.sokolov.com;

public class StockToShow {
    String subject;
    String supplierArticle;
    int quantity;
    int quantityFull;

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

    public StockToShow(String subject, String supplierArticle, int quantity, int quantityFull) {
        this.subject = subject;
        this.supplierArticle = supplierArticle;
        this.quantity = quantity;
        this.quantityFull = quantityFull;
    }
}
