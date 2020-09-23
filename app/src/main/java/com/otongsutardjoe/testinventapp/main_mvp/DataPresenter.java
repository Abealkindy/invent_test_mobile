package com.otongsutardjoe.testinventapp.main_mvp;

import android.content.Context;

import com.otongsutardjoe.testinventapp.local_storage.ProductLocalAndPriceLocalModel;
import com.otongsutardjoe.testinventapp.local_storage.ProductLocalModel;
import com.otongsutardjoe.testinventapp.model.ProductAndPriceModel;
import com.otongsutardjoe.testinventapp.network_settings.ApiEndPointService;
import com.otongsutardjoe.testinventapp.network_settings.InternetConnection;
import com.otongsutardjoe.testinventapp.network_settings.RetrofitConfig;
import com.otongsutardjoe.testinventapp.utils.Const;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.otongsutardjoe.testinventapp.MyApp.localAppDB;

public class DataPresenter {
    private BalikanListener balikanListener;

    public DataPresenter(BalikanListener balikanListener) {
        this.balikanListener = balikanListener;
    }

    public void getDataApaAja(String url, Context context, String type) {
        if (type.equalsIgnoreCase("db")) {
            if (InternetConnection.checkConnection(context)) {
                ApiEndPointService apiEndPointService = RetrofitConfig.getInitRetrofit();
                apiEndPointService.getApaAjaData(url)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ProductAndPriceModel>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ProductAndPriceModel productAndPriceModel) {
                                if (productAndPriceModel.getStatus_code().equalsIgnoreCase(Const.success_code)) {
                                    if (hasNotID(productAndPriceModel)) {
                                        getDataApaAja(Const.getProductAndPriceURL, context, "db");
                                    }
                                    isiDataLocal(productAndPriceModel);
                                    balikanListener.onGetDataSuccess(productAndPriceModel);
                                } else {
                                    hitFailedHandler(localAppDB.productAndPriceDAO().getProductAndPriceJoin(), productAndPriceModel.getStatus_message_eng());
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                hitFailedHandler(localAppDB.productAndPriceDAO().getProductAndPriceJoin(), e.getMessage());
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            } else {
                hitFailedHandler(localAppDB.productAndPriceDAO().getProductAndPriceJoin(), "No internet connection!");
            }
        } else if (type.equalsIgnoreCase("searchByName")) {
            List<ProductAndPriceModel.DataValue> dataValueList = localAppDB.productAndPriceDAO().findByName("%" + url + "%");
            hitFailedHandler(dataValueList, "Data not found!");
        } else if (type.equalsIgnoreCase("searchByPrice")) {
            List<ProductAndPriceModel.DataValue> dataValueList = localAppDB.productAndPriceDAO().findByPrice("%" + url + "%");
            hitFailedHandler(dataValueList, "Data not found!");
        } else if (type.equalsIgnoreCase("desc&desc")) {
            List<ProductAndPriceModel.DataValue> dataValueList = localAppDB.productAndPriceDAO().sortByNameAndPriceDESC();
            hitFailedHandler(dataValueList, "Data not found!");
        } else if (type.equalsIgnoreCase("asc&asc")) {
            List<ProductAndPriceModel.DataValue> dataValueList = localAppDB.productAndPriceDAO().sortByNameAndPriceASC();
            hitFailedHandler(dataValueList, "Data not found!");
        } else if (type.equalsIgnoreCase("asc&desc")) {
            List<ProductAndPriceModel.DataValue> dataValueList = localAppDB.productAndPriceDAO().sortByNameAndPriceDESCnASC();
            hitFailedHandler(dataValueList, "Data not found!");
        } else if (type.equalsIgnoreCase("desc&asc")) {
            List<ProductAndPriceModel.DataValue> dataValueList = localAppDB.productAndPriceDAO().sortByNameAndPriceASCnDESC();
            hitFailedHandler(dataValueList, "Data not found!");
        }

    }

    private boolean validateList(List<ProductAndPriceModel.DataValue> dataValueList) {
        return dataValueList != null && !dataValueList.isEmpty();
    }

    private void hitFailedHandler(List<ProductAndPriceModel.DataValue> dataValues, String status_message_eng) {
        if (validateList(dataValues)) {
            balikanListener.onGetDataLocalSuccess(dataValues);
        } else {
            balikanListener.onGetDataFailed(status_message_eng);
        }
    }

    public boolean hasNotID(ProductAndPriceModel productAndPriceModel) {
        return productAndPriceModel.getValue().get(0).getId().isEmpty();
    }

    private void isiDataLocal(ProductAndPriceModel productAndPriceModel) {
        if (hasNotID(productAndPriceModel)) {
            for (int position = 0; position < productAndPriceModel.getValue().size(); position++) {
                ProductLocalModel productLocalModel = new ProductLocalModel();
                productLocalModel.setKodeBarang(productAndPriceModel.getValue().get(position).getKode_barang());
                productLocalModel.setNamaBarang(productAndPriceModel.getValue().get(position).getNama_barang());
                productLocalModel.setImage(productAndPriceModel.getValue().get(position).getImage());
                localAppDB.productAndPriceDAO().insertProductData(productLocalModel);
            }
        } else {
            for (int position = 0; position < productAndPriceModel.getValue().size(); position++) {
                ProductLocalAndPriceLocalModel productLocalAndPriceLocalModel = new ProductLocalAndPriceLocalModel();
                productLocalAndPriceLocalModel.setCabang(productAndPriceModel.getValue().get(position).getCabang());
                productLocalAndPriceLocalModel.setHarga_barang(productAndPriceModel.getValue().get(position).getHarga_barang());
                productLocalAndPriceLocalModel.setIdBarang(productAndPriceModel.getValue().get(position).getId());
                productLocalAndPriceLocalModel.setKode_barang(productAndPriceModel.getValue().get(position).getKode_barang());
                localAppDB.productAndPriceDAO().insertProductAndPriceData(productLocalAndPriceLocalModel);
            }
        }
    }
}
