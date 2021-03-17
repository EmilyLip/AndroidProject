package com.example.plantarium.HomePageFragments;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.plantarium.Models.DBModels.PlaceMemberModel;
import com.example.plantarium.Models.DBModels.PlaceModel;
import com.example.plantarium.Models.Place;
import com.example.plantarium.Models.PlaceMember;

import java.util.List;

public class LoginPageViewModel extends ViewModel {
    private LiveData<List<PlaceMember>> pmList;
    private LiveData<List<Place>> userPlaces;

    public LoginPageViewModel(){
        Log.d("TAG","StudentListViewModel");
        pmList = PlaceMemberModel.instance.getAllPlaceMembers();
        userPlaces = PlaceModel.instance.getAllPlaces();
    }

    LiveData<List<PlaceMember>> getPlaceMembersList(){
        return pmList;
    }

    LiveData<List<Place>> getUsersPlaceList(){
        return userPlaces;
    }
}
