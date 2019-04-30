package com.ns.userprofilefragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.ns.loginfragment.BaseFragmentTHP;
import com.ns.thpremium.R;

public class ChangePasswordFragment extends BaseFragmentTHP {

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_change_passwd;
    }

    public static ChangePasswordFragment getInstance(String from) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
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
