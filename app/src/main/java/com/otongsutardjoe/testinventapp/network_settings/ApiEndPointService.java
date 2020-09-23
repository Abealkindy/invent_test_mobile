package com.otongsutardjoe.testinventapp.network_settings;

import com.otongsutardjoe.testinventapp.model.ProductAndPriceModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiEndPointService {
    //test_core/v1/get_m_product & test_core/v1/get_product_price
    @GET
    Observable<ProductAndPriceModel> getApaAjaData(
            @Url String url
    );
}