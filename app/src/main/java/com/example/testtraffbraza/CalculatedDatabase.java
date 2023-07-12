package com.example.testtraffbraza;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CalculationData.class}, version = 1)
public abstract class CalculatedDatabase extends RoomDatabase {

    private static CalculatedDatabase instance = null;
    private final static String DB_NAME = "calculatedData.db";

    public static CalculatedDatabase getInstance(Application application) {
        if (instance == null) {
            instance = Room.databaseBuilder(application, CalculatedDatabase.class, DB_NAME).build();
        }
        return instance;
    }

    public abstract CalculationsDao calculationsDao();
}

