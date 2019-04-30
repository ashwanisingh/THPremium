package com.ns.userprofilefragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.ns.loginfragment.BaseFragmentTHP;
import com.ns.thpremium.R;

public class ManageAccountsFragment extends BaseFragmentTHP {

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
