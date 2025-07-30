package com.example.backend;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/*
 *
 * */


public class Review implements Serializable {
    private static final long serialVersionUID = 4505793262472345332L;
    private int reviewid;
    private double stars;
    private int coffeeid;
    private String description;
    private boolean isVerified;
    private String title;
    private LocalDateTime timestamp;
    private int userid;

    public static int numberOfReviews = 0;

    public Review( int userid, double stars, int coffeeid, String description, String timestamp, boolean isVerified, String title) {
        this.title=title;
        this.reviewid = numberOfReviews++;
        this.stars = stars;
        this.coffeeid = coffeeid;
        this.description = description;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        this.timestamp = LocalDateTime.parse(timestamp, formatter);
        //setTimestamp(timestamp);
        this.userid = userid;
        this.isVerified = isVerified;
    }
    public Review(){

    }
    public Review(String title,String description,User user,int stars,boolean isVerified){
        this.title = title;
        this.description = description;
        this.stars = stars;
    }
    public Review(String title,String description,User user,double stars,boolean isVerified){
        this.userid = user.getUserid();
        this.title = title;
        this.description = description;
        this.stars = stars;
        this.isVerified = isVerified;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        this.timestamp = LocalDateTime.parse(timestamp, formatter);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCoffeeId() {
        return coffeeid;
    }

    public void setCoffee(Coffee coffee) {
        this.coffeeid = coffeeid;
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

    public int getReviewid() {
        return reviewid;
    }

    public void setReviewid(int reviewid) {
        this.reviewid = reviewid;
    }

    public int getCoffeeid() {
        return coffeeid;
    }

    public void setCoffeeid(int coffeeid) {
        this.coffeeid = coffeeid;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}