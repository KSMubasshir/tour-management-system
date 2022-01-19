package com.example.ViewController.TableClasses;

import javafx.beans.property.SimpleStringProperty;

public class Place {

    private final SimpleStringProperty placeName;
    private final SimpleStringProperty location;
    private final SimpleStringProperty rating;
    private final SimpleStringProperty speciality;

    public Place(String placeName, String location,  String rating,String speciality) {
        this.placeName = new SimpleStringProperty(placeName);
        this.location = new SimpleStringProperty(location);
        this.rating = new SimpleStringProperty(rating);
        this.speciality= new SimpleStringProperty(speciality);
    }

    public String getSpeciality() {
        return speciality.get();
    }
    public void setSpeciality(String sp){
        speciality.set(sp);
    }
    public String getPlaceName() {
        return placeName.get();
    }

    public void setPlaceName(String pName) {
        placeName.set(pName);
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String fName) {
        location.set(fName);
    }

    public String getRating() {
        return rating.get();
    }

    public void setRating(String fName) {
        rating.set(fName);
    }
    @Override
    public String toString(){
        return placeName+" "+location+" "+rating+" "+speciality;
    }
}