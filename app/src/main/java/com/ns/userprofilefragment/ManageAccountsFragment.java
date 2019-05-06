package com.ns.userprofilefragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ns.loginfragment.BaseFragmentTHP;
import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;

public class ManageAccountsFragment extends BaseFragmentTHP {

    private String mFrom = "";

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

        // Back button click listener
        view.findViewById(R.id.backBtn).setOnClickListener(v->{
            FragmentUtil.clearSingleBackStack((AppCompatActivity)getActivity());
        });


        // Suspend button click listener
        view.findViewById(R.id.suspendAccountBtn_Txt).setOnClickListener(v->{

        });

        // Delete button click listener
        view.findViewById(R.id.deleteAccountBtn_Txt).setOnClickListener(v->{

        });

    }
}
