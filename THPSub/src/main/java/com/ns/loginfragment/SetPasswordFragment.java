package com.ns.loginfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

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
import com.ns.utils.ResUtil;
import com.ns.utils.THPConstants;
import com.ns.view.CustomProgressBar;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class SetPasswordFragment extends BaseFragmentTHP {

    private String mFrom;

    public static SetPasswordFragment getInstance(String from) {
        SetPasswordFragment fragment = new SetPasswordFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static SetPasswordFragment getInstance(String from, boolean isUserEnteredEmail, String email, String contact, String otp) {
        SetPasswordFragment fragment = new SetPasswordFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        bundle.putBoolean("isUserEnteredEmail", isUserEnteredEmail);
        bundle.putString("email", email);
        bundle.putString("contact", contact);
        bundle.putString("otp", otp);
        fragment.setArguments(bundle);
        return fragment;
    }

    private EditText password_Et;
    private ImageButton passwordVisible_Btn;
    private boolean mIsPasswdVisible;
    private TextView setPasswdTitle_Txt;
    private TextView submit_Txt;

    private CustomProgressBar progressBar;

    private String email;
    private String contact;
    private String otp;
    private boolean isUserEnteredEmail;

    private String mCountryCode = "+91";

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_set_password;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            mFrom = getArguments().getString("from");
            isUserEnteredEmail = getArguments().getBoolean("isUserEnteredEmail");
            email = getArguments().getString("email");
            contact = getArguments().getString("contact");
            otp = getArguments().getString("otp");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);
        password_Et = view.findViewById(R.id.password_Et);
        passwordVisible_Btn = view.findViewById(R.id.passwordVisible_Btn);
        setPasswdTitle_Txt = view.findViewById(R.id.setPasswdTitle_Txt);
        submit_Txt = view.findViewById(R.id.submit_Txt);

        if(mFrom != null && mFrom.equalsIgnoreCase(THPConstants.FROM_FORGOT_PASSWORD)) {
            setPasswdTitle_Txt.setText("Reset Password");
            password_Et.setHint("New Password");
        } else {
            setPasswdTitle_Txt.setText("Set Password");
            password_Et.setHint("Password");
            password_Et.setText("ashwani");
        }

        // Cross button click listener
        view.findViewById(R.id.backBtn).setOnClickListener(v->{
            FragmentUtil.clearSingleBackStack((AppCompatActivity) getActivity());
        });

        // Password show / hide button click listener
        passwordVisible_Btn.setOnClickListener(v-> {
            if(mIsPasswdVisible) {
                password_Et.setTransformationMethod(new PasswordTransformationMethod());
                passwordVisible_Btn.setImageResource(R.drawable.ic_hide_password);
                mIsPasswdVisible = false;
            } else {
                password_Et.setTransformationMethod(null);
                passwordVisible_Btn.setImageResource(R.drawable.ic_show_password);
                mIsPasswdVisible = true;
            }
        });

        // Submit button click listener
        submit_Txt.setOnClickListener(v->{
                if (mFrom != null && mFrom.equalsIgnoreCase(THPConstants.FROM_FORGOT_PASSWORD)) {
                    resetPassword();
                } else {
                    sigupApiRequest();
                }
        });

    }


    private void resetPassword() {
        String password = password_Et.getText().toString();

        if(TextUtils.isEmpty(password)) {
            Alerts.showErrorDailog(getChildFragmentManager(), "Alert", "Please enter password.");
            return;
        }
        else if(password.length()<=5) {
            Alerts.showErrorDailog(getChildFragmentManager(), "Alert", getString(R.string.passwd_length_err_msg));
            return;
        } else if(!ResUtil.isValidPassword(password)) {
            Alerts.showErrorDailog(getChildFragmentManager(), "Alert", getString(R.string.passwd_char_err_msg));
            return;
        }

        submit_Txt.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        CommonUtil.hideKeyboard(getView());

        ApiManager.resetPassword(new RequestCallback<KeyValueModel>() {
            @Override
            public void onNext(KeyValueModel keyValueModel) {
                if(getActivity() == null && getView() == null) {
                    return;
                }

                if(keyValueModel.getState() != null && !keyValueModel.getState().equalsIgnoreCase("success")) {
                    Alerts.showAlertDialogOKBtn(getActivity(), "Sorry!", keyValueModel.getName());
                }
                else {
                    Alerts.showToast(getActivity(), "Password is reset.");
                    getActivity().finish();
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
        }, otp, password, mCountryCode, email, BuildConfig.SITEID, BuildConfig.ORIGIN_URL, contact);
    }

    private void sigupApiRequest() {
        String password = password_Et.getText().toString();

        if(TextUtils.isEmpty(password)) {
            Alerts.showErrorDailog(getChildFragmentManager(), "Alert", "Please enter password.");
            return;
        }
        else if(password.length()<=5) {
            Alerts.showErrorDailog(getChildFragmentManager(), "Alert", getString(R.string.passwd_length_err_msg));
            return;
        } else if(!ResUtil.isValidPassword(password)) {
            Alerts.showErrorDailog(getChildFragmentManager(), "Alert", getString(R.string.passwd_char_err_msg));
            return;
        }

        submit_Txt.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        CommonUtil.hideKeyboard(getView());

        mDisposable.add(ApiManager.userSignUp(getActivity(), otp, mCountryCode, password, email, contact, ResUtil.getDeviceId(getActivity()), BuildConfig.SITEID, BuildConfig.ORIGIN_URL)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(keyValueModel->{
                    if(getActivity() == null && getView() == null) {
                        return;
                    }

                    progressBar.setVisibility(View.INVISIBLE);
                    submit_Txt.setEnabled(true);

                    if(keyValueModel.getState() != null && !keyValueModel.getState().equalsIgnoreCase("success")) {
                        Alerts.showAlertDialogOKBtn(getActivity(), "Sorry!", keyValueModel.getName());
                    }
                    else {
                        // Open new Screen
                        IntentUtil.openContentListingActivity(getActivity(), "SignUp");
                    }
                }, throwable -> {
                    submit_Txt.setEnabled(true);
                }));


    }
}
