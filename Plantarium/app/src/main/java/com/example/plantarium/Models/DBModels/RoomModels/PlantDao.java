package com.example.plantarium.Models.DBModels.RoomModels;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.plantarium.Models.Place;
import com.example.plantarium.Models.Plant;
import com.example.plantarium.Models.User;

import java.util.List;

@Dao
public interface PlantDao {
    @Query("select * from Plant")
    LiveData<List<Plant>> getAllPlants();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Plant plant);

    @Delete
    void delete(Plant plant);

    @Query("select p.* from Plant p where p.placeId = :place_id and p.deleted = 0")
    LiveData<List<Plant>> getAllPlantsByPlaceId(String place_id);
}
