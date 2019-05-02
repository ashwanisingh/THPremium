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

        setupOfGenderPopUp();
        setupOfCountryPopUp();





    }

    private void setupOfGenderPopUp() {

        final int genderWidth = getResources().getDimensionPixelSize(R.dimen.country_GenderWidth);
        final int genderHeight = getResources().getDimensionPixelSize(R.dimen.country_GenderHeight);

        genderLayoutET.setOnClickListener(v->{

            int[] locationOnWindow = new int[2];

            genderLayout.getLocationInWindow(locationOnWindow);

            int XPadding = locationOnWindow[0];
            int YPadding = locationOnWindow[1];
            int deviceHeight = CommonUtil.getDeviceHeight(getActivity());
            int deviceWidth = CommonUtil.getDeviceWidth(getActivity());
            int layoutHeight = genderLayout.getHeight();
            int layoutWidth = genderLayout.getWidth();

            int finalBottomPadding = deviceHeight-(YPadding+layoutHeight)-layoutHeight*2;
            int finalXPadding = -((deviceWidth-layoutWidth)/2-XPadding);

            List<String> strList = new ArrayList<>();
            strList.add("Male");
            strList.add("Female");
            strList.add("Tran");

            final StandardPopupWindow window = new StandardPopupWindow(getActivity(), strList, genderWidth, genderHeight);
            window.show(genderLayout, Gravity.BOTTOM, finalXPadding, finalBottomPadding);

            window.setOnStandardPopupItemSelect(gender->{
                genderLayoutET.setText(gender);
                window.dismiss();
            });

        });
    }


    private void setupOfCountryPopUp() {
        final int genderWidth = getResources().getDimensionPixelSize(R.dimen.country_GenderWidth);
        final int genderHeight = getResources().getDimensionPixelSize(R.dimen.country_GenderHeight);

        countryET.setOnClickListener(v->{

            int[] locationOnWindow = new int[2];

            countryLayout.getLocationInWindow(locationOnWindow);

            int XPadding = locationOnWindow[0];
            int YPadding = locationOnWindow[1];
            int deviceHeight = CommonUtil.getDeviceHeight(getActivity());
            int deviceWidth = CommonUtil.getDeviceWidth(getActivity());
            int layoutHeight = countryLayout.getHeight();
            int layoutWidth = countryLayout.getWidth();

            int finalBottomPadding = deviceHeight-(YPadding+layoutHeight)-layoutHeight*2;
            int finalXPadding = (deviceWidth-XPadding)/2;

            List<String> strList = new ArrayList<>();
            strList.add("INDIA");
            strList.add("AUSTRALIA");
            strList.add("NEWZELAND");
            strList.add("SWITZERLAND");

            final StandardPopupWindow window = new StandardPopupWindow(getActivity(), strList, genderWidth, genderHeight);

            window.show(countryLayout, Gravity.BOTTOM, finalXPadding, finalBottomPadding);


            window.setOnStandardPopupItemSelect(country->{
                countryET.setText(country);
                window.dismiss();
            });

        });
    }


}
