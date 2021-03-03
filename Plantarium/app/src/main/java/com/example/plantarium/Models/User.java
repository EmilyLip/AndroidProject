package com.example.plantarium.Models;


import android.net.Uri;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    // DM
    private String _id;
    private String _email;
    private String _fullname;
    private String _image_url;
    private Date _last_login_time;

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
}
