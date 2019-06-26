package com.ns.userprofilefragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.netoperation.net.ApiManager;
import com.ns.adapter.TransactionHistoryAdapter;
import com.ns.alerts.Alerts;
import com.ns.loginfragment.BaseFragmentTHP;
import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;
import com.ns.utils.ResUtil;
import com.ns.view.CustomProgressBar;
import com.ns.view.CustomTextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class ChangePasswordFragment extends BaseFragmentTHP {

    private String mFrom = "";

    private CustomProgressBar progressBar;
    private CustomTextView updatePasswordBtn_Txt;

    private TextInputEditText currentPasswordET;
    private TextInputEditText newPasswordET;
    private TextInputEditText confirmPasswordET;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_change_passwd;
    }

    public static ChangePasswordFragment getInstance(String from) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
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

        progressBar = view.findViewById(R.id.progressBar);
        currentPasswordET = view.findViewById(R.id.currentPasswordET);
        newPasswordET = view.findViewById(R.id.newPasswordET);
        confirmPasswordET = view.findViewById(R.id.confirmPasswordET);
        updatePasswordBtn_Txt = view.findViewById(R.id.updatePasswordBtn_Txt);

        // Back button click listener
        view.findViewById(R.id.backBtn).setOnClickListener(v->{
            FragmentUtil.clearSingleBackStack((AppCompatActivity)getActivity());
        });

        updatePasswordBtn_Txt.setOnClickListener(v->{
            updatePassword();
        });

    }

    private void updatePassword() {
        String currentPassword = currentPasswordET.getText().toString();
        String newPassword = newPasswordET.getText().toString();
        String confirmPassword = confirmPasswordET.getText().toString();

        if(TextUtils.isEmpty(currentPassword) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
            Alerts.showAlertDialogOKBtn(getActivity(), getString(R.string.alert), getString(R.string.all_field_mandatory));
        }
        else if(!newPassword.equals(confirmPassword)) {
            Alerts.showAlertDialogOKBtn(getActivity(), getString(R.string.alert), "Password are not same.");
            return;
        }
        else if(confirmPassword.length()<=5) {
            Alerts.showAlertDialogOKBtn(getActivity(), getString(R.string.alert), getString(R.string.passwd_length_err_msg));
            return;
        } else if(!ResUtil.isValidPassword(confirmPassword)) {
            Alerts.showAlertDialogOKBtn(getActivity(), getString(R.string.alert), getString(R.string.passwd_char_err_msg));
            return;
        } else {
            progressBar.setVisibility(View.VISIBLE);
            updatePasswordBtn_Txt.setEnabled(false);
            mDisposable.add(ApiManager.getUserProfile(getActivity())
                    .subscribe(userProfile ->
                                    ApiManager.updatePassword(userProfile.getUserId(), currentPassword, confirmPassword)
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(keyValueModel -> {
                                                if(keyValueModel.getState() != null && keyValueModel.getState().equalsIgnoreCase("failed")) {
                                                    Alerts.showAlertDialogOKBtn(getActivity(), "Failed", keyValueModel.getName());
                                                }
                                                else {
                                                    Alerts.showToast(getActivity(), "Password is updated successfully.");
                                                    FragmentUtil.clearSingleBackStack((AppCompatActivity) getActivity());
                                                }
                                            }, throwable -> {
                                                progressBar.setVisibility(View.GONE);
                                                updatePasswordBtn_Txt.setEnabled(true);
                                                Alerts.showErrorDailog(getChildFragmentManager(), getResources().getString(R.string.kindly), getResources().getString(R.string.please_check_ur_connectivity));
                                            }, () -> {
                                                progressBar.setVisibility(View.GONE);
                                                updatePasswordBtn_Txt.setEnabled(true);
                                            })
                            , throwable -> {

                            }));

        }







    }

}
