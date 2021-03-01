package com.example.plantarium.Models;


import android.net.Uri;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

public class User implements Serializable {

    // DM
    private String _email;
    private String _fullname;
    private String _image_url;
    private Date last_login_time;

   public User(String email, String fullname, Uri image_url, Date date){
       this.setEmail(email);
       this.setFullname(fullname);
       this.setImageURL(image_url);
       setLastLoginTime(date);
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
       return this.last_login_time;
    }

    //setters

    public void setEmail(String email){
        this._email = email;
    }

    public void setFullname(String fullname){
       this._fullname = fullname;
    }

    public void setImageURL(Uri image_url){
        this._image_url = image_url.toString();
    }

    public void setLastLoginTime(Date date){
        this.last_login_time = new Date(date.getTime());
    }
}
