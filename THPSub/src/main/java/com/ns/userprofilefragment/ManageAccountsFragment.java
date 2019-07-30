package com.ns.userprofilefragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.netoperation.net.ApiManager;
import com.netoperation.util.NetConstants;
import com.ns.alerts.Alerts;
import com.ns.loginfragment.BaseFragmentTHP;
import com.ns.loginfragment.OTPVerificationFragment;
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.R;
import com.ns.utils.CommonUtil;
import com.ns.utils.FragmentUtil;
import com.ns.utils.ResUtil;
import com.ns.utils.THPConstants;
import com.ns.view.CustomProgressBar;
import com.ns.view.CustomTextView;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class ManageAccountsFragment extends BaseFragmentTHP {

    private String mFrom = "";

    private CustomTextView deleteAccountBtn_Txt;
    private CustomProgressBar progressBarSuspend;

    private CustomTextView suspendAccountBtn_Txt;
    private CustomProgressBar progressBarDelete;
    private ImageButton backBtn;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_manage_accounts;
    }

    public static ManageAccountsFragment getInstance(String from) {
        ManageAccountsFragment fragment = new ManageAccountsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            mFrom = getArguments().getString("from");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backBtn = view.findViewById(R.id.backBtn);
        suspendAccountBtn_Txt = view.findViewById(R.id.suspendAccountBtn_Txt);
        progressBarSuspend = view.findViewById(R.id.progressBarSuspend);
        deleteAccountBtn_Txt = view.findViewById(R.id.deleteAccountBtn_Txt);
        progressBarDelete = view.findViewById(R.id.progressBarDelete);

        // Back button click listener
        backBtn.setOnClickListener(v->{
            FragmentUtil.clearSingleBackStack((AppCompatActivity)getActivity());
        });


        // Suspend button click listener
        view.findViewById(R.id.suspendAccountBtn_Txt).setOnClickListener(v->{
            openOtpVerificationScreen(THPConstants.FROM_SUSPEND_ACCOUNT);
        });

        // Delete button click listener
        view.findViewById(R.id.deleteAccountBtn_Txt).setOnClickListener(v->{
            openOtpVerificationScreen(THPConstants.FROM_DELETE_ACCOUNT);
        });

    }

    /**
     * Opening OTP Verification Screen
     * @param from
     */
    private void openOtpVerificationScreen(String from) {
        enableViews(from, true);
        OTPVerificationFragment fragment = OTPVerificationFragment.getInstance(from);
        FragmentUtil.pushFragmentAnim((AppCompatActivity)getActivity(), R.id.parentLayout, fragment,
                FragmentUtil.FRAGMENT_ANIMATION, false);
        fragment.setOtpVerification(new OTPVerificationFragment.OnOtpVerification() {
            @Override
            public void onOtpVerification(boolean isOtpVerified, String otp) {
                if(from.equalsIgnoreCase(THPConstants.FROM_SUSPEND_ACCOUNT)) {
                    suspendAccount(otp);
                }
                else if(from.equalsIgnoreCase(THPConstants.FROM_DELETE_ACCOUNT)) {
                    deleteAccount(otp);
                }
            }
        });
    }


    private void enableViews(String from, boolean isEnable) {
        if(from == null) {
            suspendAccountBtn_Txt.setEnabled(true);
            deleteAccountBtn_Txt.setEnabled(true);
            backBtn.setEnabled(true);
            progressBarSuspend.setVisibility(View.GONE);
            progressBarDelete.setVisibility(View.GONE);
            return;
        }
        backBtn.setEnabled(isEnable);
        suspendAccountBtn_Txt.setEnabled(isEnable);
        deleteAccountBtn_Txt.setEnabled(isEnable);
        if(from.equalsIgnoreCase(THPConstants.FROM_DELETE_ACCOUNT) && isEnable) {
            progressBarDelete.setVisibility(View.VISIBLE);
            progressBarSuspend.setVisibility(View.GONE);
        }
        else if(from.equalsIgnoreCase(THPConstants.FROM_SUSPEND_ACCOUNT) && isEnable) {
            progressBarDelete.setVisibility(View.GONE);
            progressBarSuspend.setVisibility(View.VISIBLE);
        }
        else {
            progressBarDelete.setVisibility(View.GONE);
            progressBarSuspend.setVisibility(View.GONE);
        }
    }


    private void suspendAccount(String otp) {
        enableViews(THPConstants.FROM_SUSPEND_ACCOUNT, true);
        ApiManager.getUserProfile(getActivity())
                .map(userProfile -> {
                    ApiManager.suspendAccount(userProfile.getUserId(), BuildConfig.SITEID,
                            ResUtil.getDeviceId(getActivity()), userProfile.getEmailId(), userProfile.getContact(), otp)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(keyValueModel -> {
                                if(keyValueModel.getState().equalsIgnoreCase("success")) {
                                    Alerts.showAlertDialogOKBtn(getActivity(), "Congratulations", "Yours account is suspended.");
                                } else {
                                    Alerts.showAlertDialogOKBtn(getActivity(), "", keyValueModel.getName());
                                }
                            }, throwable -> {
                                enableViews(null, false);
                                Alerts.showErrorDailog(getChildFragmentManager(), null, throwable.getLocalizedMessage());
                            }, () ->{
                                enableViews(null, false);
                            });
                    return userProfile;
                })
                .subscribe();
    }

    private void deleteAccount(String otp) {
        enableViews(THPConstants.FROM_DELETE_ACCOUNT, true);
        ApiManager.getUserProfile(getActivity())
                .map(userProfile -> {
                    ApiManager.deleteAccount(userProfile.getUserId(), BuildConfig.SITEID,
                            ResUtil.getDeviceId(getActivity()), userProfile.getEmailId(), userProfile.getContact(), otp)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(keyValueModel -> {
                                if(keyValueModel.getState().equalsIgnoreCase("success")) {
                                    Alerts.showAlertDialogOKBtn(getActivity(), "Congratulations", "Yours account is deleted.");
                                } else {
                                    Alerts.showAlertDialogOKBtn(getActivity(), "", keyValueModel.getName());
                                }
                            }, throwable -> {
                                enableViews(null, false);
                                Alerts.showErrorDailog(getChildFragmentManager(), null, throwable.getLocalizedMessage());
                            }, () ->{
                                enableViews(null, false);
                            });
                    return userProfile;
                })
                .subscribe();
    }





}
