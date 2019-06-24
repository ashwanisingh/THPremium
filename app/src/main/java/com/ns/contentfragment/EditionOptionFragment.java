package com.ns.contentfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ns.thpremium.R;
import com.ns.loginfragment.BaseFragmentTHP;
import com.ns.utils.FragmentUtil;
import com.ns.utils.IntentUtil;

public class EditionOptionFragment extends BaseFragmentTHP {

    public static EditionOptionFragment getInstance() {
        EditionOptionFragment fragment = new EditionOptionFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_editionoption;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // To block the touch or click
        view.findViewById(R.id.moreTabLayout).setOnTouchListener((v,event)->{
            FragmentUtil.clearSingleBackStack((AppCompatActivity) getActivity());
            return true;
        });

        view.findViewById(R.id.allEditions_Txt).setOnClickListener(v->{
            if(mOnEditionOptionClickListener != null) {
                mOnEditionOptionClickListener.OnEditionOptionClickListener("All Editions");
            }
        });


        view.findViewById(R.id.morningEditions_Txt).setOnClickListener(v->{
            if(mOnEditionOptionClickListener != null) {
                mOnEditionOptionClickListener.OnEditionOptionClickListener("Morning Editions");
            }
        });


        view.findViewById(R.id.noonEditions_Txt).setOnClickListener(v->{
            if(mOnEditionOptionClickListener != null) {
                mOnEditionOptionClickListener.OnEditionOptionClickListener("Noon Editions");
            }
        });


        view.findViewById(R.id.eveningEditions_Txt).setOnClickListener(v->{
            if(mOnEditionOptionClickListener != null) {
                mOnEditionOptionClickListener.OnEditionOptionClickListener("Evening Editions");
            }
        });

    }

    private OnEditionOptionClickListener mOnEditionOptionClickListener;

    public void setOnEditionOptionClickListener(OnEditionOptionClickListener onEditionOptionClickListener) {
        mOnEditionOptionClickListener = onEditionOptionClickListener;
    }


    public interface OnEditionOptionClickListener {
        void OnEditionOptionClickListener(String value);
    }
}
