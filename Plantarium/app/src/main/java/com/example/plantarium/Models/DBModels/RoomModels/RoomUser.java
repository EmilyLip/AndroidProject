package com.example.plantarium.Models.DBModels.RoomModels;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.plantarium.Models.DBModels.PlaceMemberModel;
import com.example.plantarium.Models.DBModels.UserModel;
import com.example.plantarium.Models.PlaceMember;
import com.example.plantarium.Models.User;

import java.util.List;

public class RoomUser {
    public LiveData<List<User>> getAllUsers(){
        return AppLocalDb.db.userDao().getAllUsers();
    }

    public interface AddUserListener{
        void onComplete();
    }
    public void addUser(User user, final UserModel.AddUserListener listener){
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDb.db.userDao().insertAll(user);
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
