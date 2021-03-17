package com.example.plantarium.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
public class User implements Serializable {

    static final long serialUID = 40L;

    // DM
    @PrimaryKey
    @NonNull
    public  String id;
    public String email;
    public String fullname;
    public String imageUrl;
    public Long lastLoginTime;
    public Long lastUpdated;

   public User(String email, String fullname, String image_url, String id){
       this.setEmail(email);
       this.setFullname(fullname);
       this.setImageUrl(image_url);
       this.setLastLoginTime(new Date().getTime());
       this.setId(id);
   }

   public User() {

   }

   // getters
   public String getEmail(){
       return this.email;
   }

    public String getFullname(){
        return this.fullname;
    }

    public String getImageUrl(){
        return this.imageUrl;
    }

    public Long getLastLoginTime() {
       return this.lastLoginTime;
    }

    public String getId(){
        return this.id;
    }

    //setters

    public void setEmail(String email){
        this.email = email;
    }

    public void setFullname(String fullname){
       this.fullname = fullname;
    }

    public void setImageUrl(String image_url){
        this.imageUrl = image_url;
    }

    public void setLastLoginTime(Long date){
        this.lastLoginTime = date;
    }

    public void setId(String id){
        this.id = id;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long last_updated) {
        this.lastUpdated = last_updated;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("fullname", fullname);
        result.put("email", email);
        result.put("imageUrl", imageUrl);
        result.put("lastLoginTime", lastLoginTime);
        result.put("lastUpdated", ServerValue.TIMESTAMP);
        return result;
    }
}
