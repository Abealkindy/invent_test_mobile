package com.otongsutardjoe.testinventapp.local_storage;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {ProductLocalModel.class, ProductLocalAndPriceLocalModel.class}, version = 1)
public abstract class LocalAppDB extends RoomDatabase {
    public abstract ProductAndPriceDAO productAndPriceDAO();
}
