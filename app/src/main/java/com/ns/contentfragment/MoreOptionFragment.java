package com.ns.contentfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.netoperation.net.ApiManager;
import com.ns.thpremium.R;
import com.ns.loginfragment.BaseFragmentTHP;
import com.ns.utils.IntentUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class MoreOptionFragment extends BaseFragmentTHP {

    public static MoreOptionFragment getInstance(String userId) {
        MoreOptionFragment fragment = new MoreOptionFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_moreoption;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mUserId = getArguments().getString("userId");
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.suggested_Txt).setOnClickListener(v->{
            IntentUtil.openBookmarkActivity(getActivity(), "BookmarkListing", mUserId);

        });


        view.findViewById(R.id.personalise_Txt).setOnClickListener(v->{
            IntentUtil.openPersonaliseActivity(getActivity(), "MoreOptions");
        });





    }


}
