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
import android.widget.Toast;

import com.netoperation.net.ApiManager;
import com.netoperation.net.RequestCallback;
import com.ns.alerts.Alerts;
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;
import com.ns.utils.ResUtil;
import com.ns.view.CustomProgressBar;

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

        if(mFrom != null && mFrom.equalsIgnoreCase("resetPassword")) {
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

            sigupApiRequest();

        });

    }

    private void sigupApiRequest() {
        String password = password_Et.getText().toString();

        if(TextUtils.isEmpty(password)) {
            Alerts.showErrorDailog(getChildFragmentManager(), "Alert", "Please enter password.");
            return;
        }
        else if(password.length()<=5) {
            Alerts.showErrorDailog(getChildFragmentManager(), "Alert", "Password length must be greater than 5.");
            return;
        }

        submit_Txt.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        ApiManager.userSignUp(new RequestCallback<Boolean>() {
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
                    // TODO, Open new Screen
                }

            }

            @Override
            public void onError(Throwable t, String str) {
                if(getActivity() != null && getView() != null) {
                    progressBar.setVisibility(View.INVISIBLE);
                    submit_Txt.setEnabled(true);
                    Toast.makeText(getActivity(), "FAIL", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onComplete(String str) {
                submit_Txt.setEnabled(true);
            }
        }, otp, "", password, email, contact, ResUtil.getDeviceId(getActivity()), BuildConfig.SITEID, BuildConfig.ORIGIN_URL);
    }
}
