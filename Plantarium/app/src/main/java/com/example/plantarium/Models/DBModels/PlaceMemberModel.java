package com.example.plantarium.Models.DBModels;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.plantarium.Models.DBModels.FirebaseModels.FirebasePlaceMember;
import com.example.plantarium.Models.DBModels.RoomModels.RoomPlaceMember;
import com.example.plantarium.Models.PlaceMember;
import com.example.plantarium.MyApplication;

import java.util.List;

public class PlaceMemberModel {
    public final static PlaceMemberModel instance = new PlaceMemberModel();
    FirebasePlaceMember modelFirebase = new FirebasePlaceMember();
    RoomPlaceMember modelSql = new RoomPlaceMember();

    public interface  GetAllPlaceMembersListListener {
        void onComplete(List<PlaceMember> placeMembers);
    }

    LiveData<List<PlaceMember>> placeMembers;
    public LiveData<List<PlaceMember>> getAllPlaceMembers() {
        if (placeMembers == null){
            placeMembers = modelSql.getAllPlaceMembers();
            refreshAllPlaceMembers(null);
        }
        return placeMembers;
    }

    public interface  GetAllPlaceMembersListener {
        void onComplete();
    }
    public void refreshAllPlaceMembers(final GetAllPlaceMembersListener listener) {
        //1. get local last update date
        final SharedPreferences sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
        long lastUpdated = sp.getLong("lastUpdatedPlaceMember",0);
        //2. get all updated record from firebase from the last update date
        modelFirebase.getAllPlaceMembers(lastUpdated, new FirebasePlaceMember.GetAllPlaceMembersListener() {
            @Override
            public void onComplete(List<PlaceMember> result) {
                //3. insert the new updates to the local db
                long lastU = 0;
                for (PlaceMember s: result) {
                    modelSql.addPlaceMember(s,null);
                    if (s.getLastUpdated()>lastU){
                        lastU = s.getLastUpdated();
                    }
                }
                //4. update the local last update date
                sp.edit().putLong("lastUpdatedPlaceMember", lastU).commit();
                //5. return the updates data to the listeners
                if(listener != null){
                    listener.onComplete();
                }
            }
        });
    }

    public interface  UpdatePlaceMemberListener {
        void onComplete();
    }

    public void updatePlaceMember(final PlaceMember place, final UpdatePlaceMemberListener listener) {
        modelFirebase.updatePlaceMember(place, new UpdatePlaceMemberListener() {
            @Override
            public void onComplete() {
                refreshAllPlaceMembers(new GetAllPlaceMembersListener() {
                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }
                });
            }
        });
    }
}
