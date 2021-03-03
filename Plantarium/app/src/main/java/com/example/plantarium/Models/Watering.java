package com.example.plantarium.Models;

import java.util.Date;
import java.io.Serializable;

public class Watering implements Serializable {
    // DM
    private int _id;
    private int _plant_id;
    private String _user_id;
    private String _image_url;
    private Date _watering_date;

    public Watering() {

    }

    public Watering(int _id, int _plant_id, String _user_id, String _image_url, Date _watering_date) {
        this._id = _id;
        this._plant_id = _plant_id;
        this._user_id = _user_id;
        this._image_url = _image_url;
        this._watering_date = _watering_date;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public int getPlantId() {
        return _plant_id;
    }

    public void setPlantId(int _plant_id) {
        this._plant_id = _plant_id;
    }

    public String getUserId() {
        return _user_id;
    }

    public void setUserId(String _user_id) {
        this._user_id = _user_id;
    }

    public String getImageUrl() {
        return _image_url;
    }

    public void setImageUrl(String _image_url) {
        this._image_url = _image_url;
    }

    public Date getWateringDate() {
        return _watering_date;
    }

    public void setWateringDate(Date _watering_date) {
        this._watering_date = _watering_date;
    }
}
