package com.example.ViewController.TableClasses;

import javafx.beans.property.SimpleStringProperty;

public class Restaurant {

    private final SimpleStringProperty Name;
    private final SimpleStringProperty location;
    private final SimpleStringProperty rating;
    private final SimpleStringProperty minCost;
    private final SimpleStringProperty maxCost;



    public Restaurant(String Name, String location, String rating,String minCost,String maxCost) {
        this.Name = new SimpleStringProperty(Name);
        this.location = new SimpleStringProperty(location);
        this.rating = new SimpleStringProperty(rating);
        this.minCost = new SimpleStringProperty(minCost);
        this.maxCost = new SimpleStringProperty(maxCost);
    }
    
    public String getName() {
        return Name.get();
    }

    public void setName(String pName) {
        Name.set(pName);
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocaion(String sp) {
        location.set(sp);
    }

    public String getRating() {
        return rating.get();
    }

    public void setRating(String fName) {
        rating.set(fName);
    }
    
    public String getMinCost() {
        return minCost.get();
    }

    public void setMinCost(String fName) {
        minCost.set(fName);
    }
    public String getMaxCost() {
        return maxCost.get();
    }

    public void setMaxCost(String fName) {
        maxCost.set(fName);
    }
}
