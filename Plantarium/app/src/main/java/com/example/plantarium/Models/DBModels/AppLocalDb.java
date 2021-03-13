package com.example.plantarium.Models.DBModels;

import com.example.plantarium.Models.*;
import com.example.plantarium.Models.DBModels.RoomModels.UserDao;
import com.example.plantarium.MyApplication;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Place.class, PlaceMember.class, Plant.class, Watering.class}, version = 1)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract UserDao userDao();
}
public class AppLocalDb{
    static public AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.context,
                    AppLocalDbRepository.class,
                    "dbFileName.db")
                    .fallbackToDestructiveMigration()
                    .build();
}