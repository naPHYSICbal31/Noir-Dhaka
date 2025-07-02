package com.example.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/*
 * Critical
 * getCriticalReviews (Sorted by timestamp verified aage)
 * getModerateReviews
 * getPositiveReviews
 *
 * pore
 * Static String array of coupon codes
 * price -10%
 * Instrument
 */


public class Coffee {
    //intro

    static String[] Tags = {"a", "b", "c"};
    private int id;
    private String name;
    private String imageurl;
    private String description;

    //product details
    private String packetSize; //e.g., "250g", "500g", "1kg"
    private double weight; //in grams
    private List<Boolean> tag;
    private double price;

    //coffee quality
    private int strength;
    private int flavour;
    private int acidity;
    private int aroma;

    //product state
    private int currentStock;
    private int numberOfSales;
    private boolean isSoldOut;
    private boolean isNearSoldOut;
    private boolean isRare;
    private boolean isSmallBatch;
    private boolean isFarmToCup;

    //extras
    private List<Review> reviews;


    //new constructor
    public Coffee(int id, String name, String imageurl, String description, String packetSize, double weight,double price ,int strength, int flavour, int acidity, int aroma,
                  boolean isRare, boolean isSmallBatch, boolean isFarmToCup, int currentStock, List<Boolean> tag) {
        this.id = id;
        this.name = name;
        this.imageurl = imageurl;
        this.description = description;
        this.packetSize = packetSize;
        this.weight = weight;
        this.price = price;
        this.strength = strength;
        this.flavour = flavour;
        this.acidity = acidity;
        this.aroma = aroma;

        this.isRare = isRare;
        this.isSmallBatch = isSmallBatch;
        this.isFarmToCup = isFarmToCup;

        this.currentStock = currentStock;
        this.numberOfSales = 0;
        this.isSoldOut = false;
        this.isNearSoldOut = false;
        this.reviews = new ArrayList<Review>();

        this.tag = new ArrayList<>(3);
        this.tag = tag;
    }

    //existing constructor

    public Coffee(int id, String name, String imageurl, String description, String packetSize, double weight, double price ,int strength, int flavour, int acidity, int aroma,
                  boolean isRare, boolean isSmallBatch, boolean isFarmToCup, int currentStock, int numberOfSales, boolean isSoldOut, boolean isNearSoldOut, List<Review> reviews, List<Boolean> tag) {
        this.id = id;
        this.name = name;
        this.imageurl = imageurl;
        this.description = description;
        this.strength = strength;
        this.packetSize = packetSize;
        this.weight = weight;
        this.price = price;
        this.flavour = flavour;
        this.acidity = acidity;
        this.aroma = aroma;
        this.isRare = isRare;
        this.isSmallBatch = isSmallBatch;
        this.isFarmToCup = isFarmToCup;
        this.currentStock = currentStock;
        this.numberOfSales = numberOfSales;
        this.isSoldOut = isSoldOut;
        this.isNearSoldOut = isNearSoldOut;
        this.reviews = new Vector<Review>();
        this.reviews.addAll(reviews);
        this.tag = new ArrayList<>(3);
        this.tag = tag;
    }
    //getters and setters

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Boolean> getTag() {
        return tag;
    }

    public void setTag(List<Boolean> tag) {
        this.tag = tag;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getFlavour() {
        return flavour;
    }

    public void setFlavour(int flavour) {
        this.flavour = flavour;
    }

    public int getAcidity() {
        return acidity;
    }

    public void setAcidity(int acidity) {
        this.acidity = acidity;
    }

    public int getAroma() {
        return aroma;
    }

    public void setAroma(int aroma) {
        this.aroma = aroma;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    public int getNumberOfSales() {
        return numberOfSales;
    }

    public void setNumberOfSales(int numberOfSales) {
        this.numberOfSales = numberOfSales;
    }

    public boolean isSoldOut() {
        return isSoldOut;
    }

    public void setSoldOut(boolean isSoldOut) {
        this.isSoldOut = isSoldOut;
    }

    public boolean isNearSoldOut() {
        return isNearSoldOut;
    }

    public void setNearSoldOut(boolean isNearSoldOut) {
        this.isNearSoldOut = isNearSoldOut;
    }

    public boolean isRare() {
        return isRare;
    }

    public void setRare(boolean isRare) {
        this.isRare = isRare;
    }

    public boolean isSmallBatch() {
        return isSmallBatch;
    }

    public void setSmallBatch(boolean isSmallBatch) {
        this.isSmallBatch = isSmallBatch;
    }

    public boolean isFarmToCup() {
        return isFarmToCup;
    }

    public void setFarmToCup(boolean isFarmToCup) {
        this.isFarmToCup = isFarmToCup;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Vector<Review> reviews) {
        this.reviews = reviews;
    }

    public String getPacketSize() {
        return packetSize;
    }

    public void setPacketSize(String packetSize) {
        this.packetSize = packetSize;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }


    /***************************************************************************************************************
     *
     *
     *
     ***************************************************************************************************************/

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public void removeReview(Review review) {
        this.reviews.remove(review);
    }

    public void clearReviews() {
        this.reviews.clear();
    }

    public double getAverageRating(){
        if(reviews.isEmpty()){
            return 0.0;
        }
        double total = 0.0;
        for(Review review : reviews){
            total += review.getStars();
        }
        return total / reviews.size();
    }

    public void sell(){
        if(!isSoldOut && currentStock > 0) {
            processSale();
            this.currentStock--;
            this.numberOfSales++;
        } else {
            //System.out.println("Cannot sell, product is sold out or stock is zero.");
        }

        if(currentStock == 0){
            this.isSoldOut = true;
        } else if(currentStock <= 5) {
            this.isNearSoldOut = true;
        } else {
            this.isNearSoldOut = false;
        }
    }

    private void processSale(){
        //database codes

    }

    private void refund(){
        if(numberOfSales > 0) {
            this.currentStock++;
            this.numberOfSales--;
        } else {
            //System.out.println("Cannot refund, no sales to process.");
        }

        if(currentStock == 0){
            this.isSoldOut = true;
        } else this.isNearSoldOut = currentStock <= 5;
    }

    private void processRefund(){
        //database codes
    }

}
