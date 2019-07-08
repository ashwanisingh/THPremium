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
import com.ns.utils.THPConstants;
import com.ns.utils.ValidationUtil;
import com.ns.view.CustomTextView;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class ForgotPasswordFragment extends BaseFragmentTHP {

    public static ForgotPasswordFragment getInstance(String val) {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private EditText emailOrMobile_Et;
    private CustomTextView submit_Txt;

    private String email = "";
    private String contact = "";
    private String emailOrContact;

    private boolean isUserEnteredEmail;
    private boolean isUserEnteredMobile;

    private ProgressBar progressBar;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_forgot_password;
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

        emailOrMobile_Et = view.findViewById(R.id.emailOrMobile_Et);
        progressBar = view.findViewById(R.id.progressBar);
        submit_Txt = view.findViewById(R.id.submit_Txt);

        // Cross button click listener
        view.findViewById(R.id.backBtn).setOnClickListener(v->{
            FragmentUtil.clearSingleBackStack((AppCompatActivity) getActivity());
        });


        // Submit button click listener
        submit_Txt.setOnClickListener(v->{


            String emailOrMobile = emailOrMobile_Et.getText().toString();
            String mobileStr = "";
            String emailStr = "";

            isUserEnteredMobile = false;
            isUserEnteredEmail = false;

            if(ValidationUtil.isValidMobile(emailOrMobile)) {
                isUserEnteredMobile = true;
                isUserEnteredEmail = false;
                mobileStr = emailOrMobile;
            }

            if(ValidationUtil.isValidEmail(emailOrMobile)) {
                isUserEnteredEmail = true;
                isUserEnteredMobile = false;
                emailStr = emailOrMobile;
            }

            if(!isUserEnteredMobile && !isUserEnteredEmail) {
                Alerts.showAlertDialogNoBtnWithCancelable(getActivity(), "", "\nPlease enter valid Email or Mobile \n");
                return;
            }

            submit_Txt.setEnabled(false);

            progressBar.setVisibility(View.VISIBLE);

            progressBar.setVisibility(View.VISIBLE);

            String mobile = mobileStr;
            String email = emailStr;

            // Hide SoftKeyboard
            CommonUtil.hideKeyboard(getView());

            ApiManager.userVerification(new RequestCallback<KeyValueModel>() {
                @Override
                public void onNext(KeyValueModel keyValueModel) {
                    if(getActivity() == null && getView() == null) {
                        return;
                    }

                    if(keyValueModel.getState() != null && !keyValueModel.getState().equalsIgnoreCase("success")) {
                        Alerts.showAlertDialogOKBtn(getActivity(), "Sorry!", keyValueModel.getName());
                    }
                    else {
                        // Opening OTP Verification Screen
                        OTPVerificationFragment fragment = OTPVerificationFragment.getInstance(THPConstants.FROM_FORGOT_PASSWORD,
                                isUserEnteredEmail, email, mobile);
                        FragmentUtil.pushFragmentAnim((AppCompatActivity)getActivity(), R.id.parentLayout, fragment,
                                FragmentUtil.FRAGMENT_ANIMATION, false);

                    }

                }

                @Override
                public void onError(Throwable t, String str) {
                    if(getActivity() != null && getView() != null) {
                        progressBar.setVisibility(View.GONE);
                        submit_Txt.setEnabled(true);
                        Alerts.showErrorDailog(getChildFragmentManager(), null, t.getLocalizedMessage());
                    }
                }

                @Override
                public void onComplete(String str) {
                    progressBar.setVisibility(View.GONE);
                    submit_Txt.setEnabled(true);
                }
            }, emailStr, mobileStr, BuildConfig.SITEID, NetConstants.EVENT_FORGOT_PASSWORD);

        });


    }



}
