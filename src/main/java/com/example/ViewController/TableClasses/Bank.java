package com.example.ViewController.TableClasses;

import javafx.beans.property.SimpleStringProperty;

public class Bank {

    private final SimpleStringProperty Name;
    private final SimpleStringProperty location;
    private final SimpleStringProperty contact;
    private final SimpleStringProperty holiday;
    private final SimpleStringProperty openingTime;



    public Bank(String Name, String location, String contact,String holiday,String openingTime) {
        this.Name = new SimpleStringProperty(Name);
        this.location = new SimpleStringProperty(location);
        this.contact = new SimpleStringProperty(contact);
        this.holiday = new SimpleStringProperty(holiday);
        this.openingTime = new SimpleStringProperty(openingTime);
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

    public void setRating(String fName) {
        contact.set(fName);
    }
    
    public String getHoliday() {
        return holiday.get();
    }

    public void setHoliday(String fName) {
        holiday.set(fName);
    }
    public String getOpeningTime() {
        return openingTime.get();
    }

    public void setOpeningTime(String fName) {
        openingTime.set(fName);
    }
}
