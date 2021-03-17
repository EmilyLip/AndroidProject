package com.example.plantarium.Models.DBModels.RoomModels;

import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.example.plantarium.Models.DBModels.PlaceMemberModel;
import com.example.plantarium.Models.PlaceMember;

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
}
