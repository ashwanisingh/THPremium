package com.ns.loginfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.netoperation.net.ApiManager;
import com.netoperation.net.RequestCallback;
import com.ns.alerts.Alerts;
import com.ns.sharedpreference.THPPreferences;
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.R;
import com.ns.utils.CommonUtil;
import com.ns.utils.FragmentUtil;
import com.ns.utils.ResUtil;
import com.ns.utils.THPConstants;
import com.ns.utils.TextSpanCallback;
import com.ns.utils.ValidationUtil;
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

    private boolean isUserEnteredEmail;
    private boolean isUserEnteredMobile;

    private THPPreferences preferences;

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
            OTPVerificationFragment fragment = OTPVerificationFragment.getInstance(THPConstants.FROM_SignUpFragment);
            FragmentUtil.pushFragmentAnim((AppCompatActivity)getActivity(), R.id.parentLayout, fragment,
                    FragmentUtil.FRAGMENT_ANIMATION, false);


            String emailOrMobile = emailOrMobile_Et.getText().toString();
            String mobile = "";
            String email = "";

            if(ValidationUtil.isValidMobile(emailOrMobile)) {
                isUserEnteredMobile = true;
                isUserEnteredEmail = false;
                mobile = emailOrMobile;
            }

            if(ValidationUtil.isValidEmail(emailOrMobile)) {
                isUserEnteredEmail = true;
                isUserEnteredMobile = false;
                email = emailOrMobile;
            }

            if(!isUserEnteredMobile && !isUserEnteredEmail) {
                Alerts.showAlertDialogNoBtnWithCancelable(getActivity(), "", "\nPlease enter valid Email or Mobile \n");
                return;
            }

            // Hide SoftKeyboard
            CommonUtil.hideKeyboard(getView());

            ApiManager.userVerification(new RequestCallback<Boolean>() {
                @Override
                public void onNext(Boolean bool) {
                    if(getActivity() == null && getView() == null) {
                        return;
                    }

                    if(!bool) {
                        if(isUserEnteredMobile) {
                            Alerts.showAlertDialogOKBtn(getActivity(), "Sorry!", "Mobile already exist");
                        }
                        else {
                            Alerts.showAlertDialogOKBtn(getActivity(), "Sorry!", "Email already exist");
                        }
                    }
                    else {

                        // Todo,


                        preferences=THPPreferences.getInstance(getActivity());
                        String shEmailOrMobile=emailOrMobile_Et.getText().toString().trim();
                        preferences.saveSignUpDetails(shEmailOrMobile);
                    }

                }

                @Override
                public void onError(Throwable t, String str) {
                    if(getActivity() != null && getView() != null) {
                        Alerts.showErrorDailog(getChildFragmentManager(), null, t.getLocalizedMessage());
                    }
                }

                @Override
                public void onComplete(String str) {

                }
            }, email, mobile, BuildConfig.SITEID);



        });


    }


}
