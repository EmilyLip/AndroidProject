package com.example.plantarium.Models;

public class PlaceMember {
    //DM
    private String _user_id;
    private String _place_id;

    public PlaceMember(String _user_id, String _place_id) {
        this._user_id = _user_id;
        this._place_id = _place_id;
    }

    public PlaceMember(){

    }

    public String getUserId() {
        return _user_id;
    }

    public void setUserId(String _user_id) {
        this._user_id = _user_id;
    }

    public String getPlaceId() {
        return _place_id;
    }

    public void setPlaceId(String place_id) {
        this._place_id = place_id;
    }
}
