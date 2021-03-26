package com.example.plantarium.Models.DBModels;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;

import com.example.plantarium.Models.DBModels.FirebaseModels.FirebasePlant;
import com.example.plantarium.Models.DBModels.RoomModels.RoomPlant;
import com.example.plantarium.Models.Plant;
import com.example.plantarium.MyApplication;

import java.util.List;

public class PlantModel {
    public final static PlantModel instance = new PlantModel();

    FirebasePlant modelFirebase = new FirebasePlant();
    RoomPlant modelSql = new RoomPlant();

    public interface  UpdatePlantListener {
        void onComplete();
    }
    public void updatePlant(Plant plant, final UpdatePlantListener listener){
        modelFirebase.updatePlant(plant, new UpdatePlantListener(){
            @Override
            public void onComplete() {
                refreshAllPlants(new GetAllPlantsListener() {
                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }
                });
            }
        });
    }

    String currPlaceId;
    LiveData<List<Plant>> plants;
    public LiveData<List<Plant>> getPlantsByPlaceId(String placeId) {
        if (plants == null || currPlaceId != placeId) {
            currPlaceId = placeId;
            plants = modelSql.getAllPlantsByPlace(placeId);
            refreshAllPlants(null);
        }

        refreshAllPlants(null);
        return plants;
    }

    public interface  GetAllPlantsListener {
        void onComplete();
    }
    public void refreshAllPlants(final GetAllPlantsListener listener) {
        //1. get local last update date
        final SharedPreferences sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
        long lastUpdated = sp.getLong("lastUpdatedPlant",0);
        //2. get all updated record from firebase from the last update date
        modelFirebase.getAllPlants(lastUpdated, new FirebasePlant.GetAllPlantsListener() {
            @Override
            public void onComplete(List<Plant> result) {
                //3. insert the new updates to the local db
                long lastU = 0;
                for (Plant p: result) {
                    modelSql.addPlant(p,null);
                    if (p.getLastUpdated() > lastU){
                        lastU = p.getLastUpdated();
                    }
                }
                //4. update the local last update date
                sp.edit().putLong("lastUpdatedPlant", lastU).commit();
                //5. return the updates data to the listeners
                if(listener != null){
                    listener.onComplete();
                }
            }
        });
    }
}
