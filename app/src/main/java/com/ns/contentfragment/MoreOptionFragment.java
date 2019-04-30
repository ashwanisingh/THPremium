package com.ns.contentfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.ns.thpremium.R;
import com.ns.loginfragment.BaseFragmentTHP;

public class MoreOptionFragment extends BaseFragmentTHP {

    public static MoreOptionFragment getInstance() {
        MoreOptionFragment fragment = new MoreOptionFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_moreoption;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // To block the touch or click
        view.findViewById(R.id.moreTabLayout).setOnTouchListener((v,event)->{
            return true;
        });

        view.findViewById(R.id.suggested_Txt).setOnClickListener(v->{
            if(mMoreTabOptionClickListener != null) {
                mMoreTabOptionClickListener.OnEditionOptionClickListener("suggested");
            }
        });


        view.findViewById(R.id.personalise_Txt).setOnClickListener(v->{
            if(mMoreTabOptionClickListener != null) {
                mMoreTabOptionClickListener.OnEditionOptionClickListener("personalise");
            }
        });


        view.findViewById(R.id.cancel_Txt).setOnClickListener(v->{
            if(mMoreTabOptionClickListener != null) {
                mMoreTabOptionClickListener.OnEditionOptionClickListener("cancel");
            }
        });


    }

    private MoreTabOptionClickListener mMoreTabOptionClickListener;

    public void setOnMoreOptionClickListener(MoreTabOptionClickListener moreTabOptionClickListener) {
        mMoreTabOptionClickListener = moreTabOptionClickListener;
    }


    public interface MoreTabOptionClickListener {
        void OnEditionOptionClickListener(String value);
    }
}
