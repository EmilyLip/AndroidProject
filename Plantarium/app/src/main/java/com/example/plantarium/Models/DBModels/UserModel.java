package com.example.plantarium.Models.DBModels;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;

import com.example.plantarium.Models.DBModels.FirebaseModels.FirebaseUser;
import com.example.plantarium.Models.DBModels.RoomModels.RoomUser;
import com.example.plantarium.Models.User;
import com.example.plantarium.MyApplication;

import java.util.List;

public class UserModel {
    public final static UserModel instance = new UserModel();
    FirebaseUser modelFirebase = new FirebaseUser();
    RoomUser modelSql = new RoomUser();

    public interface GetUsertListener{
        void onComplete(User user);
    }

    public interface AddUserListener{
        void onComplete();
    }

    public void getUserByID(String userId, final GetUsertListener listener){
        modelFirebase.getUserByID(userId, listener);
    }

    public interface  UpdateUserListener {
        void onComplete();
    }

    public void updateUser(User user, final AddUserListener listener){
        modelFirebase.updateUser(user, new AddUserListener() {
            @Override
            public void onComplete() {
                refreshAllUsers(new GetAllUsersListener() {
                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }});
            }
        });
    }

    public interface  GetAllUsersListListener {
        void onComplete(List<User> users);
    }

    LiveData<List<User>> users;
    public LiveData<List<User>> getAllPlaceMembersUser(String place_id) {
            users = modelSql.getAllPlaceMembersUser(place_id);
            refreshAllUsers(null);
            return users;
    }

    public interface  GetAllUsersListener {
        void onComplete();
    }
    public void refreshAllUsers(final UserModel.GetAllUsersListener listener) {
        //1. get local last update date
        final SharedPreferences sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
        long lastUpdated = sp.getLong("lastUpdatedUserClean",0);
        //2. get all updated record from firebase from the last update date
        modelFirebase.getAllUsers(lastUpdated, new FirebaseUser.GetAllUsersListener() {
            @Override
            public void onComplete(List<User> result) {
                //3. insert the new updates to the local db
                long lastU = 0;
                for (User s: result) {
                    modelSql.addUser(s,null);
                    if (s.getLastUpdated()>lastU){
                        lastU = s.getLastUpdated();
                    }
                }
                //4. update the local last update date
                sp.edit().putLong("lastUpdatedUser", lastU).commit();
                //5. return the updates data to the listeners
                if(listener != null){
                    listener.onComplete();
                }
            }
        });
    }
}
