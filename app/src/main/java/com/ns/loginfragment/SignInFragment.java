package com.ns.loginfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bumptech.glide.load.HttpException;
import com.netoperation.net.ApiManager;
import com.netoperation.net.RequestCallback;
import com.ns.alerts.Alerts;
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.R;
import com.ns.utils.CommonUtil;
import com.ns.utils.FragmentUtil;
import com.ns.utils.IntentUtil;
import com.ns.utils.ResUtil;
import com.ns.utils.THPConstants;
import com.ns.utils.TextSpanCallback;
import com.ns.utils.ValidationUtil;
import com.ns.view.CustomProgressBar;
import com.ns.view.CustomTextView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;

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
    private CustomTextView signIn_Txt;
    private CustomTextView forgotPassword_Txt;

    private ImageButton googleBtn;
    private ImageButton tweeterBtn;
    private ImageButton facebookBtn;

    private boolean isUserEnteredEmail;
    private boolean isUserEnteredMobile;

    private CustomProgressBar progressBar;


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_signin;
    }

    private void enableButton(boolean isEnable) {
        if(isEnable) {
            progressBar.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
        signIn_Txt.setEnabled(isEnable);
        googleBtn.setEnabled(isEnable);
        tweeterBtn.setEnabled(isEnable);
        facebookBtn.setEnabled(isEnable);
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
        signIn_Txt = view.findViewById(R.id.signIn_Txt);
        progressBar = view.findViewById(R.id.progressBar);

        // Terms and Conditions click listener
        ResUtil.doClickSpanForString(getActivity(), "By signing in, you agree to our  ", "Terms and Conditions",
                tc_Txt, R.color.blueColor_1, new TextSpanCallback() {
                    @Override
                    public void onTextSpanClick() {
                        TCFragment fragment = TCFragment.getInstance(THPConstants.TnC_URL);
                        FragmentUtil.pushFragmentAnim((AppCompatActivity) getActivity(), R.id.parentLayout,
                                fragment, FragmentUtil.FRAGMENT_ANIMATION, false);
                    }
                });

        // Password show / hide button click listener
        passwordVisible_Btn.setOnClickListener(v -> {
            if (mIsPasswdVisible) {
                password_Et.setTransformationMethod(new PasswordTransformationMethod());
                passwordVisible_Btn.setImageResource(R.drawable.ic_show_password);
                mIsPasswdVisible = false;
            } else {
                password_Et.setTransformationMethod(null);
                passwordVisible_Btn.setImageResource(R.drawable.ic_hide_password);
                mIsPasswdVisible = true;
            }
        });

        // Sign In button click listener
        signIn_Txt.setOnClickListener(v -> {

            String emailOrMobile = emailOrMobile_Et.getText().toString();
            String mobile = "";
            String email = "";
            String passwd = password_Et.getText().toString();

            if (ValidationUtil.isValidMobile(emailOrMobile)) {
                isUserEnteredMobile = true;
                isUserEnteredEmail = false;
                mobile = emailOrMobile;
            }

            if (ValidationUtil.isValidEmail(emailOrMobile)) {
                isUserEnteredEmail = true;
                isUserEnteredMobile = false;
                email = emailOrMobile;
            }

            if (!isUserEnteredMobile && !isUserEnteredEmail) {
                Alerts.showAlertDialogNoBtnWithCancelable(getActivity(), "", "\nPlease enter valid Email or Mobile \n");
                return;
            }

            if (ValidationUtil.isEmpty(passwd)) {
                Alerts.showAlertDialogNoBtnWithCancelable(getActivity(), "", "\nPlease enter password \n");
                return;
            }

            enableButton(false);

            // Hide SoftKeyboard
            CommonUtil.hideKeyboard(getView());

            String deviceId = ResUtil.getDeviceId(getActivity());

            ApiManager.userLogin(getActivity(), email, mobile, BuildConfig.SITEID, passwd, deviceId, BuildConfig.ORIGIN_URL)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(bool -> {
                                if (getActivity() == null && getView() == null) {
                                    return;
                                }

                                if (!bool) {
                                    if (isUserEnteredMobile) {
                                        Alerts.showAlertDialogOKBtn(getActivity(), "Sorry!", "User Mobile number not found.");
                                    } else {
                                        Alerts.showAlertDialogOKBtn(getActivity(), "Sorry!", "User email not found.");
                                    }
                                } else {
                                    // TODO, process for user sign - In
                                    IntentUtil.openContentListingActivity(getActivity(), "");
                                }
                            }, throwable -> {
                                if (getActivity() != null && getView() != null) {
                                    enableButton(true);
                                    if (throwable instanceof HttpException || throwable instanceof ConnectException
                                            || throwable instanceof SocketTimeoutException || throwable instanceof TimeoutException) {
                                        Alerts.showErrorDailog(getChildFragmentManager(), getResources().getString(R.string.kindly), getResources().getString(R.string.please_check_ur_connectivity));
                                    }
                                    else {
                                        Alerts.showErrorDailog(getChildFragmentManager(), null, throwable.getLocalizedMessage());
                                    }
                                }
                            },
                            () -> {
                                enableButton(true);
                            });


        });


        // Forgot Password button click listener
        view.findViewById(R.id.forgotPassword_Txt).setOnClickListener(v -> {
            ForgotPasswordFragment fragment = ForgotPasswordFragment.getInstance("");
            FragmentUtil.pushFragmentAnim((AppCompatActivity) getActivity(), R.id.parentLayout,
                    fragment, FragmentUtil.FRAGMENT_ANIMATION, false);
        });
    }
}
