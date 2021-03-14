package com.example.plantarium.Models.DBModels.RoomModels;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.plantarium.Models.PlaceMember;
import com.example.plantarium.Models.User;

import java.util.List;

@Dao
public interface PlaceMemberDao {
    @Query("select * from User")
    LiveData<List<PlaceMember>> getAllPlaceMembers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(PlaceMember... placeMember);

    @Delete
    void delete(PlaceMember placeMember);
}
