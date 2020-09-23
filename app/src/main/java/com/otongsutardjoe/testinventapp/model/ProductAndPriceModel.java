package com.otongsutardjoe.testinventapp.model;

import androidx.room.Embedded;

import com.otongsutardjoe.testinventapp.local_storage.ProductLocalAndPriceLocalModel;
import com.otongsutardjoe.testinventapp.local_storage.ProductLocalModel;

import java.util.List;

import lombok.Data;

@Data
public class ProductAndPriceModel {
    private String status_code = "",
            status_message_ind = "",
            status_message_eng = "";
    private List<DataValue> value = null;

    @Data
    public static class DataValue {
        private String id = "",
                kode_barang = "",
                nama_barang = "",
                image = "",
                cabang = "";
        private float harga_barang = 0;
    }
}
