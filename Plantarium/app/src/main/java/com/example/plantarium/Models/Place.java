package com.example.plantarium.Models;

import com.example.plantarium.LoginPageFragment;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Place implements Serializable {
    // DM
    private String _id;
    private String _name;
    private String _image_url;
    private String creator_id;

    public Place(){

    }

    public Place(String _name) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        setId(uuidAsString);
        setName(_name);
        setCreatorId(LoginPageFragment.getAccount().getId());
    }


    public Place(String _name, String _image_url) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        setId(uuidAsString);
        setName(_name);
        setImageUrl(_image_url);
        setCreatorId(LoginPageFragment.getAccount().getId());
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
}
