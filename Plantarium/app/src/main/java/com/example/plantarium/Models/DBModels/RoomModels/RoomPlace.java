package com.example.plantarium.Models.DBModels.RoomModels;

import androidx.lifecycle.LiveData;

import com.example.plantarium.Models.Place;

import java.util.List;

public class RoomPlace {
    public LiveData<List<Place>> getAllUsers(){
        return AppLocalDb.db.placeDao().getAllPlaces();
    }
}
