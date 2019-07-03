package com.ns.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.netoperation.model.TxnDataBean;
import com.netoperation.model.UserProfile;
import com.netoperation.net.ApiManager;
import com.netoperation.retrofit.ServiceAPIs;
import com.netoperation.retrofit.ServiceFactory;
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
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.R;
import com.ns.userprofilefragment.UserProfileFragment;
import com.ns.utils.FragmentUtil;
import com.ns.utils.IntentUtil;
import com.ns.utils.NetUtils;
import com.ns.utils.THPConstants;
import com.ns.utils.TextUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class UserProfileActivity extends BaseAcitivityTHP implements OnSubscribeBtnClick, OnPlanInfoLoad {

    private String mSelectedPlanId;
    private static final int INAPP_SUBSCRIPTION_REQUEST_CODE = 10001;
    private IabHelper mHelper;

    private OnSubscribeEvent mOnSubscribeEvent;
    private UserProfile mUserProfile;
    private TxnDataBean mSelectedBean;



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

        // Gets User Profile Data
        ApiManager.getUserProfile(this).subscribe(userProfile -> {
            mUserProfile = userProfile;
        });

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

        if(mUserProfile == null || mUserProfile.getUserId() == null || TextUtils.isEmpty(mUserProfile.getUserId())) {
            IntentUtil.openSignInOrUpActivity(this, "signIn");
            return;
        }

        if(mHelper != null) {
            mHelper.flagEndAsync();
        }

        mSelectedPlanId = bean.getPlanId();
        mSelectedBean = bean;
        IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
                = new IabHelper.OnIabPurchaseFinishedListener() {
            public void onIabPurchaseFinished(IabResult result,
                                              Purchase purchase) {

                Log.i("", "");
                if (result.isFailure()) {
                    // Handle error
                    Alerts.showAlertDialogOKBtn(UserProfileActivity.this, "Purchase Error!", result.getMessage());
                    return;
                } else if (purchase.getSku().equals(mSelectedPlanId)) {
                    loadInventory();
                    // buyButton.setEnabled(false);
                    //Submit subscription purchase details
                    createSubscription();
                }

            }
        };

        boolean isSubscription = mHelper.subscriptionsSupported();
        Log.i("", "");
        mHelper.launchSubscriptionPurchaseFlow(UserProfileActivity.this, mSelectedPlanId, INAPP_SUBSCRIPTION_REQUEST_CODE,
                mPurchaseFinishedListener);

        //Testing create subscription
        //createSubscription();
    }

    @SuppressLint("CheckResult")
    private void createSubscription() {
        String email = mUserProfile.getEmailId();
        String contact = mUserProfile.getContact();
        String prefContact = email;
        if(email == null || TextUtils.isEmpty(email)) {
            prefContact = contact;
        }
        //Alerts

        String currency = "inr";
        if(mSelectedBean.getAmount() == 0.0) {
            currency = null;
        }
        ApiManager.createSubscription(mUserProfile.getUserId(), "txnId", "amt", "WEB", BuildConfig.SITEID,
                mSelectedBean.getPlanId(), mSelectedBean.getPlanType(),"GooglePay", mSelectedBean.getValidity(),
                prefContact, currency, null, ""+mSelectedBean.getNetamount())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(keyValueModel -> {
            // TODO, fail and success conditional implementation
            Alerts.showToast(this, keyValueModel.getName());
        }, throwable -> {
            // TODO, Handle Error and network interruption
            Log.i("", "");
            if(NetUtils.isConnected(this)) {
                Alerts.showErrorDailog(getSupportFragmentManager(), "Error", "Purchase could not be completed");
            } else {
                Alerts.showSnackbar(this, "Connect to Internet");
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
                    mHelper.consumeAsync(inventory.getPurchase(mSelectedPlanId),
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
