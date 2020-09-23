package com.otongsutardjoe.testinventapp.local_storage;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.otongsutardjoe.testinventapp.model.ProductAndPriceModel;

import java.util.List;

@Dao
public interface ProductAndPriceDAO {
    //product only
    @Query("SELECT * FROM tbl_product ORDER BY kode_barang ASC")
    List<ProductLocalModel> getProductDataOnly();

    //product and price only
    @Query("SELECT * FROM tbl_product_and_price ORDER BY id ASC")
    List<ProductLocalAndPriceLocalModel> getProductDataWithPrice();

    //product and price join
    @Query("SELECT tbl_product_and_price.id," +
            "tbl_product_and_price.kode_barang," +
            "tbl_product.nama_barang," +
            "tbl_product.image," +
            "tbl_product_and_price.cabang," +
            "tbl_product_and_price.harga_barang" +
            " FROM tbl_product_and_price " +
            "JOIN tbl_product ON tbl_product_and_price.kode_barang=tbl_product.kode_barang " +
            "GROUP BY tbl_product_and_price.id")
    List<ProductAndPriceModel.DataValue> getProductAndPriceJoin();

    @Query("SELECT tbl_product_and_price.id," +
            "tbl_product_and_price.kode_barang," +
            "tbl_product.nama_barang," +
            "tbl_product.image," +
            "tbl_product_and_price.cabang," +
            "tbl_product_and_price.harga_barang" +
            " FROM tbl_product_and_price " +
            "JOIN tbl_product ON tbl_product_and_price.kode_barang=tbl_product.kode_barang " +
            "ORDER BY tbl_product.nama_barang ASC, tbl_product_and_price.harga_barang ASC")
    List<ProductAndPriceModel.DataValue> sortByNameAndPriceASC();

    @Query("SELECT tbl_product_and_price.id," +
            "tbl_product_and_price.kode_barang," +
            "tbl_product.nama_barang," +
            "tbl_product.image," +
            "tbl_product_and_price.cabang," +
            "tbl_product_and_price.harga_barang" +
            " FROM tbl_product_and_price " +
            "JOIN tbl_product ON tbl_product_and_price.kode_barang=tbl_product.kode_barang " +
            "ORDER BY tbl_product.nama_barang DESC, tbl_product_and_price.harga_barang DESC")
    List<ProductAndPriceModel.DataValue> sortByNameAndPriceDESC();

    @Query("SELECT tbl_product_and_price.id," +
            "tbl_product_and_price.kode_barang," +
            "tbl_product.nama_barang," +
            "tbl_product.image," +
            "tbl_product_and_price.cabang," +
            "tbl_product_and_price.harga_barang" +
            " FROM tbl_product_and_price " +
            "JOIN tbl_product ON tbl_product_and_price.kode_barang=tbl_product.kode_barang " +
            "ORDER BY tbl_product.nama_barang ASC, tbl_product_and_price.harga_barang DESC")
    List<ProductAndPriceModel.DataValue> sortByNameAndPriceASCnDESC();

    @Query("SELECT tbl_product_and_price.id," +
            "tbl_product_and_price.kode_barang," +
            "tbl_product.nama_barang," +
            "tbl_product.image," +
            "tbl_product_and_price.cabang," +
            "tbl_product_and_price.harga_barang" +
            " FROM tbl_product_and_price " +
            "JOIN tbl_product ON tbl_product_and_price.kode_barang=tbl_product.kode_barang " +
            "ORDER BY tbl_product.nama_barang DESC, tbl_product_and_price.harga_barang ASC")
    List<ProductAndPriceModel.DataValue> sortByNameAndPriceDESCnASC();

    @Query("SELECT tbl_product_and_price.id," +
            "tbl_product_and_price.kode_barang," +
            "tbl_product.nama_barang," +
            "tbl_product.image," +
            "tbl_product_and_price.cabang," +
            "tbl_product_and_price.harga_barang" +
            " FROM tbl_product_and_price " +
            "JOIN tbl_product ON tbl_product_and_price.kode_barang=tbl_product.kode_barang " +
            "WHERE tbl_product.nama_barang LIKE :namaBarang ")
    List<ProductAndPriceModel.DataValue> findByName(String namaBarang);

    @Query("SELECT tbl_product_and_price.id," +
            "tbl_product_and_price.kode_barang," +
            "tbl_product.nama_barang," +
            "tbl_product.image," +
            "tbl_product_and_price.cabang," +
            "tbl_product_and_price.harga_barang" +
            " FROM tbl_product_and_price " +
            "JOIN tbl_product ON tbl_product_and_price.kode_barang=tbl_product.kode_barang " +
            "WHERE tbl_product_and_price.harga_barang LIKE :hargaBarang ")
    List<ProductAndPriceModel.DataValue> findByPrice(String hargaBarang);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProductData(ProductLocalModel... productLocalModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProductAndPriceData(ProductLocalAndPriceLocalModel... productLocalModel);

    @Query("DELETE FROM tbl_product WHERE kode_barang = :kodeBarang")
    void deleteProductItem(String kodeBarang);

    @Query("DELETE FROM tbl_product_and_price WHERE id = :idBarang")
    void deleteProductAndPriceItem(String idBarang);

    @Query("DELETE FROM tbl_product")
    void deleteAllProductItem();

    @Query("DELETE FROM tbl_product_and_price")
    void deleteAllProductAndPriceItem();
}
