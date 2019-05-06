package com.ns.userprofilefragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ns.loginfragment.BaseFragmentTHP;
import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;

public class MyAddressFragment extends BaseFragmentTHP {

    private String mFrom;

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


        // Back button click listener
        view.findViewById(R.id.backBtn).setOnClickListener(v->{
            FragmentUtil.clearSingleBackStack((AppCompatActivity)getActivity());
        });

        view.findViewById(R.id.addNewAddress_Txt).setOnClickListener(v->{
            AddAddressFragment fragment = AddAddressFragment.getInstance("");
            FragmentUtil.pushFragmentAnim((AppCompatActivity)getActivity(), R.id.parentLayout, fragment,
                    FragmentUtil.FRAGMENT_NO_ANIMATION, false);
        });


    }
}
