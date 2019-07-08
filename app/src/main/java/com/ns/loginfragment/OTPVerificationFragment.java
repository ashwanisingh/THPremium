package com.ns.loginfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.netoperation.model.KeyValueModel;
import com.netoperation.net.ApiManager;
import com.netoperation.net.RequestCallback;
import com.netoperation.util.NetConstants;
import com.ns.alerts.Alerts;
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;
import com.ns.utils.THPConstants;
import com.ns.view.CustomTextView;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class OTPVerificationFragment extends BaseFragmentTHP {

    public OnOtpVerification mOnOtpVerification;

    public void setOtpVerification(OnOtpVerification onOtpVerification) {
        mOnOtpVerification = onOtpVerification;
    }

    public interface OnOtpVerification {
        void onOtpVerification(boolean isOtpVerified, String otp);
    }

    public static OTPVerificationFragment getInstance(String from, boolean isUserEnteredEmail, String email, String contact) {
        OTPVerificationFragment fragment = new OTPVerificationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        bundle.putBoolean("isUserEnteredEmail", isUserEnteredEmail);
        bundle.putString("email", email);
        bundle.putString("contact", contact);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static OTPVerificationFragment getInstance(String from) {
        OTPVerificationFragment fragment = new OTPVerificationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    private PinEntryEditText pinEntry;
    private TextView resend_Txt;
    private ProgressBar progressBar;
    private CustomTextView otpSendTitle_TV;

    private String mFrom;
    private boolean isUserEnteredEmail;
    private String email = "";
    private String contact = "";
    private String emailOrContact;

    private String mEventType;


    @Override
    public int getLayoutRes() {
        if(mFrom != null && (mFrom.equalsIgnoreCase(THPConstants.FROM_AccountInfoFragment)
        || mFrom.equalsIgnoreCase(THPConstants.FROM_PersonalInfoFragment))) {
            return R.layout.fragment_otp_verification_userprofile;
        } else {
            return R.layout.fragment_otp_verification;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            mFrom = getArguments().getString("from");
            email = getArguments().getString("email");
            contact = getArguments().getString("contact");
            isUserEnteredEmail = getArguments().getBoolean("isUserEnteredEmail");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pinEntry = view.findViewById(R.id.pinEntry_ET);
        resend_Txt = view.findViewById(R.id.resend_Txt);
        progressBar = view.findViewById(R.id.progressBar);
        otpSendTitle_TV = view.findViewById(R.id.otpSendTitle_TV);

        if(isUserEnteredEmail) {
            emailOrContact = email;
            otpSendTitle_TV.setText("Enter OTP sent to your email address");
        } else {
            emailOrContact = contact;
            otpSendTitle_TV.setText("Enter OTP sent to your mobile number");
        }

        view.findViewById(R.id.otpParentLayout).setOnTouchListener((v, e)->{
            return true;
        });

        // OTP Entered Verification Listener
        pinEntry.setOnPinEnteredListener(pinEntryValue->{

        });

        // Cross button click listener
        view.findViewById(R.id.backBtn).setOnClickListener(v->
            FragmentUtil.clearSingleBackStack((AppCompatActivity) getActivity())
        );

        // Verify button click listener
        view.findViewById(R.id.verify_Txt).setOnClickListener(v->
            validateOTP(pinEntry.getText().toString(), emailOrContact)
        );

        // Resend button click listener
        view.findViewById(R.id.resend_Txt).setOnClickListener(v->
            reSendSignupOtpReq()
        );

        if(mFrom != null && (mFrom.equalsIgnoreCase(THPConstants.FROM_DELETE_ACCOUNT)
                || mFrom.equalsIgnoreCase(THPConstants.FROM_SUSPEND_ACCOUNT))) {

            mEventType = NetConstants.EVENT_CHANGE_ACCOUNT_STATUS;

            ApiManager.getUserProfile(getActivity())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(userProfile -> {
                        email = userProfile.getEmailId();

                        if(email != null && !TextUtils.isEmpty(email)) {
                            emailOrContact = email;
                            otpSendTitle_TV.setText("Enter OTP sent to your email address");
                            isUserEnteredEmail = true;
                        }
                        else if(userProfile.getContact() != null && !TextUtils.isEmpty(userProfile.getContact())) {
                            emailOrContact = contact;
                            otpSendTitle_TV.setText("Enter OTP sent to your mobile number");
                            contact = userProfile.getContact();
                        }

                        generateOTP();
                        return userProfile;
                    })
                    .subscribe();
        }
        else if(mFrom != null && (mFrom.equalsIgnoreCase(THPConstants.FROM_FORGOT_PASSWORD))) {
            mEventType = NetConstants.EVENT_FORGOT_PASSWORD;
        }
        else if(mFrom != null && (mFrom.equalsIgnoreCase(THPConstants.FROM_SignUpFragment))) {
            mEventType = NetConstants.EVENT_SIGNUP;
        }

    }

    private void validateOTP(String otp, String emailOrContact) {
        ApiManager.validateOTP(new RequestCallback<Boolean>() {
            @Override
            public void onNext(Boolean bool) {
                if(getActivity() == null && getView() == null) {
                    return;
                }

                if(mOnOtpVerification != null) {
                    mOnOtpVerification.onOtpVerification(bool, otp);
                }

                progressBar.setVisibility(View.INVISIBLE);
                if(!bool) {
                    pinEntry.setError(true);
                    pinEntry.postDelayed(()-> pinEntry.setText(null), 1000);
                    Alerts.showAlertDialogOKBtn(getActivity(), "", "Otp is not verified, please try again.");
                }
                else {
                    if(mFrom != null && mFrom.equalsIgnoreCase(THPConstants.FROM_SignUpFragment)) {
                        SetPasswordFragment fragment = SetPasswordFragment.getInstance(mFrom, isUserEnteredEmail, email, contact, otp);
                        FragmentUtil.pushFragmentAnim((AppCompatActivity) getActivity(),
                                R.id.parentLayout, fragment,
                                FragmentUtil.FRAGMENT_NO_ANIMATION, false);
                    }
                    else if(mFrom != null && (mFrom.equalsIgnoreCase(THPConstants.FROM_DELETE_ACCOUNT)
                            || mFrom.equalsIgnoreCase(THPConstants.FROM_SUSPEND_ACCOUNT))) {
                        FragmentUtil.clearSingleBackStack((AppCompatActivity)getActivity());
                    }
                }
            }

            @Override
            public void onError(Throwable t, String str) {
                if(getActivity() != null && getView() != null) {
                    progressBar.setVisibility(View.INVISIBLE);
                    pinEntry.setError(true);
                    Toast.makeText(getActivity(), "FAIL", Toast.LENGTH_SHORT).show();
                    pinEntry.postDelayed(()-> pinEntry.setText(null), 1000);
                }
            }

            @Override
            public void onComplete(String str) {

            }
        }, otp, emailOrContact);
    }

    /**
     * Resends OTP request to server
     */
    private void reSendSignupOtpReq() {
        progressBar.setVisibility(View.VISIBLE);
        ApiManager.userVerification(new RequestCallback<KeyValueModel>() {
            @Override
            public void onNext(KeyValueModel keyValueModel) {
                if(getActivity() == null && getView() == null) {
                    return;
                }
                progressBar.setVisibility(View.INVISIBLE);
                if(keyValueModel.getState() != null && !keyValueModel.getState().equalsIgnoreCase("success")) {
                    Alerts.showAlertDialogOKBtn(getActivity(), "Sorry!", keyValueModel.getName());
                }
                else {
                    // TODO, Nothing
                }
            }

            @Override
            public void onError(Throwable t, String str) {
                if(getActivity() != null && getView() != null) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Alerts.showErrorDailog(getChildFragmentManager(), null, t.getLocalizedMessage());
                }
            }

            @Override
            public void onComplete(String str) {

            }
        }, email, contact, BuildConfig.SITEID, mEventType);
    }

    private void generateOTP() {
        progressBar.setVisibility(View.VISIBLE);
        ApiManager.generateOtp(email, contact, BuildConfig.SITEID, mEventType)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    if(getActivity() == null && getView() == null) {
                        return;
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    if(!aBoolean) {
                        Alerts.showAlertDialogOKBtn(getActivity(), "Sorry!", "OTP couldn't generated please try again.");
                    }
                }, throwable -> {

                }, () -> {
                    progressBar.setVisibility(View.INVISIBLE);
                });
    }
}
