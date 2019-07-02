package com.ns.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.netoperation.model.TxnDataBean;
import com.ns.alerts.Alerts;
import com.ns.callbacks.OnPlanInfoLoad;
import com.ns.callbacks.OnSubscribeBtnClick;
import com.ns.callbacks.OnSubscribeEvent;
import com.ns.loginfragment.SubscriptionStep_1_Fragment;
import com.ns.payment.IabException;
import com.ns.payment.IabHelper;
import com.ns.payment.IabResult;
import com.ns.payment.Inventory;
import com.ns.payment.Purchase;
import com.ns.thpremium.R;
import com.ns.userprofilefragment.UserProfileFragment;
import com.ns.utils.FragmentUtil;
import com.ns.utils.THPConstants;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends BaseAcitivityTHP implements OnSubscribeBtnClick, OnPlanInfoLoad {

    private static final String ITEM_SKU = "free_subscription";
    private static final int INAPP_SUBSCRIPTION_REQUEST_CODE = 10001;
    private IabHelper mHelper;

    private OnSubscribeEvent mOnSubscribeEvent;

    public void setOnSubscribeEvent(OnSubscribeEvent onSubscribeEvent) {
        mOnSubscribeEvent = onSubscribeEvent;
    }

    @Override
    public int layoutRes() {
        return R.layout.activity_userprofile;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String from = getIntent().getExtras().getString("from");

        if(from!= null && from.equalsIgnoreCase(THPConstants.FROM_SUBSCRIPTION_EXPLORE)) {
            SubscriptionStep_1_Fragment fragment = SubscriptionStep_1_Fragment.getInstance(from);
            FragmentUtil.pushFragmentAnim(this, R.id.parentLayout, fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, true);
        }
        else if(from!= null && from.equalsIgnoreCase(THPConstants.FROM_USER_PROFILE)) {
            UserProfileFragment fragment = UserProfileFragment.getInstance("");
            FragmentUtil.pushFragmentAnim(this, R.id.parentLayout, fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, true);
        }

    }

    private List<String> mPlanIds = new ArrayList<>();


    @Override
    public void onPlansLoaded(List<TxnDataBean> planInfoList) {
        for(TxnDataBean bean : planInfoList) {
            mPlanIds.add(bean.getPlanId());
        }
        initIabHelper();
    }

    private void initIabHelper() {
        mHelper = new IabHelper(UserProfileActivity.this, THPConstants.SUBSCRIPTION_BASE64);
        mHelper.enableDebugLogging(true);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {
                Log.i("", "");

                if(result.isSuccess()) {
                    try {
                        Inventory inventory = mHelper.queryInventory(true, mPlanIds);

                        Log.i("", "");
                    } catch (IabException e) {
                        e.printStackTrace();
                        Log.i("", "");
                    }
                }
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == INAPP_SUBSCRIPTION_REQUEST_CODE) {
            if(requestCode == RESULT_OK) {
                if(mOnSubscribeEvent != null) {
                    mOnSubscribeEvent.onSubscribeEvent(true);
                }
            }
            else if(requestCode == RESULT_CANCELED) {
                if(mOnSubscribeEvent != null) {
                    mOnSubscribeEvent.onSubscribeEvent(false);
                }
            }


        }
    }

    @Override
    public void onSubscribeBtnClick(TxnDataBean bean) {

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
                    Alerts.showAlertDialogOKBtn(UserProfileActivity.this, "Purchase Error!", result.getMessage());
                    return;
                } else if (purchase.getSku().equals(ITEM_SKU)) {
                    loadInventory();
                    // buyButton.setEnabled(false);
                }

            }
        };

        boolean isSubscription = mHelper.subscriptionsSupported();
        Log.i("", "");
        mHelper.launchSubscriptionPurchaseFlow(UserProfileActivity.this, ITEM_SKU, INAPP_SUBSCRIPTION_REQUEST_CODE,
                mPurchaseFinishedListener);
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



}
