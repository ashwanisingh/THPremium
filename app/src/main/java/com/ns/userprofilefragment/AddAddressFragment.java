package com.ns.userprofilefragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.load.HttpException;
import com.netoperation.model.UserProfile;
import com.netoperation.net.ApiManager;
import com.ns.alerts.Alerts;
import com.ns.loginfragment.BaseFragmentTHP;
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;
import com.ns.view.CustomProgressBar;
import com.ns.view.CustomTextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class AddAddressFragment extends BaseFragmentTHP {

    private String mFrom = "";

    public static AddAddressFragment getInstance(String from) {
        AddAddressFragment fragment = new AddAddressFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_add_address;
    }

    private UserProfile mUserProfile;

    private TextInputEditText flatNoET;
    private TextInputEditText addressLine1ET;
    private TextInputEditText pincodeET;
    private TextInputEditText cityET;
    private TextInputEditText stateET;
    private TextInputEditText landmarkET;

    private CustomTextView homeBtn_Txt;
    private CustomTextView officeBtn_Txt;
    private CustomTextView othersBtn_Txt;
    private CustomTextView saveAddressBtn_Txt;
    private CustomProgressBar progressBar;

    private String mPrimaryAddress;


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

        flatNoET = view.findViewById(R.id.flatNoET);
        addressLine1ET = view.findViewById(R.id.addressLine1ET);
        pincodeET = view.findViewById(R.id.pincodeET);
        cityET = view.findViewById(R.id.cityET);
        stateET = view.findViewById(R.id.stateET);
        landmarkET = view.findViewById(R.id.landmarkET);

        homeBtn_Txt = view.findViewById(R.id.homeBtn_Txt);
        officeBtn_Txt = view.findViewById(R.id.officeBtn_Txt);
        othersBtn_Txt = view.findViewById(R.id.othersBtn_Txt);
        saveAddressBtn_Txt = view.findViewById(R.id.saveAddressBtn_Txt);
        progressBar = view.findViewById(R.id.progressBar);

        stateET.setFocusable(false);
        stateET.setClickable(true);


        // Back button click listener
        view.findViewById(R.id.backBtn).setOnClickListener(v->{
            FragmentUtil.clearSingleBackStack((AppCompatActivity)getActivity());
        });

        homeBtn_Txt.setOnClickListener(v->{
            mPrimaryAddress = "Home";
            homeBtn_Txt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_ok, 0, 0, 0);
            officeBtn_Txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            othersBtn_Txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        });

        officeBtn_Txt.setOnClickListener(v->{
            mPrimaryAddress = "Office";
            homeBtn_Txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            officeBtn_Txt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_ok, 0, 0, 0);
            othersBtn_Txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        });

        othersBtn_Txt.setOnClickListener(v->{
            mPrimaryAddress = "Others";
            homeBtn_Txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            officeBtn_Txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            othersBtn_Txt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_ok, 0, 0, 0);
        });

        saveAddressBtn_Txt.setOnClickListener(v->{
            updateAddress();
        });

        stateET.setOnClickListener(v->{
            loadState("IN");
        });

        loadUserProfileData();


    }

    /**
     * // Loads User Profile Data from local Database
     */
    private void loadUserProfileData() {
        mDisposable.add(ApiManager.getUserProfile(getActivity())
                .observeOn(AndroidSchedulers.mainThread())
                .map(userProfile -> {
                    if (userProfile == null) {
                        return "";
                    }

                    mUserProfile = userProfile;
                    flatNoET.setText(mUserProfile.getAddress_house_no());
                    addressLine1ET.setText(mUserProfile.getAddress_street());
                    pincodeET.setText(mUserProfile.getAddress_pincode());
                    stateET.setText(mUserProfile.getAddress_state());
                    cityET.setText(mUserProfile.getAddress_city());
                    landmarkET.setText(mUserProfile.getAddress_landmark());
                    mPrimaryAddress = mUserProfile.getAddress_default_option();

                    if(mPrimaryAddress != null) {
                        if(mPrimaryAddress.equalsIgnoreCase("Home")) {
                            homeBtn_Txt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_ok, 0, 0, 0);
                        } else if(mPrimaryAddress.equalsIgnoreCase("Office")) {
                            officeBtn_Txt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_ok, 0, 0, 0);
                        } else if(mPrimaryAddress.equalsIgnoreCase("Others")) {
                            othersBtn_Txt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_ok, 0, 0, 0);
                        }
                    }

                    return "";
                })
                .subscribe(v -> {
                        },
                        t ->
                            Log.i("", "" + t)
                        ));
    }

    private void updateAddress() {

        String flatNo = flatNoET.getText().toString();
        String addressLine1 = addressLine1ET.getText().toString();
        String pincode = pincodeET.getText().toString();
        String city = cityET.getText().toString();
        String state = stateET.getText().toString();
        String landmark = landmarkET.getText().toString();

        if(TextUtils.isEmpty(flatNo) || TextUtils.isEmpty(addressLine1) || TextUtils.isEmpty(pincode)
                || TextUtils.isEmpty(city) || TextUtils.isEmpty(state)) {
            Alerts.showAlertDialogOKBtn(getActivity(), getResources().getString(R.string.kindly), "Enter all fields are mandatory.");
            return;
        }
        else if(mPrimaryAddress == null) {
            Alerts.showAlertDialogOKBtn(getActivity(), getResources().getString(R.string.kindly), "Select Primary Address.");
        }

        progressBar.setVisibility(View.VISIBLE);
        saveAddressBtn_Txt.setEnabled(false);

        mDisposable.add(ApiManager.updateAddress(getActivity(), mUserProfile, BuildConfig.SITEID, flatNo, addressLine1,
                landmark, pincode, state, city, mPrimaryAddress, "")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bool->{
                    if(bool) {
                        Alerts.showToast(getActivity(), "Address is updated successfully.");
                        FragmentUtil.clearSingleBackStack((AppCompatActivity) getActivity());
                    }
                    else {
                        Alerts.showAlertDialogOKBtn(getActivity(), "Sorry!", "Address could not updated.\n Kindly try again.");
                    }
                }, throwable -> {
                    if (throwable instanceof HttpException || throwable instanceof ConnectException
                            || throwable instanceof SocketTimeoutException || throwable instanceof TimeoutException) {
                        Alerts.showErrorDailog(getChildFragmentManager(), getResources().getString(R.string.kindly), getResources().getString(R.string.please_check_ur_connectivity));
                    }
                    saveAddressBtn_Txt.setEnabled(true);
                }, () ->{
                    progressBar.setVisibility(View.GONE);
                    saveAddressBtn_Txt.setEnabled(true);
                }));
    }


    private void loadState(String countryCode) {
        progressBar.setVisibility(View.VISIBLE);
        mDisposable.add(ApiManager.getState(countryCode)
                .observeOn(AndroidSchedulers.mainThread())
                .map(states ->
                        states
                )
                .subscribe(value -> {
                    DropDownFragment fragment = DropDownFragment.getInstance("state", value);
                    FragmentUtil.addFragmentAnim((AppCompatActivity) getActivity(), R.id.parentLayout, fragment,
                            FragmentUtil.FRAGMENT_ANIMATION, false);
                    fragment.setOnDropdownitemSelection((from, stateModel) -> {
                        stateET.setText(stateModel.getState());
                        FragmentUtil.clearSingleBackStack((AppCompatActivity) getActivity());
                    });
                    progressBar.setVisibility(View.GONE);

                }, throwable -> {
                    progressBar.setVisibility(View.GONE);
                }));
    }


}
