package com.ns.userfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;
import com.ns.utils.ResUtil;
import com.ns.utils.THPConstants;
import com.ns.utils.TextSpanCallback;
import com.ns.view.CustomTextView;

public class SignUpFragment extends BaseFragmentTHP {

    public static SignUpFragment getInstance() {
        SignUpFragment fragment = new SignUpFragment();
        return fragment;
    }

    private EditText emailOrMobile_Et;

    private CustomTextView tc_Txt;
    private CustomTextView faq_Txt;
    private CustomTextView signUp_Txt;

    private ImageButton googleBtn;
    private ImageButton tweeterBtn;
    private ImageButton facebookBtn;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_signup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailOrMobile_Et = view.findViewById(R.id.emailOrMobile_Et);
        tc_Txt = view.findViewById(R.id.tc_Txt);
        faq_Txt = view.findViewById(R.id.faq_Txt);
        signUp_Txt = view.findViewById(R.id.signUp_Txt);

        googleBtn = view.findViewById(R.id.googleBtn);
        tweeterBtn = view.findViewById(R.id.tweeterBtn);
        facebookBtn = view.findViewById(R.id.facebookBtn);

        // Terms and Conditions Click Listener
        ResUtil.doClickSpanForString(getActivity(), "By signing up, you agree to our  ",
                "Terms and Conditions",
                tc_Txt, R.color.blueColor_1, new TextSpanCallback() {
                    @Override
                    public void onTextSpanClick() {
                        TCFragment fragment = TCFragment.getInstance(THPConstants.TnC_URL);
                        FragmentUtil.pushFragmentAnim((AppCompatActivity)getActivity(), R.id.parentLayout,
                                fragment, FragmentUtil.FRAGMENT_ANIMATION, false);
                    }
                });

        signUp_Txt.setOnClickListener(v->{
            OTPVerificationFragment fragment = OTPVerificationFragment.getInstance("");
            FragmentUtil.pushFragmentAnim((AppCompatActivity)getActivity(), R.id.parentLayout, fragment,
                    FragmentUtil.FRAGMENT_ANIMATION, false);
        });


    }
}
