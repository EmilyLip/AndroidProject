package com.example.plantarium.Models.DBModels;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;

import com.example.plantarium.Models.DBModels.FirebaseModels.FirebasePlace;
import com.example.plantarium.Models.DBModels.RoomModels.RoomPlace;
import com.example.plantarium.Models.DBModels.RoomModels.RoomUser;
import com.example.plantarium.Models.Place;
import com.example.plantarium.MyApplication;

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

    public void uploadPlaceImage(Bitmap imageBtmp, String name, final UploadImageListenr listenr){
        modelFirebase.uploadImage(imageBtmp, name, listenr);
    }

    public void updatePlace(Place place, final UpdatePlaceListener listenr){
        modelFirebase.updatePlace(place, listenr);
    }

//    public void getAllPlaces(final GetAllPlacesListener listener){
//        modelFirebase.getAllPlaces(listener);
//    }

    LiveData<List<Place>> placeList;
    public LiveData<List<Place>> getAllPlaces() {
        if (placeList == null){
            placeList = modelSql.getAllPlaces();
            refreshAllPlaces(null);
        }
        return placeList;
    }

    public interface  GetAllPlacesListener {
        void onComplete(List<Place> places);
        void onComplete();
    }
    public void refreshAllPlaces(final GetAllPlacesListener listener) {
        //1. get local last update date
        final SharedPreferences sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
        long lastUpdated = sp.getLong("lastUpdated",0);
        //2. get all updated record from firebase from the last update date
        modelFirebase.getAllPlaces(lastUpdated, new FirebasePlace.GetAllPlacesListener() {
            @Override
            public void onComplete(List<Place> result) {
                //3. insert the new updates to the local db
                long lastU = 0;
                for (Place p: result) {
                    modelSql.addPlace(p,null);
                    if (p.getLastUpdated()>lastU){
                        lastU = p.getLastUpdated();
                    }
                }
                //4. update the local last update date
                sp.edit().putLong("lastUpdated", lastU).commit();
                //5. return the updates data to the listeners
                if(listener != null){
                    listener.onComplete();
                }
            }
        });
    }
}
