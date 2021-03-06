package com.example.plantarium.Models.DBModels;

import android.graphics.Bitmap;

import com.example.plantarium.Models.DBModels.FirebaseModels.FirebasePlace;

public class PlaceModel {
    FirebasePlace modelFirebase = new FirebasePlace();

    public interface  UploadImageListenr {
        void onComplete(String url);
    }

    public void uploadPlaceImage(Bitmap imageBtmp, String name, final UploadImageListenr listenr){
        modelFirebase.uploadImage(imageBtmp, name, listenr);
    }
}
