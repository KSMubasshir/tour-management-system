package com.example.ViewController.TableClasses;

import javafx.beans.property.SimpleStringProperty;

public class User {

    private final SimpleStringProperty Name;
    private final SimpleStringProperty location;
    private final SimpleStringProperty contact;

    public User(String Name, String contact, String location) {
        this.Name = new SimpleStringProperty(Name);
        this.location = new SimpleStringProperty(location);
        this.contact = new SimpleStringProperty(contact);
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

}
