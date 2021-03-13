package com.example.plantarium.Models.DBModels.RoomModels;

import androidx.lifecycle.LiveData;

import com.example.plantarium.Models.User;

import java.util.List;

public class RoomUser {
    public LiveData<List<User>> getAllUsers(){
        return AppLocalDb.db.userDao().getAllStudents();
    }
}
