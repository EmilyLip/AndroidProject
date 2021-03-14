package com.example.plantarium.Models.DBModels;

import com.example.plantarium.Models.DBModels.FirebaseModels.FirebasePlace;
import com.example.plantarium.Models.DBModels.FirebaseModels.FirebasePlaceMember;
import com.example.plantarium.Models.DBModels.RoomModels.RoomPlaceMember;
import com.example.plantarium.Models.Place;
import com.example.plantarium.Models.PlaceMember;

import java.util.List;

public class PlaceMemberModel {
    FirebasePlaceMember modelFirebase = new FirebasePlaceMember();
    RoomPlaceMember modelSql = new RoomPlaceMember();

    public interface  GetAllPlaceMembersListener {
        void onComplete(List<PlaceMember> placeMemberList);
    }

    public interface  UpdatePlaceListener {
        void onComplete();
    }

    public void updatePlaceMember(PlaceMember place, final UpdatePlaceListener listenr){
        modelFirebase.updatePlaceMember(place, listenr);
        modelSql.addPlaceMember(place, listenr);
    }
}
