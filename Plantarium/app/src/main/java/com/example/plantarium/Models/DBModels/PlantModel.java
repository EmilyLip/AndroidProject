package com.example.plantarium.Models.DBModels;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;

import com.example.plantarium.Models.DBModels.FirebaseModels.FirebasePlant;
import com.example.plantarium.Models.DBModels.RoomModels.RoomPlant;
import com.example.plantarium.Models.DBModels.RoomModels.RoomUser;
import com.example.plantarium.Models.Plant;
import com.example.plantarium.MyApplication;

import java.util.LinkedList;
import java.util.List;

public class PlantModel {
    public final static PlantModel instance = new PlantModel();

    FirebasePlant modelFirebase = new FirebasePlant();
    RoomPlant modelSql = new RoomPlant();

    public interface  UploadImageListenr {
        void onComplete(String url);
    }

    public interface  UpdatePlantListener {
        void onComplete();
    }

    public void uploadPlantImage(Bitmap imageBtmp, String name, final UploadImageListenr listenr){
        modelFirebase.uploadImage(imageBtmp, name, listenr);
    }

    public void updatePlant(Plant plant, final UpdatePlantListener listenr){
        modelFirebase.updatePlant(plant, listenr);
    }

//    LiveData<List<Plant>> plantList;
//    public LiveData<List<Plant>> getAllPlants() {
//        if (plantList == null){
//            plantList = modelSql.getAllPlants();
//            refreshAllPlants(null);
//        }
//        return plantList;
//    }

    public List<Plant> getAllPlants() {
        List<Plant> plantList = new LinkedList<Plant>();

        for(int i = 0; i < 10; i++) {
            Plant p = new Plant();
            p.setName("יוסי" + i);
            plantList.add(p);
        }
        return plantList;
    }

    public interface GetAllPlantsListener {
        void onComplete(List<Plant> plants);
        void onComplete();
    }
    public void refreshAllPlants(final GetAllPlantsListener listener) {
        //1. get local last update date
        final SharedPreferences sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
        long lastUpdated = sp.getLong("lastUpdatedPlants",0);
        //2. get all updated record from firebase from the last update date
        modelFirebase.getAllPlants(lastUpdated, new FirebasePlant.GetAllPlantsListener() {
            @Override
            public void onComplete(List<Plant> result) {
                //3. insert the new updates to the local db
                long lastU = 0;
                for (Plant p: result) {
                    modelSql.addPlant(p,null);
                    if (p.getLastUpdated()>lastU){
                        lastU = p.getLastUpdated();
                    }
                }
                //4. update the local last update date
                sp.edit().putLong("lastUpdatedPlants", lastU).commit();
                //5. return the updates data to the listeners
                if(listener != null){
                    listener.onComplete();
                }
            }
        });
    }
}
