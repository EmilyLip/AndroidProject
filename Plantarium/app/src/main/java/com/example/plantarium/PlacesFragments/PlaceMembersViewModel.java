package com.example.plantarium.PlacesFragments;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import com.example.plantarium.Models.DBModels.PlaceMemberModel;
import com.example.plantarium.Models.DBModels.UserModel;
import com.example.plantarium.Models.PlaceMember;
import com.example.plantarium.Models.User;

import java.util.List;

public class PlaceMembersViewModel extends ViewModel {
    private LiveData<List<PlaceMember>> placeMembers;
    private LiveData<List<User>> placeMembersUsers;

    public PlaceMembersViewModel(String place_id){
        Log.d("TAG","PlaceMembersViewModel");
        placeMembersUsers = UserModel.instance.getAllPlaceMembersUser(place_id);
        placeMembers = PlaceMemberModel.instance.getAllPlaceMembers();
    }

    LiveData<List<User>> getUsersPlaceList(){
        return placeMembersUsers;
    }
    LiveData<List<PlaceMember>> getPlaceMembersList(){
        return placeMembers;
    }
}
