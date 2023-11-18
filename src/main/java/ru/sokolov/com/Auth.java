package ru.sokolov.com;

import ru.sokolov.models.User;

import java.util.ArrayList;

public class Auth {
    public static boolean getAuthorization(String authorization, String client) {
        if (authorization != null) {
            if (authorization.equals("true")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static ArrayList<String> getShops(User userDB) {
        ArrayList<String> shops = new ArrayList<>();
        if ((userDB.getTokenClientOzon() != null) & (userDB.getTokenStatisticOzon() != null)) shops.add("Ozon");
        if ((userDB.getTokenStandartWB() != null) & (userDB.getTokenStatisticWB() != null) & (userDB.getTokenAdvertisingWB() != null)) shops.add("Wildberries");
        return shops;
    }

}
