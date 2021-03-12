
package com.example.plantarium.Models;

import com.example.plantarium.HomePageFragments.LoginPageFragment;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Place implements Serializable {
    // DM
    private String _id;
    private String _name;
    private String _image_url;
    private String creator_id;
    private Date _create_date;
    private Long last_updated;

    public Place(){

    }

    public Place(String _name) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        setId(uuidAsString);
        setName(_name);
        setCreatorId(LoginPageFragment.getAccount().getId());
        setCreateDate(new Date());
    }


    public Place(String _name, String _image_url) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        setId(uuidAsString);
        setName(_name);
        setImageUrl(_image_url);
        setCreatorId(LoginPageFragment.getAccount().getId());
        setCreateDate(new Date());
    }

    public String getId() {
        return this._id;
    }

    public String getName() {
        return this._name;
    }

    public String getImageUrl() {
        return this._image_url;
    }

    public String getCreatorId () {
        return this.creator_id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public void setImageUrl(String _image_url) {
        this._image_url = _image_url;
    }

    public void setCreatorId(String creatorId) {
        this.creator_id = creatorId;
    }

    public Long getLastUpdated() {
        return last_updated;
    }

    public void setLastUpdated(Long last_updated) {
        this.last_updated = last_updated;
    }

    public Date getCreateDate() {
        return _create_date;
    }

    public void setCreateDate(Date _create_date) {
        this._create_date = _create_date;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", _id);
        result.put("name", _name);
        result.put("creatorId", creator_id);
        result.put("imageUrl", _image_url);
        result.put("createDate", _create_date);
        result.put("lastUpdated", ServerValue.TIMESTAMP);
        return result;
    }

}
