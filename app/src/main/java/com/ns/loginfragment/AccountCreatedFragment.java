package com.ns.loginfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.netoperation.model.KeyValueModel;
import com.netoperation.net.ApiManager;
import com.netoperation.net.RequestCallback;
import com.netoperation.util.NetConstants;
import com.ns.alerts.Alerts;
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.R;
import com.ns.utils.CommonUtil;
import com.ns.utils.FragmentUtil;
import com.ns.utils.IntentUtil;
import com.ns.utils.THPConstants;
import com.ns.utils.ValidationUtil;
import com.ns.view.CustomTextView;

public class AccountCreatedFragment extends BaseFragmentTHP {

    public static AccountCreatedFragment getInstance(String val) {
        AccountCreatedFragment fragment = new AccountCreatedFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_account_created;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.viewSubscriptionBtn_Txt).setOnClickListener(v->{
            IntentUtil.openSubscriptionActivity(getActivity(), THPConstants.FROM_SUBSCRIPTION_EXPLORE);
        });

        view.findViewById(R.id.setPreferenceBtn_Txt).setOnClickListener(v->{
            IntentUtil.openPersonaliseActivity(getActivity(), "");
        });

        view.findViewById(R.id.accoutCreatedParent).setOnTouchListener((v, e)->{

            return true;
        });


    }



}
