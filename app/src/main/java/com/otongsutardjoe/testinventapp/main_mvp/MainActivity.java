package com.otongsutardjoe.testinventapp.main_mvp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.otongsutardjoe.testinventapp.R;
import com.otongsutardjoe.testinventapp.adapter.RecyclerProductAdapter;
import com.otongsutardjoe.testinventapp.databinding.ActivityMainBinding;
import com.otongsutardjoe.testinventapp.model.ProductAndPriceModel;
import com.otongsutardjoe.testinventapp.utils.Const;

import java.util.List;

import static com.otongsutardjoe.testinventapp.MyApp.localAppDB;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, BalikanListener {
    private DataPresenter dataPresenter = new DataPresenter(this);
    ActivityMainBinding mBinding;
    private boolean isAlphaDesc = false, isPriceDesc = false;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        initUI();
        initEvent();
    }

    private void initEvent() {
        mBinding.imageSortByPrice.setOnClickListener(v -> {
            if (isPriceDesc) {
                isPriceDesc = false;
                mBinding.imageSortByPrice.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.low_prices));
            } else {
                isPriceDesc = true;
                mBinding.imageSortByPrice.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.high_prices));
            }
            conditioningIf();
        });
        mBinding.imageSortByAlpha.setOnClickListener(v -> {
            if (isAlphaDesc) {
                isAlphaDesc = false;
                mBinding.imageSortByAlpha.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.alpha_asc));
            } else {
                isAlphaDesc = true;
                mBinding.imageSortByAlpha.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.alpha_desc));
            }
            conditioningIf();
        });
        mBinding.swipeRefresh.setOnRefreshListener(() -> {
            mBinding.swipeRefresh.setRefreshing(false);
            hitAPI(Const.getProductURL, MainActivity.this, "db");
        });
    }

    private void conditioningIf() {
        if (isPriceDesc && isAlphaDesc) {
            hitAPI("", MainActivity.this, "desc&desc");
        } else {
            if (!isPriceDesc && !isAlphaDesc) {
                hitAPI("", MainActivity.this, "asc&asc");
            } else if (!isPriceDesc) {
                hitAPI("", MainActivity.this, "asc&desc");
            } else {
                hitAPI("", MainActivity.this, "desc&asc");
            }
        }
    }

    private void initUI() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please kindly wait...");
        hitAPI(Const.getProductURL, this, "db");
    }

    private void hitAPI(String URL, Context context, String type) {
        progressDialog.show();
        dataPresenter.getDataApaAja(URL, context, type);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.searchBar));
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public void onGetDataSuccess(ProductAndPriceModel productAndPriceModel) {
        Log.e("resultHitWithPrice", new Gson().toJson(productAndPriceModel.getValue()));
        if (!dataPresenter.hasNotID(productAndPriceModel)) {
            progressDialog.dismiss();
            onGetDataLocalSuccess(localAppDB.productAndPriceDAO().getProductAndPriceJoin());
        }
    }

    @Override
    public void onGetDataLocalSuccess(List<ProductAndPriceModel.DataValue> dataValues) {
        progressDialog.dismiss();
        mBinding.recylerProduct.setVisibility(View.VISIBLE);
        mBinding.linearError.setVisibility(View.GONE);
        mBinding.recylerProduct.setAdapter(new RecyclerProductAdapter(this, dataValues));
    }

    @Override
    public void onGetDataFailed(String message) {
        progressDialog.dismiss();
        showErrorLayout(message);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (isNumeric(query)) {
            hitAPI(query, this, "searchByPrice");
        } else {
            hitAPI(query, this, "searchByName");
        }
        return true;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (isNumeric(newText)) {
            hitAPI(newText, this, "searchByPrice");
        } else {
            hitAPI(newText, this, "searchByName");
        }
        return true;
    }

    private void showErrorLayout(String errorMessage) {
        mBinding.recylerProduct.setVisibility(View.GONE);
        mBinding.linearError.setVisibility(View.VISIBLE);
        Glide.with(this).asGif().load(R.raw.aquacry).into(mBinding.imageError);
        mBinding.textViewErrorMessage.setText(errorMessage);
    }
}