package com.example.ViewController.TableClasses;

import javafx.beans.property.SimpleStringProperty;

public class Hospital {

    private final SimpleStringProperty Name;
    private final SimpleStringProperty location;
    private final SimpleStringProperty contact;
    private final SimpleStringProperty cost;

    public Hospital(String Name, String location, String contact,String cost) {
        this.Name = new SimpleStringProperty(Name);
        this.location = new SimpleStringProperty(location);
        this.contact = new SimpleStringProperty(contact);
        this.cost = new SimpleStringProperty(cost);

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

    public String getContact() {
        return contact.get();
    }

    public void setContact(String fName) {
        contact.set(fName);
    }
    
    public String getCost() {
        return cost.get();
    }

    public void setCost(String fName) {
        cost.set(fName);
    }
    
}
