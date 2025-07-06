package com.example.backend;
import java.time.Instant;
import java.util.*;

import static com.example.backend.dbFetch.currentToken;


public class Cart {
    private String token;
    private HashMap<Integer,Integer> buyHistory;
    private String timestamp;// coffeeId, count

    public Cart(){
        token = currentToken;
        buyHistory = new HashMap<>();
        timestamp = Instant.now().toString();
    }

    public Cart(HashMap<Integer,Integer> buyHistory){
        token = currentToken;
        this.buyHistory = buyHistory;
    }

    public HashMap<Integer, Integer> getBuyHistory() {
        return buyHistory;
    }


    public void setBuyHistory(HashMap<Integer, Integer> buyHistory) {
        this.buyHistory = buyHistory;
    }

    public void addToCart(Integer coffeeid){
        if(this.buyHistory.containsKey(coffeeid)){
            this.buyHistory.put(coffeeid,this.buyHistory.get(coffeeid)+1);
            return;
        }
        this.buyHistory.put(coffeeid,1);
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

    public double getTotalPrice(){
        double sum =0;
        dbFetch auth =  new dbFetch();
        for(Integer j : buyHistory.keySet()) {
            sum += auth.getCoffeeById(j).getPrice() * this.buyHistory.get(j);
        }
        return sum;
    }



}
