package com.example.plantarium.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.common.base.Converter;

import java.io.Serializable;
import java.util.UUID;

@Entity
public class Plant implements Serializable {
    // DM
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String imageUrl;
    private String plcaeId;
    // if watering is on sunday array[0] = 1
   // TODO: Zohar you need to convert this watering days
    //private int[] wateringDays = {0, 0, 0, 0, 0, 0, 0, 0};
    private Long lastUpdated;

    public Plant() {

    }

    public Plant( String _name, String _image_url, String _place_id, int[] _watering_days) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        this.id = uuidAsString;
        this.name = _name;
        this.imageUrl = _image_url;
        this.plcaeId = _place_id;
        //this.wateringDays = _watering_days;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPlcaeId() {
        return plcaeId;
    }

    public void setPlcaeId(String plcaeId) {
        this.plcaeId = plcaeId;
    }

//    public int[] getWateringDays() {
//        return wateringDays;
//    }
//
//    public void setWateringDays(int[] wateringDays) {
//        this.wateringDays = wateringDays;
//    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
