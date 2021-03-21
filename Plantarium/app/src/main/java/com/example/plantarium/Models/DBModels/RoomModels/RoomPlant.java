package com.example.plantarium.Models.DBModels.RoomModels;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.plantarium.Models.DBModels.PlantModel;
import com.example.plantarium.Models.Plant;

import java.util.List;

public class RoomPlant {
    public LiveData<List<Plant>> getAllPlants(){
        return AppLocalDb.db.plantDao().getAllPlants();
    }

    public interface AddPlantListener{
        void onComplete();
    }
    public void addPlant(final Plant plant, final PlantModel.UpdatePlantListener listener){
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDb.db.plantDao().insertAll(plant);
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
