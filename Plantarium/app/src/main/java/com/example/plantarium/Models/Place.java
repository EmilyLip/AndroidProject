
package com.example.plantarium.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.plantarium.HomePageFragments.LoginPageFragment;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
public class Place implements Serializable {
    // DM
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String imageUrl;
    private String creatorId;
    private Long createDate;
    private Long lastUpdated;

    public Place(){

    }

    public Place(String _name) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        setId(uuidAsString);
        setName(_name);
        setCreatorId(LoginPageFragment.getAccount().getId());
        setCreateDate(new Date().getTime());
    }


    public Place(String _name, String _image_url) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        setId(uuidAsString);
        setName(_name);
        setImageUrl(_image_url);
        setCreatorId(LoginPageFragment.getAccount().getId());
        setCreateDate(new Date().getTime());
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getCreatorId () {
        return this.creatorId;
    }

    public void setId(String _id) {
        this.id = _id;
    }

    public void setName(String _name) {
        this.name = _name;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long last_updated) {
        this.lastUpdated = last_updated;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long _create_date) {
        this.createDate = _create_date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("creatorId", creatorId);
        result.put("imageUrl", imageUrl);
        result.put("createDate", createDate);
        result.put("lastUpdated", ServerValue.TIMESTAMP);
        return result;
    }

}
