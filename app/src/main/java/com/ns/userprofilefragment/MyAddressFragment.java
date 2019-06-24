package com.ns.userprofilefragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.netoperation.model.UserProfile;
import com.netoperation.net.ApiManager;
import com.ns.loginfragment.BaseFragmentTHP;
import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;
import com.ns.utils.ResUtil;
import com.ns.view.CustomTextView;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class MyAddressFragment extends BaseFragmentTHP {

    private String mFrom;
    private CustomTextView address_TV;
    private CustomTextView addNewAddress_Txt;

    private UserProfile mUserProfile;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_my_address;
    }

    public static MyAddressFragment getInstance(String from) {
        MyAddressFragment fragment = new MyAddressFragment();
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

        address_TV = view.findViewById(R.id.address_TV);
        addNewAddress_Txt = view.findViewById(R.id.addNewAddress_Txt);

        // Back button click listener
        view.findViewById(R.id.backBtn).setOnClickListener(v->{
            FragmentUtil.clearSingleBackStack((AppCompatActivity)getActivity());
        });

        addNewAddress_Txt.setOnClickListener(v->{
            AddAddressFragment fragment = AddAddressFragment.getInstance("");
            FragmentUtil.pushFragmentAnim((AppCompatActivity)getActivity(), R.id.parentLayout, fragment,
                    FragmentUtil.FRAGMENT_NO_ANIMATION, false);
        });


    }

    @Override
    public void onResume() {
        super.onResume();
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

                    if(mUserProfile.getAddress_pincode() == null || mUserProfile.getAddress_pincode().length()<=5) {
                        addNewAddress_Txt.setText("Add New Address");
                        addNewAddress_Txt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add, 0, 0 , 0);
                    } else {
                        addNewAddress_Txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0 , 0);
                        addNewAddress_Txt.setText("Edit Address");
                        addNewAddress_Txt.setPadding(getResources().getDimensionPixelOffset(R.dimen.margin_26), 0, 0, 0);
                        address_TV.append(mUserProfile.getAddress_house_no() + ", ");
                        address_TV.append(mUserProfile.getAddress_street() + ", ");
                        address_TV.append(mUserProfile.getAddress_landmark() + ", ");
                        address_TV.append(mUserProfile.getAddress_city() + " - ");
                        address_TV.append(mUserProfile.getAddress_pincode() + ", ");
                        address_TV.append(mUserProfile.getAddress_state() + ", ");

                        ResUtil.doStyleSpanForFirstString(mUserProfile.getAddress_default_option() + "\n\n",
                                address_TV.getText().toString(), address_TV);
                    }

                    return "";
                })
                .subscribe(v -> {
                        },
                        t -> {
                            Log.i("", "" + t);
                        }));
    }
}
