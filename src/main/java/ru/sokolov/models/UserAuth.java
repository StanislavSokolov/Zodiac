package ru.sokolov.models;

import javax.persistence.*;

@Entity
@Table(name = "userauth")
public class UserAuth {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "authorization")
    private String authorization;

    @Column(name = "device")
    private String device;

    @Column(name = "ip")
    private String ip;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User owner;

    public UserAuth() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public UserAuth(String authorization, String device, String ip, User owner) {
        this.authorization = authorization;
        this.device = device;
        this.ip = ip;
        this.owner = owner;
    }
}
