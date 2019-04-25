package com.ns.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;
import com.ns.utils.ResUtil;
import com.ns.utils.THPConstants;
import com.ns.utils.TextSpanCallback;
import com.ns.view.CustomTextView;

public class SignInFragment extends BaseFragmentTHP {

    public static SignInFragment getInstance() {
        SignInFragment fragment = new SignInFragment();
        return fragment;
    }

    private EditText emailOrMobile_Et;
    private EditText password_Et;
    private ImageButton passwordVisible_Btn;
    private boolean mIsPasswdVisible;

    private CustomTextView tc_Txt;
    private CustomTextView forgotPassword_Txt;

    private ImageButton googleBtn;
    private ImageButton tweeterBtn;
    private ImageButton facebookBtn;


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_signin;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailOrMobile_Et = view.findViewById(R.id.emailOrMobile_Et);
        password_Et = view.findViewById(R.id.password_Et);
        passwordVisible_Btn = view.findViewById(R.id.passwordVisible_Btn);


        tc_Txt = view.findViewById(R.id.tc_Txt);

        googleBtn = view.findViewById(R.id.googleBtn);
        tweeterBtn = view.findViewById(R.id.tweeterBtn);
        facebookBtn = view.findViewById(R.id.facebookBtn);

        // Terms and Conditions click listener
        ResUtil.doClickSpanForString(getActivity(), "By signing in, you agree to our  ", "Terms and Conditions",
                tc_Txt, R.color.blueColor_1, new TextSpanCallback() {
                    @Override
                    public void onTextSpanClick() {
                        TCFragment fragment = TCFragment.getInstance(THPConstants.TnC_URL);
                        FragmentUtil.pushFragAnim((AppCompatActivity)getActivity(), R.id.parentLayout,
                                fragment, FragmentUtil.FRAGMENT_ANIMATION, false);
                    }
                });

        // Password show / hide button click listener
        passwordVisible_Btn.setOnClickListener(v-> {
            if(mIsPasswdVisible) {
                password_Et.setTransformationMethod(new PasswordTransformationMethod());
                 passwordVisible_Btn.setImageResource(R.drawable.ic_close);
                mIsPasswdVisible = false;
            } else {
                password_Et.setTransformationMethod(null);
                passwordVisible_Btn.setImageResource(R.drawable.ic_back_copy_42);
                mIsPasswdVisible = true;
            }
        });

        // Sign In button click listener
        view.findViewById(R.id.signIn_Txt).setOnClickListener(v->{

        });


        // Forgot Password button click listener
        view.findViewById(R.id.forgotPassword_Txt).setOnClickListener(v->{
            ForgotPasswordFragment fragment = ForgotPasswordFragment.getInstance("");
            FragmentUtil.pushFragAnim((AppCompatActivity)getActivity(), R.id.parentLayout,
                    fragment, FragmentUtil.FRAGMENT_ANIMATION, false);
        });
    }
}
