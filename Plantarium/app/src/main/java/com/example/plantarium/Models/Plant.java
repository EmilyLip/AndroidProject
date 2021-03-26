package com.example.plantarium.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.firebase.database.ServerValue;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
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
    private String type;
    private String placeId;
    // if watering is on sunday array[1] = 1
    @TypeConverters(WateringDaysTypeConverter.class)
    private ArrayList<Integer> wateringDays;
    private Long lastUpdated;
    private int deleted; // 0 no 1 yes

    public Plant() {

    }

    public Plant(String _name, String _type, String _place_id, ArrayList<Integer> _watering_days) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        this.id = uuidAsString;
        this.name = _name;
        this.type = _type;
        this.placeId = _place_id;
        this.wateringDays = _watering_days;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("type", type);
        result.put("placeId", placeId);
        result.put("wateringDays", wateringDays);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public ArrayList<Integer> getWateringDays() {
        return wateringDays;
    }

    public void setWateringDays(ArrayList<Integer> wateringDays) {
        this.wateringDays = wateringDays;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public static class WateringDaysTypeConverter {

        @TypeConverter
        public static ArrayList<Integer> fromString(String wateringDaysJson) {
            if (wateringDaysJson == null) return null;

            Gson gson = new Gson();
            ArrayList<Integer> wateringDays = gson.fromJson(wateringDaysJson, ArrayList.class);
            return wateringDays;
        }

        @TypeConverter
        public static String toString(ArrayList<Integer> wateringDays) {
            if (wateringDays == null) return null;

            Gson gson = new Gson();
            String wateringDaysJson = gson.toJson(wateringDays);
            return wateringDaysJson;
        }
    }
}
