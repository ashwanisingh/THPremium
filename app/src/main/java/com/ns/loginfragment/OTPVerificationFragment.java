package com.ns.loginfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.netoperation.net.ApiManager;
import com.netoperation.net.RequestCallback;
import com.ns.alerts.Alerts;
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;
import com.ns.utils.THPConstants;

public class OTPVerificationFragment extends BaseFragmentTHP {

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

    private String mFrom;
    private boolean isUserEnteredEmail;
    private String email = "";
    private String contact = "";
    private String emailOrContact;


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

        if(isUserEnteredEmail) {
            emailOrContact = email;
        } else {
            emailOrContact = contact;
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

    }

    private void validateOTP(String otp, String emailOrContact) {
        ApiManager.validateOTP(new RequestCallback<Boolean>() {
            @Override
            public void onNext(Boolean bool) {
                if(getActivity() == null && getView() == null) {
                    return;
                }
                progressBar.setVisibility(View.INVISIBLE);
                if(!bool) {
                    pinEntry.setError(true);
                    Toast.makeText(getActivity(), "FAIL", Toast.LENGTH_SHORT).show();
                    pinEntry.postDelayed(()-> pinEntry.setText(null), 1000);
                }
                else {
                    SetPasswordFragment fragment = SetPasswordFragment.getInstance(mFrom, isUserEnteredEmail, email, contact, otp);
                    FragmentUtil.pushFragmentAnim((AppCompatActivity) getActivity(),
                            R.id.parentLayout, fragment,
                            FragmentUtil.FRAGMENT_NO_ANIMATION, false);
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
        ApiManager.userVerification(new RequestCallback<Boolean>() {
            @Override
            public void onNext(Boolean bool) {
                if(getActivity() == null && getView() == null) {
                    return;
                }
                progressBar.setVisibility(View.INVISIBLE);
                if(!bool) {
                    if(isUserEnteredEmail) {
                        Alerts.showAlertDialogOKBtn(getActivity(), "Sorry!", "Email already exist");
                    }
                    else {
                        Alerts.showAlertDialogOKBtn(getActivity(), "Sorry!", "Mobile already exist");
                    }
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
        }, email, contact, BuildConfig.SITEID);
    }
}
