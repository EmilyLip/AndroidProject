package com.example.plantarium.Models.DBModels.RoomModels;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.plantarium.Models.Watering;

import java.util.List;

@Dao
public interface WateringDao {
    @Query("select * from Watering")
    LiveData<List<Watering>> getAllWaterings();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Watering watering);

    @Delete
    void delete(Watering watering);

    @Query("select w.* from Watering w where w.plantId = :plant_id and w.deleted = 0 ORDER BY wateringDate DESC")
    LiveData<List<Watering>> getWateringsByPlantId(String plant_id);

//    @Query("select w.* from Watering w where w.plantId = :plant_id and w.deleted = 0 ORDER BY wateringDate DESC")
//    Watering getPlantLastWatering(String plant_id);
}