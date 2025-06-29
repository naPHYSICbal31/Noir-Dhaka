package com.example.noir;
import java.util.Vector;
import java.util.Map;

public class User {
    private String username;
    private int userid;
    private String email;
    private String passhash;  //sha256
    private String address;
    static int usercount = 0;
    User(String username)
    {
        this.username = username;
        this.userid = 10000 + ++usercount;
    }
    //settings
    private boolean isAds;
    //history
    private Map.Entry<Vector<Coffee>,Integer> buyHistory;


}