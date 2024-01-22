package ru.sokolov.models;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.sokolov.com.Data;
import ru.sokolov.com.DayToShow;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "supplierArticle")
    private String supplierArticle;
    //    @Column(name = "quantity")
//    private String quantity;
//    @Column(name = "quantityFull")
//    private String quantityFull;
    @Column(name = "nmId")
    private String nmId;
    @Column(name = "subject")
    private String subject;
    //    @Column(name = "warehouseName")
//    private String warehouseName;
    @Column(name = "price")
    private int price;
    @Column(name = "discount")
    private int discount;
    @Column(name = "shopName")
    private String shopName;

    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    private String rating;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Stock> stocks;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Item> items;

    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Media> medias;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User owner;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    private transient ArrayList<DayToShow> dayToShows;

    private transient int soldAllFromLastPeriod;

    private transient int orderedAllFromLastPeriod;

    public ArrayList<DayToShow> getDayToShows() {
            if (dayToShows == null) dayToShows = new ArrayList<>();
        return dayToShows;
    }

    public void setDayToShows(ArrayList<DayToShow> dayToShows) {
        this.dayToShows = dayToShows;
    }

    public Product() {
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Product(String supplierArticle, String nmId, String subject, int price, int discount, String shopName, String description, String rating, User owner) {
        this.supplierArticle = supplierArticle;
        this.nmId = nmId;
        this.subject = subject;
        this.price = price;
        this.discount = discount;
        this.shopName = shopName;
        this.description = description;
        this.rating = rating;
        this.owner = owner;
//        dayToShows = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSupplierArticle() {
        return supplierArticle;
    }

    public void setSupplierArticle(String supplierArticle) {
        this.supplierArticle = supplierArticle;
    }

    public String getNmId() {
        return nmId;
    }

    public void setNmId(String nmId) {
        this.nmId = nmId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public List<Item> getItems() {
        return items;
    }

    public int getItemsOrdered(String date) {
        int count = 0;
        for (Item i: items) {
            if (i.getCdate().equals(date)) count++;
        }
        return count;
    }

    public int getItemsSold(String date) {
        int count = 0;
        for (Item i: items) {
            if (i.getSdate().equals(date)) count++;
        }
        return count;
    }

    public void setItemsOrderedAllFromLastPeriod(int lastPeriod) {
        int sum = 0;
        for (int j = lastPeriod; j < 1; j++) {
            for (Item i: items) {
                if (i.getCdate().equals(Data.getData(j))) sum++;
            }
        }
        orderedAllFromLastPeriod = sum;
    }

    public void setItemsSoldAllFromLastPeriod(int lastPeriod) {
        int sum = 0;
        for (int j = lastPeriod; j < 1; j++) {
            for (Item i: items) {
                if (i.getSdate().equals(Data.getData(j))) sum++;
            }
        }
        soldAllFromLastPeriod = sum;
    }

    public int getItemsOrderedAllFromLastPeriod(){
        return orderedAllFromLastPeriod;
    }

    public int getItemsSoldAllFromLastPeriod() {
        return soldAllFromLastPeriod;
    }



    public void setItems(List<Item> items) {
        this.items = items;
    }
}


