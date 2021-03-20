package com.example.plantarium.Models.DBModels;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;

import com.example.plantarium.HomePageFragments.LoginPageFragment;
import com.example.plantarium.Models.DBModels.FirebaseModels.FirebasePlace;
import com.example.plantarium.Models.DBModels.FirebaseModels.FirebasePlaceMember;
import com.example.plantarium.Models.DBModels.RoomModels.RoomPlace;
import com.example.plantarium.Models.DBModels.RoomModels.RoomUser;
import com.example.plantarium.Models.Place;
import com.example.plantarium.Models.PlaceMember;
import com.example.plantarium.MyApplication;

import java.util.LinkedList;
import java.util.List;

public class PlaceModel {
    public final static PlaceModel instance = new PlaceModel();
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

    public void updatePlace(Place place, final UpdatePlaceListener listener){
        modelFirebase.updatePlace(place, new UpdatePlaceListener(){
            @Override
            public void onComplete() {
                refreshAllPlaces(new PlaceModel.GetAllPlacesListener() {
                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }
                });
            }
        });
    }

    public interface  GetAllPlacesListListener {
        void onComplete(List<Place> places);
    }

    LiveData<List<Place>> places;
    public LiveData<List<Place>> getAllPlaces() {
        if (places == null){
            places = modelSql.getAllPlacesByUser(LoginPageFragment.account.getEmail());
            refreshAllPlaces(null);
        }
        return places;
    }

    public interface  GetAllPlacesListener {
        void onComplete();
    }
    public void refreshAllPlaces(final PlaceModel.GetAllPlacesListener listener) {
        //1. get local last update date
        final SharedPreferences sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
        long lastUpdated = sp.getLong("lastUpdatedPlace",0);
        //2. get all updated record from firebase from the last update date
        modelFirebase.getAllPlaces(lastUpdated, new FirebasePlace.GetAllPlacesListener() {
            @Override
            public void onComplete(List<Place> result) {
                //3. insert the new updates to the local db
                long lastU = 0;
                for (Place s: result) {
                    modelSql.addPlace(s,null);
                    if (s.getLastUpdated()>lastU){
                        lastU = s.getLastUpdated();
                    }
                }
                //4. update the local last update date
                sp.edit().putLong("lastUpdatedPlace", lastU).commit();
                //5. return the updates data to the listeners
                if(listener != null){
                    listener.onComplete();
                }
            }
        });
    }

}
