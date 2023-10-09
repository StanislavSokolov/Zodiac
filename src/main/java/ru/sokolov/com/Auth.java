package ru.sokolov.com;

public class Auth {
    public static boolean authorization(String authorization, String client) {
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
}
