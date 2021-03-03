package com.example.plantarium.Models;

import java.io.Serializable;

public class Plant implements Serializable {
    // DM
    private int _id;
    private String _name;
    private String _image_url;
    private int _place_id;
    // if watering is on sunday array[0] = 1
    private int[] _watering_days = {0, 0, 0, 0, 0, 0, 0, 0};

    public Plant() {

    }

    public Plant(int _id, String _name, String _image_url, int _place_id, int[] _watering_days) {
        this._id = _id;
        this._name = _name;
        this._image_url = _image_url;
        this._place_id = _place_id;
        this._watering_days = _watering_days;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public String getImageUrl() {
        return _image_url;
    }

    public void setImageUrl(String _image_url) {
        this._image_url = _image_url;
    }

    public int getPlaceId() {
        return _place_id;
    }

    public void setPlaceId(int _place_id) {
        this._place_id = _place_id;
    }

    public int[] getWateringDays() {
        return _watering_days;
    }

    public void setWateringDays(int[] _watering_days) {
        this._watering_days = _watering_days;
    }
}
