package com.example.plantarium.HomePageFragments;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.plantarium.Models.DBModels.PlaceMemberModel;
import com.example.plantarium.Models.PlaceMember;

import java.util.List;

public class LoginPageViewModel extends ViewModel {
    private LiveData<List<PlaceMember>> pmList;

    public LoginPageViewModel(){
        Log.d("TAG","StudentListViewModel");
        pmList = PlaceMemberModel.instance.getAllPlaceMembers();
    }

    LiveData<List<PlaceMember>> getList(){
        return pmList;
    }
}
