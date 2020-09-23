package com.otongsutardjoe.testinventapp;

import android.app.Application;

import androidx.room.Room;

import com.otongsutardjoe.testinventapp.local_storage.LocalAppDB;

public class MyApp extends Application {
    public static LocalAppDB localAppDB;

    @Override
    public void onCreate() {
        super.onCreate();
        localAppDB = Room.databaseBuilder(
                getApplicationContext(),
                LocalAppDB.class,
                "local_data_db"
        )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }
}
