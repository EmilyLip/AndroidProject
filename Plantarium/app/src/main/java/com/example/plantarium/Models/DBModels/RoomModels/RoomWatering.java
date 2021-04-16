package com.example.plantarium.Models.DBModels.RoomModels;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.plantarium.Models.DBModels.WateringModel;
import com.example.plantarium.Models.Watering;

import java.util.List;

public class RoomWatering {
    public LiveData<List<Watering>> getAllWaterings(){
        return AppLocalDb.db.wateringDao().getAllWaterings();
    }

    public LiveData<List<Watering>> getWateringsByPlantId(String plantId){
        return AppLocalDb.db.wateringDao().getWateringsByPlantId(plantId);
    }

    public interface WateringAsynchDaoListener<T>{
        void onComplete(T data);
    }

    // public Watering getPlantLastWatering(String plantId){
    public void getPlantLastWatering(String plantId, final WateringAsynchDaoListener<Watering> listener) {
        // return AppLocalDb.db.wateringDao().getPlantLastWatering(plantId);
        class MyAsynchTask extends AsyncTask<String,String,Watering>{
            @Override
            protected Watering doInBackground(String... strings) {
                Watering wt = AppLocalDb.db.wateringDao().getPlantLastWatering(plantId);
                return wt;
            }
            @Override
            protected void onPostExecute(Watering wt) {
                super.onPostExecute(wt);
                listener.onComplete(wt);
            }
        }

        MyAsynchTask task = new MyAsynchTask();
        task.execute();
    }

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
