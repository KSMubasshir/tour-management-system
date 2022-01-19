package com.example.ViewController.TableClasses;

import javafx.beans.property.SimpleStringProperty;

public class Transport {

    private final SimpleStringProperty Name;
    private final SimpleStringProperty location;
    private final SimpleStringProperty rating;
    private final SimpleStringProperty cost;
    private final SimpleStringProperty contact;
    private final SimpleStringProperty type;
    private final SimpleStringProperty StartTime;

    public Transport(String Name, String contact, String location, String StartTime, String rating, String cost, String type) {
        this.Name = new SimpleStringProperty(Name);
        this.location = new SimpleStringProperty(location);
        this.rating = new SimpleStringProperty(rating);
        this.cost = new SimpleStringProperty(cost);
        this.contact = new SimpleStringProperty(contact);
        this.type = new SimpleStringProperty(type);
        this.StartTime = new SimpleStringProperty(StartTime);
    }

    public String getStartTime() {
        return StartTime.get();
    }
    
    public void setStartTime(String pName) {
        StartTime.set(pName);
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

    public String getCost() {
        return cost.get();
    }

    public void setCost(String fName) {
        cost.set(fName);
    }

    public String getContact() {
        return contact.get();
    }

    public void setContact(String fName) {
        contact.set(fName);
    }

    public String getType() {
        return type.get();
    }

    public void setType(String fName) {
        type.set(fName);
    }

}
