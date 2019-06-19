package com.ns.contentfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.ns.thpremium.R;
import com.ns.loginfragment.BaseFragmentTHP;
import com.ns.utils.IntentUtil;

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

        view.findViewById(R.id.suggested_Txt).setOnClickListener(v->{

        });


        view.findViewById(R.id.personalise_Txt).setOnClickListener(v->{
            IntentUtil.openPersonaliseActivity(getActivity(), "MoreOptions");
        });





    }


}
