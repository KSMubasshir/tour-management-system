package com.example.ViewController.TableClasses;

import javafx.beans.property.SimpleStringProperty;

public class Accommodation {

    private final SimpleStringProperty Name;
    private final SimpleStringProperty location;
    private final SimpleStringProperty rating;
    private final SimpleStringProperty cost;
    private final SimpleStringProperty contact;
    private final SimpleStringProperty type;

    public Accommodation(String Name, String location,  String rating,String cost,String contact,String type) {
        this.Name = new SimpleStringProperty(Name);
        this.location = new SimpleStringProperty(location);
        this.rating = new SimpleStringProperty(rating);
        this.cost= new SimpleStringProperty(cost);
        this.contact=new SimpleStringProperty(contact);
        this.type=new SimpleStringProperty(type);
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
    public void setLocaion(String sp){
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