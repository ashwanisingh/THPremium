package com.ns.userfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;
import com.ns.utils.ResUtil;
import com.ns.utils.THPConstants;
import com.ns.utils.TextSpanCallback;
import com.ns.view.CustomTextView;

public class SubscriptionStep_2_Fragment extends BaseFragmentTHP {

    private String mFrom;

    private CustomTextView packName_Txt;
    private CustomTextView planValidity_Txt;

    private EditText name_Et;
    private EditText moNumber_Et;
    private CustomTextView tc_Txt;
    private CustomTextView proceed_Txt;

    public static SubscriptionStep_2_Fragment getInstance(String from) {
        SubscriptionStep_2_Fragment fragment = new SubscriptionStep_2_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_subscription_step_2;
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

        packName_Txt = view.findViewById(R.id.packName_Txt);
        planValidity_Txt = view.findViewById(R.id.planValidity_Txt);

        tc_Txt = view.findViewById(R.id.tc_Txt);
        name_Et = view.findViewById(R.id.name_Et);
        moNumber_Et = view.findViewById(R.id.moNumber_Et);
        proceed_Txt = view.findViewById(R.id.proceed_Txt);


        // Terms and Conditions click listener
        ResUtil.doClickSpanForString(getActivity(), "By signing in, you agree to our  ", "Terms and Conditions",
                tc_Txt, R.color.blueColor_1, new TextSpanCallback() {
                    @Override
                    public void onTextSpanClick() {
                        TCFragment fragment = TCFragment.getInstance(THPConstants.TnC_URL);
                        FragmentUtil.pushFragmentAnim((AppCompatActivity)getActivity(), R.id.parentLayout,
                                fragment, FragmentUtil.FRAGMENT_ANIMATION, false);
                    }
                });

        // Change Button click listener
        view.findViewById(R.id.changeBtn_Txt).setOnClickListener(v->{
            SubscriptionStep_3_Fragment fragment = SubscriptionStep_3_Fragment.getInstance("");
            FragmentUtil.addFragmentAnim((AppCompatActivity)getActivity(), R.id.parentLayout, fragment,
                    FragmentUtil.FRAGMENT_ANIMATION, false);
        });


        // Back button click listener
        view.findViewById(R.id.backBtn).setOnClickListener(v->{
            FragmentUtil.clearSingleBackStack((AppCompatActivity)getActivity());
        });

    }
}