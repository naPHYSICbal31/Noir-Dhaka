package com.example.noir;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 *
 * */


public class Review {
    private double stars;
    private Coffee coffee;
    private String description;
    private boolean isVerified;
    private String title;
    private LocalDateTime timestamp;
    private int userid;

    public Review( int userid, double stars, Coffee coffee, String description, LocalDateTime timestamp, boolean isVerified) {
        this.stars = stars;
        this.coffee = coffee;
        this.description = description;
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
//        this.timestamp = LocalDateTime.parse(timestamp, formatter);
        this.timestamp = timestamp;
        this.userid = userid;
        this.isVerified = isVerified;
    }
    public Review(String title,String description,User user,int stars,boolean isVerified){
        this.title = title;
        this.description = description;
        this.stars = stars;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public LocalDateTime getTimestamp() {
        //DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        this.timestamp = LocalDateTime.parse(timestamp, formatter);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Coffee getCoffee() {
        return coffee;
    }

    public void setCoffee(Coffee coffee) {
        this.coffee = coffee;
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}