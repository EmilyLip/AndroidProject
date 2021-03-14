package com.example.plantarium.Models.DBModels;

import com.example.plantarium.Models.DBModels.FirebaseModels.FirebaseUser;
import com.example.plantarium.Models.DBModels.RoomModels.RoomUser;
import com.example.plantarium.Models.User;

public class UserModel {
    public final static UserModel instance = new UserModel();

    FirebaseUser modelFirebase = new FirebaseUser();
    RoomUser modelSql = new RoomUser();

    public UserModel(){

    }

    public interface Listener<T>{
        void onComplete(T result);
    }

    public interface GetUsertListener{
        void onComplete(User user);
    }

    public interface AddUserListener{
        void onComplete();
    }

    public void getUserByID(String userId, final GetUsertListener listener){
        modelFirebase.getUserByID(userId, listener);
    }

    public void updateUser(User user, final AddUserListener listener){
        modelFirebase.updateUser(user, listener);
        modelSql.addUser(user, listener);
    }
}
