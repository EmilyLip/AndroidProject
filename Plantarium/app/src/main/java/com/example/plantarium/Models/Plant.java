package com.example.plantarium.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
public class Plant implements Serializable {
    // DM
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String imageUrl;
    private String placeId;
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
        this.placeId = _place_id;
        //this.wateringDays = _watering_days;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("imageUrl", imageUrl);
        result.put("placeId", placeId);
        result.put("lastUpdated", ServerValue.TIMESTAMP);
        return result;
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

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
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
