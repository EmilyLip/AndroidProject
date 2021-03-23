package com.example.plantarium.Models.DBModels.RoomModels;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.plantarium.Models.Place;
import com.example.plantarium.Models.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("select * from User")
    LiveData<List<User>> getAllUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... user);

    @Delete
    void delete(User user);

    @Query("select u.* from User u, PlaceMember pm where u.email = pm.userEmail and pm.placeId = :place_id and pm.deleted = 0")
    LiveData<List<User>> getAllPlaceMembersUser(String place_id);
}
