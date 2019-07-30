package com.ns.userprofilefragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.netoperation.model.KeyValueModel;
import com.netoperation.model.UserProfile;
import com.netoperation.net.ApiManager;
import com.netoperation.util.NetConstants;
import com.ns.activity.THPUserProfileActivity;
import com.ns.alerts.Alerts;
import com.ns.callbacks.AppLocationListener;
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

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class PersonalInfoFragment extends BaseFragmentTHP {

    private String mFrom;

    private TextInputLayout nameLayout;
    private TextInputEditText nameET;

    private TextInputLayout dobLayout;
    private TextInputEditText dobLayoutET;

    private TextInputLayout genderLayout;
    private TextInputEditText genderLayoutET;

    private TextInputLayout countryLayout;
    private TextInputEditText countryET;

    private TextInputLayout stateLayout;
    private TextInputEditText stateET;

    private CustomProgressBar progressBar;
    private CustomTextView currentLocationBtn_Txt;

    private TextView updateBtn_Txt;

    private ArrayList<KeyValueModel> mCountryModels;
    private KeyValueModel mSelectedCountryModel;

    private UserProfile mUserProfile;

    private THPUserProfileActivity mActivity;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_personal_info;
    }

    public static PersonalInfoFragment getInstance(String from) {
        PersonalInfoFragment fragment = new PersonalInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof THPUserProfileActivity) {
            mActivity = (THPUserProfileActivity) context;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof THPUserProfileActivity) {
            mActivity = (THPUserProfileActivity) activity;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mFrom = getArguments().getString("from");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentLocationBtn_Txt = view.findViewById(R.id.currentLocationBtn_Txt);
        progressBar = view.findViewById(R.id.progressBar);
        updateBtn_Txt = view.findViewById(R.id.updateBtn_Txt);

        nameLayout = view.findViewById(R.id.nameLayout);
        nameET = view.findViewById(R.id.nameET);

        dobLayout = view.findViewById(R.id.dobLayout);
        dobLayoutET = view.findViewById(R.id.dobLayoutET);

        genderLayout = view.findViewById(R.id.genderLayout);
        genderLayoutET = view.findViewById(R.id.genderLayoutET);

        countryLayout = view.findViewById(R.id.countryLayout);
        countryET = view.findViewById(R.id.countryET);

        stateLayout = view.findViewById(R.id.stateLayout);
        stateET = view.findViewById(R.id.stateET);

        dobLayoutET.setFocusable(false);
        dobLayoutET.setClickable(true);

        genderLayoutET.setFocusable(false);
        genderLayoutET.setClickable(true);

        countryET.setFocusable(false);
        countryET.setClickable(true);

        stateET.setFocusable(false);
        stateET.setClickable(true);

        view.findViewById(R.id.info_Txt).setOnClickListener(v->{
            Alerts.showAlertDialogOKBtn(getActivity(), "",
                    "Your profile details will enables us to tailor the stories that we bring to better suit your interst and tastes.");
        });

         // Update Profile Button Click Listener
        updateBtn_Txt.setOnClickListener(v->{
            disableAllView(false);
            progressBar.setVisibility(View.VISIBLE);
            updateProfile();
        });

        // Back button click listener
        view.findViewById(R.id.backBtn).setOnClickListener(v -> {
            CommonUtil.hideKeyboard(v);
            FragmentUtil.clearSingleBackStack((AppCompatActivity) getActivity());
        });

        // Verify OTP button click listener
        view.findViewById(R.id.verifyViaOTPBtn_Txt).setOnClickListener(v -> {
            CommonUtil.hideKeyboard(v);
            OTPVerificationFragment fragment = OTPVerificationFragment.getInstance(THPConstants.FROM_PersonalInfoFragment);
            FragmentUtil.addFragmentAnim((AppCompatActivity) getActivity(), R.id.parentLayout, fragment,
                    FragmentUtil.FRAGMENT_ANIMATION, false);
        });

        // Date of birth Click Listener
        dobLayoutET.setOnClickListener(v -> {
            CommonUtil.hideKeyboard(v);
            /*CalendarFragment fragment = CalendarFragment.getInstance();

            FragmentUtil.addFragmentAnim((AppCompatActivity) getActivity(),
                    R.id.parentLayout, fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, false);

            fragment.setOnCalendarDateClickListener(date -> {
                SimpleDateFormat df = new SimpleDateFormat(THPConstants.date_dd_MM_yyyy);
                dobLayoutET.setText(df.format(date));

                // Clearing Calendar Fragment
                FragmentUtil.clearSingleBackStack((AppCompatActivity) getActivity());

            });*/

            //Date picker dialog
            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            //Date picker dialog
            DatePickerDialog picker = new DatePickerDialog(getActivity(), R.style.DatePickerDialogTheme,
                    (view1, year1, monthOfYear, dayOfMonth) -> {
                        SimpleDateFormat df = new SimpleDateFormat(THPConstants.date_dd_MM_yyyy);
                        Calendar cal = Calendar.getInstance();
                        cal.set(year1, monthOfYear, dayOfMonth);
                        dobLayoutET.setText(df.format(cal.getTime()));
                    }, year, month, day);
            picker.show();
        });

        // Gender Button Click Listener
        genderLayoutET.setOnClickListener(v -> {
            CommonUtil.hideKeyboard(v);
            ArrayList<KeyValueModel> mList = new ArrayList<>();
            KeyValueModel model = new KeyValueModel();
            model.setName("Male");
            mList.add(model);

            model = new KeyValueModel();
            model.setName("Female");
            mList.add(model);

            model = new KeyValueModel();
            model.setName("Trans");
            mList.add(model);

            DropDownFragment fragment = DropDownFragment.getInstance("gender", mList);

            FragmentUtil.addFragmentAnim((AppCompatActivity) getActivity(), R.id.parentLayout, fragment,
                    FragmentUtil.FRAGMENT_ANIMATION, false);
            fragment.setOnDropdownitemSelection((from, genderModel) -> {
                genderLayoutET.setText(genderModel.getName());
                FragmentUtil.clearSingleBackStack((AppCompatActivity) getActivity());
            });
        });

        // Country Click Listener
        countryET.setOnClickListener(v -> {
            CommonUtil.hideKeyboard(v);
            if (mCountryModels == null) {
                loadCountry();
            } else {
                DropDownFragment fragment = DropDownFragment.getInstance("country", mCountryModels);
                FragmentUtil.addFragmentAnim((AppCompatActivity) getActivity(), R.id.parentLayout, fragment,
                        FragmentUtil.FRAGMENT_ANIMATION, false);
                fragment.setOnDropdownitemSelection((from, countryModel) -> {
                    mSelectedCountryModel = countryModel;
                    countryET.setText(countryModel.getName());
                    enableStateField(true);
                    FragmentUtil.clearSingleBackStack((AppCompatActivity) getActivity());

                    loadState(countryModel.getCode());
                });
            }
        });

        // State Click Listener
        stateET.setOnClickListener(v -> {
            CommonUtil.hideKeyboard(v);
            if (mCountryModels == null || mSelectedCountryModel == null) {
                Alerts.showAlertDialogOKBtn(getActivity(), "Kindly!", "First select country.");
                return;
            } else if (!mSelectedCountryModel.getCode().equalsIgnoreCase("IN")) {
                Alerts.showAlertDialogOKBtn(getActivity(), "Warning!", "Currently, We are supporting state list only for India.");
                return;
            }
            loadState(mSelectedCountryModel.getCode());

        });

        // Current Location Click Listener
        currentLocationBtn_Txt.setOnClickListener(v -> {
            CommonUtil.hideKeyboard(v);
            if (!ResUtil.isGooglePlayServicesAvailable(getActivity())) {
                Alerts.showToast(getActivity(), "Re-Install Google Play Services App");
                return;
            }
            else if(mActivity != null) {
                mActivity.getLocation(new AppLocationListener() {
                    @Override
                    public void locationReceived(String maxAddress, String pin, String state,
                                                 String city, String subCity, String countryCode, String countryName) {
                        if(mCountryModels == null) {
                            mCountryModels = new ArrayList<>();
                        }

                        mSelectedCountryModel = new KeyValueModel();
                        mSelectedCountryModel.setCode(countryCode);
                        mSelectedCountryModel.setName(countryName);
                        mCountryModels.add(mSelectedCountryModel);

                        countryET.setText(countryName);
                        stateET.setText(state);


                    }

                    @Override
                    public void locationFailed() {
                        currentLocationBtn_Txt.setText("Failed");

                    }
                });
            }


        });

        // Load User Profile Data from local Database
        loadData();
    }


    /**
     * @param isEnable
     */
    private void enableStateField(boolean isEnable) {
        stateET.setEnabled(isEnable);
    }


    /**
     * // Loads User Profile Data from local Database
     */
    private void loadData() {
        mDisposable.add(ApiManager.getUserProfile(getActivity())
                .observeOn(AndroidSchedulers.mainThread())
                .map(userProfile -> {
                    if (userProfile == null) {
                        return "";
                    }

                    mUserProfile = userProfile;

                    nameET.setText(mUserProfile.getFullName());
                    dobLayoutET.setText(mUserProfile.getDOB());
                    genderLayoutET.setText(mUserProfile.getGender());
                    countryET.setText(mUserProfile.getProfile_Country());
                    stateET.setText(mUserProfile.getProfile_State());
                    return "";
                })
                .subscribe(v -> {
                        },
                        t -> {
                            Log.i("", "" + t);
                        }));
    }

    private void loadCountry() {
        progressBar.setVisibility(View.VISIBLE);
        mDisposable.add(ApiManager.getCountry()
                .observeOn(AndroidSchedulers.mainThread())
                .map(countries -> {
                    return countries;
                })
                .subscribe(value -> {
                            mCountryModels = value;
                            progressBar.setVisibility(View.GONE);
                            DropDownFragment fragment = DropDownFragment.getInstance("country", value);
                            FragmentUtil.addFragmentAnim((AppCompatActivity) getActivity(), R.id.parentLayout, fragment,
                                    FragmentUtil.FRAGMENT_ANIMATION, false);

                            fragment.setOnDropdownitemSelection((from, countryModel) -> {
                                mSelectedCountryModel = countryModel;
                                countryET.setText(countryModel.getName());
                                enableStateField(true);

                                FragmentUtil.clearSingleBackStack((AppCompatActivity) getActivity());
                                loadState(countryModel.getCode());
                            });

                        }, throwable ->
                                progressBar.setVisibility(View.GONE)
                ));
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

    private void updateProfile() {
        String FullName = nameET.getText().toString();
        String DOB = dobLayoutET.getText().toString();
        String Gender = genderLayoutET.getText().toString();
        String Profile_Country = countryET.getText().toString();
        String Profile_State = stateET.getText().toString();
        mDisposable.add(ApiManager.updateProfile(getActivity(), mUserProfile, BuildConfig.SITEID, FullName, DOB, Gender, Profile_Country, Profile_State)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(keyValueModel->{
                    if(keyValueModel.getState().equalsIgnoreCase(NetConstants.SUCCESS)) {
                        Alerts.showToast(getActivity(), "Profile is updated successfully.");
                        FragmentUtil.clearSingleBackStack((AppCompatActivity) getActivity());
                    } else {
                        Alerts.showAlertDialogOKBtn(getActivity(), "Sorry!", keyValueModel.getName());
                    }
                }, throwable -> {
                    disableAllView(true);
                    progressBar.setVisibility(View.GONE);
                    if (throwable instanceof ConnectException
                            || throwable instanceof SocketTimeoutException || throwable instanceof TimeoutException) {
                        Alerts.showErrorDailog(getChildFragmentManager(), getResources().getString(R.string.kindly), getResources().getString(R.string.please_check_ur_connectivity));
                    }

                }, ()->{
                    disableAllView(true);
                    progressBar.setVisibility(View.GONE);
                }));
    }

    private void disableAllView(boolean isEnable) {
        updateBtn_Txt.setEnabled(isEnable);
        nameET.setEnabled(isEnable);
        dobLayoutET.setEnabled(isEnable);
        genderLayoutET.setEnabled(isEnable);
        countryET.setEnabled(isEnable);
        stateET.setEnabled(isEnable);
    }


}