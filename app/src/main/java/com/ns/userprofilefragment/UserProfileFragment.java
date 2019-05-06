package com.ns.userprofilefragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.ns.loginfragment.BaseFragmentTHP;
import com.ns.loginfragment.SubscriptionStep_3_Fragment;
import com.ns.loginfragment.TCFragment;
import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;
import com.ns.utils.ResUtil;
import com.ns.utils.THPConstants;
import com.ns.utils.TextSpanCallback;
import com.ns.view.CustomTextView;

public class UserProfileFragment extends BaseFragmentTHP {

    private String mFrom;

    private CustomTextView packName_Txt;
    private CustomTextView planValidity_Txt;
    private CustomTextView userName_Txt;
    private CustomTextView mobileNumber_Txt;

    private CustomTextView versionName_Txt;


    public static UserProfileFragment getInstance(String from) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_userprofile;
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

        packName_Txt = view.findViewById(R.id.packName_Txt);
        planValidity_Txt = view.findViewById(R.id.planValidity_Txt);
        versionName_Txt = view.findViewById(R.id.versionName_Txt);


        // Back button click listener
        view.findViewById(R.id.backBtn).setOnClickListener(v->{
            getActivity().finish();
        });

        // Change Button click listener
        view.findViewById(R.id.viewAllBtn_Txt).setOnClickListener(v->{
            SubscriptionStep_3_Fragment fragment = SubscriptionStep_3_Fragment.getInstance("");
            FragmentUtil.addFragmentAnim((AppCompatActivity)getActivity(), R.id.parentLayout, fragment,
                    FragmentUtil.FRAGMENT_ANIMATION, false);
        });


        // User Next Btn button click listener
        view.findViewById(R.id.userNextBtn_Img).setOnClickListener(v->{
            AccountInfoFragment fragment = AccountInfoFragment.getInstance("");
            FragmentUtil.pushFragmentAnim((AppCompatActivity) getActivity(), R.id.parentLayout,
                    fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, false);
        });

        // Personal Info Row click listener - 1
        view.findViewById(R.id.personalInfo_Row).setOnClickListener(v->{
            PersonalInfoFragment fragment = PersonalInfoFragment.getInstance("");
            FragmentUtil.pushFragmentAnim((AppCompatActivity) getActivity(), R.id.parentLayout,
                    fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, false);
        });

        // My Address Row click listener - 2
        view.findViewById(R.id.myAddress_Row).setOnClickListener(v->{
            MyAddressFragment fragment = MyAddressFragment.getInstance("");
            FragmentUtil.pushFragmentAnim((AppCompatActivity) getActivity(), R.id.parentLayout,
                    fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, false);
        });

        // Notification Row click listener - 3
        view.findViewById(R.id.notification_Row).setOnClickListener(v->{
            NotificationFragment fragment = NotificationFragment.getInstance("");
            FragmentUtil.pushFragmentAnim((AppCompatActivity) getActivity(), R.id.parentLayout,
                    fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, false);
        });

        // Transaction History Row click listener - 4
        view.findViewById(R.id.transactionHistory_Row).setOnClickListener(v->{
            TransactionHistoryFragment fragment = TransactionHistoryFragment.getInstance("");
            FragmentUtil.pushFragmentAnim((AppCompatActivity) getActivity(), R.id.parentLayout,
                    fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, false);
        });


        // Change Password Row click listener - 5
        view.findViewById(R.id.changePassword_Row).setOnClickListener(v->{
            ChangePasswordFragment fragment = ChangePasswordFragment.getInstance("");
            FragmentUtil.pushFragmentAnim((AppCompatActivity) getActivity(), R.id.parentLayout,
                    fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, false);
        });

        // Manage Your Address Row click listener - 6
        view.findViewById(R.id.manageYourAccounts_Row).setOnClickListener(v->{
            ManageAccountsFragment fragment = ManageAccountsFragment.getInstance("");
            FragmentUtil.pushFragmentAnim((AppCompatActivity) getActivity(), R.id.parentLayout,
                    fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, false);
        });

        // Sign Out Row click listener - 7
        view.findViewById(R.id.signOut_Row).setOnClickListener(v->{

        });


        versionName_Txt.setText(ResUtil.getVersionName(getActivity()));




    }
}