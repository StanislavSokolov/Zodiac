package ru.sokolov.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import javax.persistence.*;

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
    private int tokenStandartWB;

    @Column(name = "tokenStatisticWB")
    private int tokenStatisticWB;

    @Column(name = "tokenAdvertisingWB")
    private int tokenAdvertisingWB;

    @Column(name = "nameShopOzon")
    private String nameShopOzon;

    @Column(name = "tokenClientOzon")
    private String tokenClientOzon;

    @Column(name = "tokenStatisticOzon")
    private String tokenStatisticOzon;

    @Column(name = "login")
    private String login;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    //@Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}", message = "Your address should be in this format: Country, City, Postal Code (6 digits)")
//    @Column(name = "address")
//    private String address;

    public User() {

    }

    public User(String nameShopWB, int tokenStandartWB, int tokenStatisticWB, int tokenAdvertisingWB, String nameShopOzon, String tokenClientOzon, String tokenStatisticOzon, String login, String email, String password) {
        this.nameShopWB = nameShopWB;
        this.tokenStandartWB = tokenStandartWB;
        this.tokenStatisticWB = tokenStatisticWB;
        this.tokenAdvertisingWB = tokenAdvertisingWB;
        this.nameShopOzon = nameShopOzon;
        this.tokenClientOzon = tokenClientOzon;
        this.tokenStatisticOzon = tokenStatisticOzon;
        this.login = login;
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

    public int getTokenStandartWB() {
        return tokenStandartWB;
    }

    public void setTokenStandartWB(int tokenStandartWB) {
        this.tokenStandartWB = tokenStandartWB;
    }

    public int getTokenStatisticWB() {
        return tokenStatisticWB;
    }

    public void setTokenStatisticWB(int tokenStatisticWB) {
        this.tokenStatisticWB = tokenStatisticWB;
    }

    public int getTokenAdvertisingWB() {
        return tokenAdvertisingWB;
    }

    public void setTokenAdvertisingWB(int tokenAdvertisingWB) {
        this.tokenAdvertisingWB = tokenAdvertisingWB;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
}
