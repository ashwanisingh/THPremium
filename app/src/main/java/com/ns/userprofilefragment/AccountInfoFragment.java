package com.ns.userprofilefragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.ns.alerts.Alerts;
import com.ns.loginfragment.BaseFragmentTHP;
import com.ns.loginfragment.OTPVerificationFragment;
import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;
import com.ns.utils.THPConstants;
import com.ns.utils.ValidationUtil;
import com.ns.view.CustomTextView;

public class AccountInfoFragment extends BaseFragmentTHP {


    TextInputLayout mobileNumberLayout;
    TextInputLayout emailLayout;

    CustomTextView verifyViaOTPBtn_Txt;
    CustomTextView updateBtn_Txt;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_account_info;
    }

    public static AccountInfoFragment getInstance(String from) {
        AccountInfoFragment fragment = new AccountInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mobileNumberLayout = view.findViewById(R.id.mobileNumberLayout);
        emailLayout = view.findViewById(R.id.emailLayout);

//        mobileNumberLayout.setErrorEnabled(true);
//        emailLayout.setErrorEnabled(true);


        // Back button click listener
        view.findViewById(R.id.backBtn).setOnClickListener(v->{
            FragmentUtil.clearSingleBackStack((AppCompatActivity)getActivity());
        });


        // Verify OTP button click listener
        view.findViewById(R.id.verifyViaOTPBtn_Txt).setOnClickListener(v->{
            OTPVerificationFragment fragment = OTPVerificationFragment.getInstance(THPConstants.FROM_AccountInfoFragment);
            FragmentUtil.addFragmentAnim((AppCompatActivity)getActivity(), R.id.parentLayout, fragment,
                    FragmentUtil.FRAGMENT_ANIMATION, false);
        });


        // Back button click listener
        view.findViewById(R.id.updateBtn_Txt).setOnClickListener(v->{
            String email = emailLayout.getEditText().getText().toString();
            String mobile = mobileNumberLayout.getEditText().getText().toString();

            if(!ValidationUtil.isValidMobile(mobile)) {
                Alerts.showToast(getActivity(),"Please enter valid mobile number");
                requestFocus(view.findViewById(R.id.mobileNumberET));
            }
            else if(!ValidationUtil.isValidEmail(email)) {
                Alerts.showToast(getActivity(),"Please enter valid email");
                requestFocus(view.findViewById(R.id.emailET));
            }

            Log.i("", "");
        });


    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    /*private boolean shouldShowError() {
        int textLength = editText.getText().length();
        return textLength > 0 && textLength < MIN_TEXT_LENGTH;
    }

    private void showError() {
        textInputLayout.setError(getString(R.string.error));
    }

    private void hideError() {
        textInputLayout.setError(EMPTY_STRING);
    }

    private static final class ActionListener implements TextView.OnEditorActionListener {
        private final WeakReference<MainActivity> mainActivityWeakReference;

        public static ActionListener newInstance(MainActivity mainActivity) {
            WeakReference<MainActivity> mainActivityWeakReference = new WeakReference<>(mainActivity);
            return new ActionListener(mainActivityWeakReference);
        }

        private ActionListener(WeakReference<MainActivity> mainActivityWeakReference) {
            this.mainActivityWeakReference = mainActivityWeakReference;
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            MainActivity mainActivity = mainActivityWeakReference.get();
            if (mainActivity != null) {
                if (actionId == EditorInfo.IME_ACTION_GO && mainActivity.shouldShowError()) {
                    mainActivity.showError();
                } else {
                    mainActivity.hideError();
                }
            }
            return true;
        }
    }*/

}
