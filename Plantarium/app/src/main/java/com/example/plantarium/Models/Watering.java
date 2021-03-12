package com.example.plantarium.Models;

import java.util.Date;
import java.io.Serializable;
import java.util.UUID;

public class Watering implements Serializable {
    // DM
    private String _id;
    private String _plant_id;
    private String _user_id;
    private String _image_url;
    private Date _watering_date;
    private Long last_updated;

    public Watering() {

    }

    public Watering(String _plant_id, String _user_id, String _image_url, Date _watering_date) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        this._id = uuidAsString;
        this._plant_id = _plant_id;
        this._user_id = _user_id;
        this._image_url = _image_url;
        this._watering_date = _watering_date;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getPlantId() {
        return _plant_id;
    }

    public void setPlantId(String _plant_id) {
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

    public Long getLastUpdated() {
        return last_updated;
    }

    public void setLastUpdated(Long last_updated) {
        this.last_updated = last_updated;
    }
}
