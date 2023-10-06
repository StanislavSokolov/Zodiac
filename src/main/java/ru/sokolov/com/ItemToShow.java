package ru.sokolov.com;

public class ItemToShow {
    String subject;
    String supplierArticle;
    int ordered;
    int sold;
    int cancelled;

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

    public ItemToShow(String subject, String supplierArticle, int ordered, int sold, int cancelled) {
        this.subject = subject;
        this.supplierArticle = supplierArticle;
        this.ordered = ordered;
        this.sold = sold;
        this.cancelled = cancelled;
    }
}
