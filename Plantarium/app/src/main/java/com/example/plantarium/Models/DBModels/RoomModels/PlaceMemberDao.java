package com.example.plantarium.Models.DBModels.RoomModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.plantarium.Models.Place;
import com.example.plantarium.Models.PlaceMember;
import com.example.plantarium.Models.User;

import java.util.List;

@Dao
public interface PlaceMemberDao {
    @Query("select * from PlaceMember")
    LiveData<List<PlaceMember>> getAllPlaceMembers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(PlaceMember... placeMember);

    @Delete
    void delete(PlaceMember placeMember);

    @Query("select * from PlaceMember pm where pm.placeId= :place_id and pm.userEmail = :user_email and pm.deleted=0")
    PlaceMember getPlaceMemberByPlaceAndUser(String user_email, String place_id);
}
