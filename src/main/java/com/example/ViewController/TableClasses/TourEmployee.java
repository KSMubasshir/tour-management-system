package com.example.ViewController.TableClasses;

import javafx.beans.property.SimpleStringProperty;

public class TourEmployee {
    
    private final SimpleStringProperty tourNo;
    private final SimpleStringProperty user;
    private final SimpleStringProperty place;
    private final SimpleStringProperty accommodationType;
    private final SimpleStringProperty accommodation;
    private final SimpleStringProperty transportType;
    private final SimpleStringProperty transport;
    private final SimpleStringProperty guide;

    public TourEmployee(String tourNo,String user,String place, String accommodationType,  String accommodation,String transportType,String transport,String guide) {
        this.tourNo=new SimpleStringProperty(tourNo);        
        this.user=new SimpleStringProperty(user);
        this.place = new SimpleStringProperty(place);
        this.accommodationType = new SimpleStringProperty(accommodationType);
        this.accommodation = new SimpleStringProperty(accommodation);
        this.transportType= new SimpleStringProperty(transportType);
        this.transport=new SimpleStringProperty(transport);
        this.guide=new SimpleStringProperty(guide);
    }
    
    public String getTourNo() {
        return tourNo.get();
    }

    public void setTourNo(String pName) {
        tourNo.set(pName);
    }
    
    
    
    public String getUser() {
        return user.get();
    }

    public void setUser(String pName) {
        user.set(pName);
    }
    
    public String getPlace() {
        return place.get();
    }

    public void setPlace(String pName) {
        place.set(pName);
    }

    public String getAccommodationType() {
        return accommodationType.get();
    }
    public void setAccommodationType(String sp){
        accommodationType.set(sp);
    }

    public String getAccommodation() {
        return accommodation.get();
    }

    public void setAccommodation(String fName) {
        accommodation.set(fName);
    }
    
    public String getTransportType() {
        return transportType.get();
    }
    public void setTransportType(String fName) {
        transportType.set(fName);
    }
    
    
    public String getTransport() {
        return transport.get();
    }
    public void setTransport(String fName) {
        transport.set(fName);
    }

    public String getGuide() {
        return guide.get();
    }
    public void setGuide(String fName) {
        guide.set(fName);
    }
    
}