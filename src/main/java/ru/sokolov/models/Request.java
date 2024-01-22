package ru.sokolov.models;

import javax.persistence.*;

@Entity
@Table(name = "queuerequests")
public class Request {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "clientId")
    private int clientId;

    @Column(name = "shop")
    private String shop;

    @Column(name = "method")
    private String method;

    @Column(name = "article")
    private String article;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getDataToChange() {
        return dataToChange;
    }

    public void setDataToChange(String dataToChange) {
        this.dataToChange = dataToChange;
    }

    public Request(int clientId, String shop, String method, String article, String dataToChange) {
        this.clientId = clientId;
        this.shop = shop;
        this.method = method;
        this.article = article;
        this.dataToChange = dataToChange;
    }

    @Column(name = "dataToChange")
    private String dataToChange;

    public Request() {
    }

    private transient String supplierArticle;

    public String getSupplierArticle() {
        return supplierArticle;
    }

    public void setSupplierArticle(String supplierArticle) {
        this.supplierArticle = supplierArticle;
    }

    private transient String price;
    private transient String discount;
    private transient String description;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
