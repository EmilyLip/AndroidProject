package com.example.plantarium.Models.DBModels;

import android.graphics.Bitmap;

import com.example.plantarium.Models.DBModels.FirebaseModels.FirebasePlace;
import com.example.plantarium.Models.DBModels.RoomModels.RoomPlace;
import com.example.plantarium.Models.DBModels.RoomModels.RoomUser;
import com.example.plantarium.Models.Place;

import java.util.LinkedList;
import java.util.List;

public class PlaceModel {
    FirebasePlace modelFirebase = new FirebasePlace();
    RoomPlace modelSql = new RoomPlace();

    public interface  UploadImageListenr {
        void onComplete(String url);
    }

    public interface  UpdatePlaceListener {
        void onComplete();
    }

    public interface  GetAllPlacesListener {
        void onComplete(List<Place> places);
    }

    public void uploadPlaceImage(Bitmap imageBtmp, String name, final UploadImageListenr listenr){
        modelFirebase.uploadImage(imageBtmp, name, listenr);
    }

    public void updatePlace(Place place, final UpdatePlaceListener listenr){
        modelFirebase.updatePlace(place, listenr);
    }

    public void getAllPlaces(final GetAllPlacesListener listener){
        modelFirebase.getAllPlaces(listener);
    }
}
