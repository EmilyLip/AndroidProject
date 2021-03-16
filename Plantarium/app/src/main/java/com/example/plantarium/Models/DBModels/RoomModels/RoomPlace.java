package com.example.plantarium.Models.DBModels.RoomModels;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.plantarium.Models.DBModels.PlaceModel;
import com.example.plantarium.Models.Place;

import java.util.List;

public class RoomPlace {
    public LiveData<List<Place>> getAllPlaces(){
        return AppLocalDb.db.placeDao().getAllPlaces();
    }

    public interface AddPlaceListener{
        void onComplete();
    }
    public void addPlace(Place place, final PlaceModel.UpdatePlaceListener listener){
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDb.db.placeDao().insertAll(place);
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
