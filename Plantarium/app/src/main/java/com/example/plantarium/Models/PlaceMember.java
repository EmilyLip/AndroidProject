package com.example.plantarium.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
public class PlaceMember {
    //DM
    @PrimaryKey
    @NonNull
    private String id;
    private String userEmail;
    private String placeId;
    private Long lastUpdated;

    public PlaceMember(String _user_email, String _place_id) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        this.id = uuidAsString;
        this.userEmail = _user_email;
        this.placeId = _place_id;
    }

    public PlaceMember(){

    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String _user_id) {
        this.userEmail = _user_id;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String place_id) {
        this.placeId = place_id;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setLastUpdated(Long last_updated) {
        this.lastUpdated = last_updated;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("userEmail", userEmail);
        result.put("placeId", placeId);
        result.put("lastUpdated", ServerValue.TIMESTAMP);
        return result;
    }
}
