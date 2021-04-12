package com.example.plantarium.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.firebase.database.ServerValue;

import java.util.Date;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
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
    @TypeConverters(DateConverter.class)
    private Date wateringDate;
    private Long lastUpdated;
    private int deleted; // 0 no 1 yes

    public Watering() {

    }

    public Watering(String _plant_id, String _user_id, Date _watering_date) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        this.id = uuidAsString;
        this.plantId = _plant_id;
        this.userId = _user_id;
        this.wateringDate = _watering_date;
    }

    public Watering(String _plant_id, String _user_id, String _image_url, Date _watering_date) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        this.id = uuidAsString;
        this.plantId = _plant_id;
        this.userId = _user_id;
        this.imageUrl = _image_url;
        this.wateringDate = _watering_date;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("plantId", plantId);
        result.put("userId", userId);
        result.put("imageUrl", imageUrl);
        result.put("wateringDate", wateringDate);
        result.put("lastUpdated", ServerValue.TIMESTAMP);
        result.put("deleted", deleted);
        return result;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
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

    public Date getWateringDate() {
        return wateringDate;
    }

    public void setWateringDate(Date wateringDate) {
        this.wateringDate = wateringDate;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public static class DateConverter {

        @TypeConverter
        public static Date toDate(Long dateLong){
            return dateLong == null ? null: new Date(dateLong);
        }

        @TypeConverter
        public static Long fromDate(Date date){
            return date == null ? null : date.getTime();
        }
    }
}
