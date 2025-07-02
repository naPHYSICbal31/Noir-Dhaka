package com.example.backend;
import java.util.HashMap;
import java.util.Vector;
import java.util.Map;
import org.mindrot.jbcrypt.BCrypt;

public class User {
    private String username;
    private int userid;
    private String email;



    private String passhash;  //sha256
    private String address;
    static int usercount = 0;
    //settings
    private boolean isAds;
    //history
    private HashMap<Vector<Coffee>,Integer> buyHistory;

    public User(String username,String pass, String email,  String address, boolean isAds) {
        this.buyHistory = new HashMap<Vector<Coffee>,Integer>();
        this.isAds = isAds;
        this.address = address;
        this.setPass(pass);
        this.email = email;
        this.userid = 10000 + ++usercount;
        this.username = username;
    }

    public User(String username, int userid, String email, String address, boolean isAds, HashMap<Vector<Coffee>, Integer> buyHistory) {
        this.username = username;
        this.userid = userid;
        this.email = email;
        //this.passhash = passhash;
        this.address = address;
        this.isAds = isAds;
        this.buyHistory = buyHistory;
    }

    public User(String username)
    {
        this.username = username;
        this.userid = 10000 + ++usercount;
    }

    public void setPass(String password) {

        this.passhash = hashpass(password);
    }

    public static String hashpass(String pass) {
        return BCrypt.hashpw(pass, BCrypt.gensalt());
    }

    public HashMap<Vector<Coffee>, Integer> getBuyHistory() {
        return buyHistory;
    }

    public void setBuyHistory(HashMap<Vector<Coffee>, Integer> buyHistory) {
        this.buyHistory = buyHistory;
    }

    public boolean isAds() {
        return isAds;
    }

    public void setAds(boolean ads) {
        isAds = ads;
    }

    public static int getUsercount() {
        return usercount;
    }

    public static void setUsercount(int usercount) {
        User.usercount = usercount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPasshash() {
        return passhash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}