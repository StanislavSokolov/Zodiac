package ru.sokolov.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nameShopWB")
    private String nameShopWB;

    @Column(name = "tokenStandartWB")
    private String tokenStandartWB;

    @Column(name = "nameShopOzon")
    private String nameShopOzon;

    @Column(name = "tokenClientOzon")
    private String tokenClientOzon;

    @Column(name = "tokenStatisticOzon")
    private String tokenStatisticOzon;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public List<UserAuth> getUserAuths() {
        return userAuths;
    }

    public void setUserAuths(List<UserAuth> userAuths) {
        this.userAuths = userAuths;
    }

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Product> products;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<UserAuth> userAuths;

    //@Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}", message = "Your address should be in this format: Country, City, Postal Code (6 digits)")
//    @Column(name = "address")
//    private String address;

    public User() {

    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public User(String nameShopWB, String tokenStandartWB, String nameShopOzon, String tokenClientOzon, String tokenStatisticOzon, String email, String password) {
        this.nameShopWB = nameShopWB;
        this.tokenStandartWB = tokenStandartWB;
        this.nameShopOzon = nameShopOzon;
        this.tokenClientOzon = tokenClientOzon;
        this.tokenStatisticOzon = tokenStatisticOzon;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameShopWB() {
        return nameShopWB;
    }

    public void setNameShopWB(String nameShopWB) {
        this.nameShopWB = nameShopWB;
    }

    public String getTokenStandartWB() {
        return tokenStandartWB;
    }

    public void setTokenStandartWB(String tokenStandartWB) {
        this.tokenStandartWB = tokenStandartWB;
    }

    public String getNameShopOzon() {
        return nameShopOzon;
    }

    public void setNameShopOzon(String nameShopOzon) {
        this.nameShopOzon = nameShopOzon;
    }

    public String getTokenClientOzon() {
        return tokenClientOzon;
    }

    public void setTokenClientOzon(String tokenClientOzon) {
        this.tokenClientOzon = tokenClientOzon;
    }

    public String getTokenStatisticOzon() {
        return tokenStatisticOzon;
    }

    public void setTokenStatisticOzon(String tokenStatisticOzon) {
        this.tokenStatisticOzon = tokenStatisticOzon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Product> getProductBySupplierArticle(String supplierArticle, String shopConverter) {
        ArrayList<Product> productList = new ArrayList<>();
        for (Product p: getProducts()) {
            if (p.getSupplierArticle().equals(supplierArticle) & p.getShopName().equals(shopConverter)) {
                productList.add(p);
                return productList;
            }
        }
        return productList;
    }

    public ArrayList<Product> getProductsBySubject(String subject, String shopConverter) {
        ArrayList<Product> productList = new ArrayList<>();
        for (Product p: getProducts()) {
            if (p.getSubject().equals(subject) & p.getShopName().equals(shopConverter)) {
                productList.add(p);
            }
        }
        System.out.println(productList.size());
        return productList;
    }

    public ArrayList<Product> getProducts(String shopConverter) {
        ArrayList<Product> productList = new ArrayList<>();
        for (Product p: getProducts()) {
            if (!p.getSupplierArticle().equals("") & p.getShopName().equals(shopConverter)) {
                productList.add(p);
            }
        }
        return productList;
    }
}
