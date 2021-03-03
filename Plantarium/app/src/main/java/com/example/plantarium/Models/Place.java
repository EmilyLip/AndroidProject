package com.example.plantarium.Models;

import java.io.Serializable;
import java.util.Date;

public class Place implements Serializable {
    // DM
    private int _id;
    private String _name;
    private String _image_url;

    public Place(){

    }

    public Place(int _id, String _name, String _image_url) {
       setId(_id);
       setName(_name);
       setImageurl(_image_url);
    }

    public int getId() {
        return this._id;
    }

    public String getName() {
        return this._name;
    }

    public String getImageUrl() {
        return this._image_url;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public void setImageurl(String _image_url) {
        this._image_url = _image_url;
    }
}
