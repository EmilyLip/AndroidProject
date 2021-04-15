package com.example.plantarium.Models.DBModels;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;

import com.example.plantarium.Models.DBModels.RoomModels.RoomWatering;
import com.example.plantarium.Models.DBModels.FirebaseModels.FirebaseWatering;
import com.example.plantarium.Models.User;
import com.example.plantarium.Models.Watering;
import com.example.plantarium.MyApplication;

import java.util.List;

public class WateringModel {

    public final static WateringModel instance = new WateringModel();

    FirebaseWatering modelFirebase = new FirebaseWatering();
    RoomWatering modelSql = new RoomWatering();

    public interface  UploadImageListenr {
        void onComplete(String url);
    }

    public interface  UpdateWateringListener {
        void onComplete();
    }

    public void uploadWateringImage(Bitmap imageBtmp, String name, final WateringModel.UploadImageListenr listener){
        modelFirebase.uploadImage(imageBtmp, name, listener);
    }

    public void updateWatering(Watering watering, final UpdateWateringListener listener){
        modelFirebase.updateWatering(watering, new WateringModel.UpdateWateringListener(){
            @Override
            public void onComplete() {
                refreshAllWaterings(new GetAllWateringListener() {
                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }
                });
            }
        });
    }

    String currPlantId;
    LiveData<List<Watering>> wateringsLive;
    public LiveData<List<Watering>> getWateringByPlantId(String plantId) {
        if (wateringsLive == null || currPlantId != plantId) {
            currPlantId = plantId;
            wateringsLive = modelSql.getWateringsByPlantId(plantId);
            refreshAllWaterings(null);
        }

        refreshAllWaterings(null);
        return wateringsLive;
    }

//    public LiveData<Watering> getPlantLastWatering(String plantId) {
//        return modelSql.getPlantLastWatering(plantId);
//    }

    public interface  GetAllWateringListener {
        void onComplete();
    }
    public void refreshAllWaterings(final GetAllWateringListener listener) {
        //1. get local last update date
        final SharedPreferences sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
        long lastUpdated = sp.getLong("lastUpdatedWatering",0);
        //2. get all updated record from firebase from the last update date
        modelFirebase.getAllWaterings(lastUpdated, new FirebaseWatering.GetAllWateringsListener() {
            @Override
            public void onComplete(List<Watering> result) {
                //3. insert the new updates to the local db
                long lastU = 0;
                for (Watering w: result) {
                    modelSql.addWatering(w,null);
                    if (w.getLastUpdated() > lastU){
                        lastU = w.getLastUpdated();
                    }
                }
                //4. update the local last update date
                sp.edit().putLong("lastUpdatedWatering", lastU).commit();
                //5. return the updates data to the listeners
                if(listener != null){
                    listener.onComplete();
                }
            }
        });
    }
}