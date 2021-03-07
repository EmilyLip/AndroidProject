package com.example.plantarium.Models.DBModels;

import android.graphics.Bitmap;

import com.example.plantarium.Models.DBModels.FirebaseModels.FirebasePlace;
import com.example.plantarium.Models.Place;

public class PlaceModel {
    FirebasePlace modelFirebase = new FirebasePlace();

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
}
