package com.ns.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;

import com.netoperation.net.ApiManager;
import com.netoperation.retrofit.ServiceFactory;
import com.ns.alerts.Alerts;
import com.ns.contentfragment.AppTabFragment;
import com.ns.payment.IabException;
import com.ns.payment.IabHelper;
import com.ns.payment.IabResult;
import com.ns.payment.Inventory;
import com.ns.payment.Purchase;
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;
import com.ns.utils.THPConstants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class AppTabActivity extends BaseAcitivityTHP {

    private IabHelper mHelper;
    private Button mPlanBtn;
//    static final String ITEM_SKU = "38";
    static final String ITEM_SKU = "free_subscription";
    static final int INAPP_SUBSCRIPTION_REQUEST_CODE = 10001;

    @Override
    public int layoutRes() {
        return R.layout.activity_apptab;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ServiceFactory.BASE_URL = BuildConfig.BASE_URL;

        ApiManager.getUserProfile(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userProfile -> {
                    AppTabFragment fragment = AppTabFragment.getInstance("", userProfile.getUserId());

                    FragmentUtil.pushFragmentAnim(this, R.id.parentLayout, fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, true);
                });



        // IN APP SUBSCRIPTION TESTing
        initIabHelper();

        mPlanBtn = findViewById(R.id.planBtn);

        mPlanBtn.setOnClickListener(v->{

            if(mHelper != null) {
                mHelper.flagEndAsync();
            }

            IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
                    = new IabHelper.OnIabPurchaseFinishedListener() {
                public void onIabPurchaseFinished(IabResult result,
                                                  Purchase purchase) {

                    Log.i("", "");
                    if (result.isFailure()) {
                        // Handle error
                        Alerts.showAlertDialogOKBtn(AppTabActivity.this, "Purchase Error!", result.getMessage());
                        return;
                    } else if (purchase.getSku().equals(ITEM_SKU)) {
                        loadInventory();
                        // buyButton.setEnabled(false);
                    }

                }
            };

            boolean isSubscription = mHelper.subscriptionsSupported();
            Log.i("", "");
            mHelper.launchSubscriptionPurchaseFlow(this, ITEM_SKU, INAPP_SUBSCRIPTION_REQUEST_CODE,
                    mPurchaseFinishedListener);
        });





    }


    private void initIabHelper() {
        mHelper = new IabHelper(this, THPConstants.SUBSCRIPTION_BASE64);
        mHelper.enableDebugLogging(true);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {
                Log.i("", "");

                if(result.isSuccess()) {
                    List<String> planIds = new ArrayList<>();
                    planIds.add("39");
                    planIds.add("40");
                    planIds.add("38");
                    try {
                        Inventory inventory = mHelper.queryInventory(true, planIds);

                        Log.i("", "");
                    } catch (IabException e) {
                        e.printStackTrace();
                        Log.i("", "");
                    }
                }
            }
        });


    }




    public void loadInventory() {
        IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener
                = new IabHelper.QueryInventoryFinishedListener() {
            public void onQueryInventoryFinished(IabResult result,
                                                 Inventory inventory) {

                Log.i("", "");

                if (result.isFailure()) {
                    // Handle failure
                } else {
                    mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU),
                            mConsumeFinishedListener);
                }
            }
        };
        mHelper.queryInventoryAsync(mReceivedInventoryListener);
    }



    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                public void onConsumeFinished(Purchase purchase,
                                              IabResult result) {

                    Log.i("", "");

                    if (result.isSuccess()) {
                        //clickButton.setEnabled(true);
                    } else {
                        // handle error
                    }
                }
            };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == INAPP_SUBSCRIPTION_REQUEST_CODE) {
            if(requestCode == RESULT_OK) {

            }
            else if(requestCode == RESULT_CANCELED) {

            }
        }
    }
}
