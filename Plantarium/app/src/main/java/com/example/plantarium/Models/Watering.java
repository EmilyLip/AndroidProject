package com.example.plantarium.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.io.Serializable;
import java.util.UUID;

@Entity
public class Watering implements Serializable {
    // DM
    @PrimaryKey
    @NonNull
    private String id;
    private String plantId;
    private String userId;
    private String imageUrl;
    private Long wateringDate;
    private Long lastUpdated;

    public Watering() {

    }

    public Watering(String _plant_id, String _user_id, String _image_url, Long _watering_date) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        this.id = uuidAsString;
        this.plantId = _plant_id;
        this.userId = _user_id;
        this.imageUrl = _image_url;
        this.wateringDate = _watering_date;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getWateringDate() {
        return wateringDate;
    }

    public void setWateringDate(Long wateringDate) {
        this.wateringDate = wateringDate;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
