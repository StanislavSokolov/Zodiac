package ru.sokolov.models;

import javax.persistence.*;

@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "warehouseName")
    private String warehouseName;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "quantityFull")
    private int quantityFull;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product owner;

    public Stock() {
    }

    public Stock(String warehouseName, int quantity, int quantityFull, Product owner) {
        this.warehouseName = warehouseName;
        this.quantity = quantity;
        this.quantityFull = quantityFull;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getOwner() {
        return owner;
    }

    public void setOwner(Product owner) {
        this.owner = owner;
    }
}


