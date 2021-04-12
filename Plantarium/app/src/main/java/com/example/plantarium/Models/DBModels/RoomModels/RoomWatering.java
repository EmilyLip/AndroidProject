package com.example.plantarium.Models.DBModels.RoomModels;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.plantarium.Models.DBModels.PlantModel;
import com.example.plantarium.Models.DBModels.WateringModel;
import com.example.plantarium.Models.Plant;
import com.example.plantarium.Models.Watering;

import java.util.List;

public class RoomWatering {
    public LiveData<List<Watering>> getAllWaterings(){
        return AppLocalDb.db.wateringDao().getAllWaterings();
    }

    public LiveData<List<Watering>> getWateringsByPlantId(String plantId){
        return AppLocalDb.db.wateringDao().getWateringsByPlantId(plantId);
    }

//    public LiveData<Watering> getPlantLastWatering(String plantId){
//        return AppLocalDb.db.wateringDao().getPlantLastWatering(plantId);
//    }

    public void addWatering(final Watering watering, final WateringModel.UpdateWateringListener listener){
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDb.db.wateringDao().insertAll(watering);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (listener != null){
                    listener.onComplete();
                }
            }
        };
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }
}
