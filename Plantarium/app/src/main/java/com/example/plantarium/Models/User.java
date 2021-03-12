package com.example.plantarium.Models;

import com.google.firebase.Timestamp;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class User implements Serializable {

    static final long serialUID = 40L;

    // DM
    public  String _id;
    public String _email;
    public String _fullname;
    public String _image_url;
    public Date _last_login_time;
    public Long last_updated;

   public User(String email, String fullname, String image_url, Date date, String id){
       this.setEmail(email);
       this.setFullname(fullname);
       this.setImageURL(image_url);
       this.setLastLoginTime(date);
       this.setId(id);
   }

   public User() {

   }

   // getters
   public String getEmail(){
       return this._email;
   }

    public String getFullname(){
        return this._fullname;
    }

    public String getImageURL(){
        return this._image_url;
    }

    public Date getLastLoginTime() {
       return this._last_login_time;
    }

    public String getId(){
        return this._id;
    }

    //setters

    public void setEmail(String email){
        this._email = email;
    }

    public void setFullname(String fullname){
       this._fullname = fullname;
    }

    public void setImageURL(String image_url){
        this._image_url = image_url;
    }

    public void setLastLoginTime(Date date){
        this._last_login_time = new Date(date.getTime());
    }

    public void setId(String id){
        this._id = id;
    }

    public Long getLastUpdated() {
        return last_updated;
    }

    public void setLastUpdated(Long last_updated) {
        this.last_updated = last_updated;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", _id);
        result.put("fullname", _fullname);
        result.put("email", _email);
        result.put("imageUrl", _image_url);
        result.put("lastLoginTime", _last_login_time);
        result.put("lastUpdated", ServerValue.TIMESTAMP);
        return result;
    }
}
