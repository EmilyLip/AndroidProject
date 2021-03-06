package com.example.plantarium.Models.DBModels.RoomModels;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.plantarium.Models.*;
import com.example.plantarium.MyApplication;

@Database(entities = {User.class, Plant.class, PlaceMember.class, Watering.class, Place.class}, version = 41)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract PlaceDao placeDao();
    public abstract PlaceMemberDao placeMemberDao();
    public abstract PlantDao plantDao();
    public abstract WateringDao wateringDao();
}

public class AppLocalDb{
    static public AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.context,
                    AppLocalDbRepository.class,
                    "dbFileClean.db")
                    .fallbackToDestructiveMigration()
                    .build();
}
