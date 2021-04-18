package com.example.plantarium.PlacesFragments;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.plantarium.HomePageFragments.LoginPageFragment;
import com.example.plantarium.Models.DBModels.PlaceMemberModel;
import com.example.plantarium.Models.DBModels.PlaceModel;
import com.example.plantarium.Models.Place;
import com.example.plantarium.Models.PlaceMember;

import java.util.List;

public class PlacesListViewModel extends ViewModel {
    private LiveData<List<Place>> userPlaces;
    private LiveData<List<PlaceMember>> placeMembers;

    public PlacesListViewModel(){
        Log.d("TAG","PlacesListViewModel");
        userPlaces = PlaceModel.instance.getAllPlaces(LoginPageFragment.account.getEmail());
        placeMembers = PlaceMemberModel.instance.getAllPlaceMembers();
    }

    LiveData<List<Place>> getUsersPlaceList(){
        return userPlaces;
    }

    LiveData<List<PlaceMember>> getPlaceMembersListList(){
        return placeMembers;
    }

    public void refreshPlaces() {
       PlaceModel.instance.refreshAllPlaces(null);
       PlaceMemberModel.instance.refreshAllPlaceMembers(null);
    }
}