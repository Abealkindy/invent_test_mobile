package com.otongsutardjoe.testinventapp.local_storage;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Data
@Entity(tableName = "tbl_product_and_price")
public class ProductLocalAndPriceLocalModel {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    String idBarang = "";
    @ColumnInfo(name = "kode_barang")
    String kode_barang = "";
    @ColumnInfo(name = "harga_barang")
    float harga_barang = 0;
    @ColumnInfo(name = "cabang")
    String cabang = "";
}
