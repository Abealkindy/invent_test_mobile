package com.otongsutardjoe.testinventapp.local_storage;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Data
@Entity(tableName = "tbl_product")
public class ProductLocalModel {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "kode_barang")
    String kodeBarang = "";
    @ColumnInfo(name = "nama_barang")
    String namaBarang = "";
    @ColumnInfo(name = "image")
    String image = "";
}
