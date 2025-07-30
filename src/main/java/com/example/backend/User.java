package com.example.backend;
import java.io.Serializable;
import java.security.Timestamp;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;
import java.util.Map;
import org.mindrot.jbcrypt.BCrypt;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

public class User implements Serializable {
    private String username;
    private int userid;
    private String email;


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", userid=" + userid +
                ", email='" + email + '\'' +
                ", passhash='" + passhash + '\'' +
                ", address='" + address + '\'' +
                ", isAds=" + isAds +
                ", buyHistory=" + buyHistory +
                ", buyTime=" + buyTime +
                ", recipts=" + recipts +
                '}';
    }

    private String passhash; //sha256
    private String address;

    public HashMap<Integer, LocalDateTime> getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(HashMap<Integer, LocalDateTime> buyTime) {
        this.buyTime = buyTime;
    }

    static int usercount = 0;
    //settings
    private boolean isAds;
    //history
    private HashMap<Integer,Integer> buyHistory;
    private HashMap<Integer, LocalDateTime> buyTime;
    private HashMap<String, LocalDateTime> recipts;

    public User(String username,String pass, String email,  String address, boolean isAds) {
        this.buyHistory = new HashMap<>();
        this.buyTime = new HashMap<>();
        this.recipts = new HashMap<>();
        this.isAds = isAds;
        this.address = address;
        this.setPass(pass);
        this.email = email;
        //this.userid = 10000 + ++usercount;
        this.username = username;
    }

    public User(String username, int userid, String email, String address, boolean isAds, HashMap<Integer, Integer> buyHistory, HashMap<Integer, LocalDateTime> buyTime, HashMap<String, LocalDateTime> recipts) {
        this.username = username;
        this.userid = userid;
        this.email = email;
        this.recipts = recipts;
        //this.passhash = passhash;
        this.address = address;
        this.isAds = isAds;
        this.buyHistory = buyHistory;
        this.buyTime = buyTime;
    }


    public HashMap<String, LocalDateTime> getRecipts() {
        return recipts;
    }

    public void setRecipts(HashMap<String, LocalDateTime> recipts) {
        this.recipts = recipts;
    }

    public void addToRecipts(String rec){
        this.recipts.put(rec, LocalDateTime.now());
    }

    private static String hashSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());


            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = String.format("%02x", b);
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found");
        }
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
        return hashSHA256(pass);
    }

    public HashMap<Integer, Integer> getBuyHistory() {
        return buyHistory;
    }

    public void setBuyHistory(HashMap<Integer, Integer> buyHistory) {
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



    /*
    *  FUNCTIONS OTHER THAN GETTER SETTER
    * */

    public void addBuyHistoryFromCart(Cart c){

        HashMap<Integer, Integer> j = c.getBuyHistory();

        for(Map.Entry<Integer, Integer> entry : j.entrySet()){
            if(this.buyHistory.containsKey(entry.getKey())){
                this.buyHistory.replace(entry.getKey(), this.buyHistory.get(entry.getKey())+entry.getValue());
            }else{
                this.buyHistory.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public void addTimeHistoryFromCart(Cart c){

        HashMap<Integer, Integer> j = c.getBuyHistory();

        for(Map.Entry<Integer, Integer> entry : j.entrySet()){

            this.buyTime.put(entry.getKey(), LocalDateTime.now());

        }
    }

    public int getUserId() {
        return userid;
    }

}