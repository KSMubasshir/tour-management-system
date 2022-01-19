package com.example.ViewController.TableClasses;

import javafx.beans.property.SimpleStringProperty;

public class Event {

    private final SimpleStringProperty Name;
    private final SimpleStringProperty location;
    private final SimpleStringProperty contact;
    private final SimpleStringProperty startDate;
    private final SimpleStringProperty duration;

    public Event(String Name, String location, String contact,String startDate,String duration) {
        this.Name = new SimpleStringProperty(Name);
        this.location = new SimpleStringProperty(location);
        this.contact = new SimpleStringProperty(contact);
        this.duration = new SimpleStringProperty(duration);
        this.startDate=new SimpleStringProperty(startDate);
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
    
    public String getStartDate() {
        return startDate.get();
    }

    public void setStartDate(String fName) {
        startDate.set(fName);
    }
    public String getDuration() {
        return duration.get();
    }

    public void setDuration(String fName) {
        duration.set(fName);
    }
    
}
