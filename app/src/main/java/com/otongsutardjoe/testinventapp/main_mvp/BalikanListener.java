package com.otongsutardjoe.testinventapp.main_mvp;

import com.otongsutardjoe.testinventapp.model.ProductAndPriceModel;

import java.util.List;

public interface BalikanListener {
    void onGetDataSuccess(ProductAndPriceModel productAndPriceModel);

    void onGetDataLocalSuccess(List<ProductAndPriceModel.DataValue> dataValues);

    void onGetDataFailed(String message);
}
