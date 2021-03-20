package com.example.plantarium.PlacesFragments;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.plantarium.Models.DBModels.PlaceMemberModel;
import com.example.plantarium.Models.DBModels.PlaceModel;
import com.example.plantarium.Models.Place;
import com.example.plantarium.Models.PlaceMember;

import java.util.List;

public class PlacesListViewModel extends ViewModel {
    private LiveData<List<Place>> userPlaces;

    public PlacesListViewModel(){
        Log.d("TAG","PlacesListViewModel");
        userPlaces = PlaceModel.instance.getAllPlaces();
    }

    LiveData<List<Place>> getUsersPlaceList(){
        return userPlaces;
    }
}