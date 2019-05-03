package com.ns.userprofilefragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import com.ns.alerts.Alerts;
import com.ns.contentfragment.CalendarFragment;
import com.ns.loginfragment.BaseFragmentTHP;
import com.ns.loginfragment.OTPVerificationFragment;
import com.ns.thpremium.R;
import com.ns.utils.CommonUtil;
import com.ns.utils.FragmentUtil;
import com.ns.utils.THPConstants;
import com.ns.view.StandardPopupWindow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PersonalInfoFragment extends BaseFragmentTHP {

    private String mFrom;

    private TextInputLayout dobLayout;
    private TextInputEditText dobLayoutET;

    private TextInputLayout genderLayout;
    private TextInputEditText genderLayoutET;

    private TextInputLayout countryLayout;
    private TextInputEditText countryET;

    private TextInputLayout stateLayout;
    private TextInputEditText stateET;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            mFrom = getArguments().getString("from");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        // Back button click listener
        view.findViewById(R.id.backBtn).setOnClickListener(v->{
            FragmentUtil.clearSingleBackStack((AppCompatActivity)getActivity());
        });

        // Verify OTP button click listener
        view.findViewById(R.id.verifyViaOTPBtn_Txt).setOnClickListener(v->{
            OTPVerificationFragment fragment = OTPVerificationFragment.getInstance(THPConstants.FROM_PersonalInfoFragment);
            FragmentUtil.addFragmentAnim((AppCompatActivity)getActivity(), R.id.parentLayout, fragment,
                    FragmentUtil.FRAGMENT_ANIMATION, false);
        });

        dobLayoutET.setOnClickListener(v->{
            CalendarFragment fragment = CalendarFragment.getInstance();

            FragmentUtil.addFragmentAnim((AppCompatActivity) getActivity(),
                    R.id.parentLayout, fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, false);

            fragment.setOnCalendarDateClickListener(date -> {
                SimpleDateFormat df = new SimpleDateFormat(THPConstants.date_dd_MM_yyyy);
                dobLayoutET.setText(df.format(date));

                // Clearing Calendar Fragment
                FragmentUtil.clearSingleBackStack((AppCompatActivity) getActivity());

            });

        });


        genderLayoutET.setOnClickListener(v->{
            DropDownFragment fragment  = DropDownFragment.getInstance("gender");
            FragmentUtil.addFragmentAnim((AppCompatActivity)getActivity(), R.id.parentLayout, fragment,
                    FragmentUtil.FRAGMENT_ANIMATION, false);
            fragment.setOnDropdownitemSelection((from, gender)->{
                genderLayoutET.setText(gender);
                FragmentUtil.clearSingleBackStack((AppCompatActivity)getActivity());
            });
        });

        countryET.setOnClickListener(v->{
            DropDownFragment fragment  = DropDownFragment.getInstance("country");
            FragmentUtil.addFragmentAnim((AppCompatActivity)getActivity(), R.id.parentLayout, fragment,
                    FragmentUtil.FRAGMENT_ANIMATION, false);
            fragment.setOnDropdownitemSelection((from, country)->{
                countryET.setText(country);
                FragmentUtil.clearSingleBackStack((AppCompatActivity)getActivity());
            });

        });

        stateET.setOnClickListener(v->{
            DropDownFragment fragment  = DropDownFragment.getInstance("state");
            FragmentUtil.addFragmentAnim((AppCompatActivity)getActivity(), R.id.parentLayout, fragment,
                    FragmentUtil.FRAGMENT_ANIMATION, false);
            fragment.setOnDropdownitemSelection((from, state)->{
                stateET.setText(state);
                FragmentUtil.clearSingleBackStack((AppCompatActivity)getActivity());
            });

        });




    }



}
