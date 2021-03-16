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
public interface PlaceDao {
    @Query("select * from Place")
    LiveData<List<Place>> getAllPlaces();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Place... place);

    @Delete
    void delete(Place place);
}
