package com.example.plantarium.Models.DBModels.RoomModels;

import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.example.plantarium.Models.DBModels.PlaceMemberModel;
import com.example.plantarium.Models.PlaceMember;

import java.util.ArrayList;
import java.util.List;

public class RoomPlaceMember {
    public LiveData<List<PlaceMember>> getAllPlaceMembers(){
        return AppLocalDb.db.placeMemberDao().getAllPlaceMembers();
    }

    public interface AddPlaceMemberListener{
        void onComplete();
    }
    public void addPlaceMember(PlaceMember place, final PlaceMemberModel.UpdatePlaceMemberListener listener){
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDb.db.placeMemberDao().insertAll(place);
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

    public interface getPlaceMemberByPlaceAndUserListener{
        void onComplete(PlaceMember placeMember);
    }
    public void getPlaceMemberByPlaceAndUser(String user_email, String place_id, final PlaceMemberModel.getPlaceMemberByPlaceAndUserListener listener){
        class MyAsyncTask extends AsyncTask {
            PlaceMember placeMember;
            @Override
            protected Object doInBackground(Object[] objects) {
                placeMember= AppLocalDb.db.placeMemberDao().getPlaceMemberByPlaceAndUser(user_email, place_id);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                listener.onComplete(placeMember);
            }
        };
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }
}
