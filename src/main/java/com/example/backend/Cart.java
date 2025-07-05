package com.example.backend;
import java.util.*;
public class Cart {
    private HashMap<Coffee,Integer> buyHistory;

    public Cart(){
        buyHistory = new HashMap<>();
    }

    public HashMap<Coffee, Integer> getBuyHistory() {
        return buyHistory;
    }

    public void setBuyHistory(HashMap<Coffee, Integer> buyHistory) {
        this.buyHistory = buyHistory;
    }

    public void addToCart(Coffee coffee){
        if(this.buyHistory.containsKey(coffee)){
            this.buyHistory.put(coffee,this.buyHistory.get(coffee)+1);
            return;
        }
        this.buyHistory.put(coffee,1);
    }

    public void removeFromCart(Coffee coffee){
        if(this.buyHistory.containsKey(coffee)){
            if(this.buyHistory.get(coffee)==1){
                this.buyHistory.remove(coffee);
                return;
            }
            this.buyHistory.put(coffee,this.buyHistory.get(coffee)-1);
        }
    }

    public double getTotalPrice(){
        double sum =0;
        for(Coffee j : buyHistory.keySet()) {
            sum += j.getPrice() * this.buyHistory.get(j);
        }
        return sum;
    }



}
