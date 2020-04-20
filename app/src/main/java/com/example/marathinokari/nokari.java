package com.example.marathinokari;

import java.sql.Timestamp;

public class nokari {
    private String title;
    private String description;
    private String advertise;

    public nokari(){

    }

    public nokari(String title, String description, String advertise) {
        this.title = title;
        this.description = description;
        this.advertise = advertise;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public  String getAdvertise() {
        return advertise;
    }

    public void setAdvertise(String advertise) {
        this.advertise = advertise;
    }

}
