package com.example.backend;
import com.example.backend.Client;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;

import static com.example.noir.HelloApplication.client;

//import static com.example.backend.Client.currentToken;


public class Cart extends Element implements Serializable {
    public static int numberOfCarts = 0;
    private int id;
    private String token;
    private HashMap<Integer,Integer> buyHistory;
    private String timestamp;// coffeeId, count

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Cart(String currentToken){
        id = ++numberOfCarts;
        token = currentToken;
        buyHistory = new HashMap<>();
        timestamp = Instant.now().toString();
    }
    public Cart(int coffeeid, int count, String currentToken){
        id = ++numberOfCarts;
        token = currentToken;
        buyHistory = new HashMap<>();
        timestamp = Instant.now().toString();
        buyHistory.put(coffeeid, count);
    }

    public int getId() {
        return id;
    }

    
    public void setId(int id) {
        this.id = id;
    }

    public Cart(HashMap<Integer,Integer> buyHistory, String currentToken){
        id = ++numberOfCarts;
        token = currentToken;
        this.buyHistory = buyHistory;
    }

    public HashMap<Integer, Integer> getBuyHistory() {
        return buyHistory;
    }


    public void setBuyHistory(HashMap<Integer, Integer> buyHistory) {
        this.buyHistory = buyHistory;
    }

    public void addToCart(Integer coffeeid, int count){
        if(this.buyHistory.containsKey(coffeeid)){
            this.buyHistory.put(coffeeid,this.buyHistory.get(coffeeid)+count);
            return;
        }
        this.buyHistory.put(coffeeid,count);
    }

    public void removeFromCart(Integer coffeeid){
        if(this.buyHistory.containsKey(coffeeid)){
            if(this.buyHistory.get(coffeeid)==1){
                this.buyHistory.remove(coffeeid);
                return;
            }
            this.buyHistory.put(coffeeid,this.buyHistory.get(coffeeid)-1);
        }
    }

    public void removeFromCartEntirely(Integer coffeeid){
        this.buyHistory.remove(coffeeid);
    }


    public double getTotalPrice(){
        double sum =0;

        for(Integer j : buyHistory.keySet()) {
            sum += client.getCoffeeById(j).getPrice() * this.buyHistory.get(j);
        }
        return sum;
    }



}
