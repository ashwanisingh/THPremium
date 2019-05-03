package com.ns.loginfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;
import com.ns.utils.THPConstants;

public class OTPVerificationFragment extends BaseFragmentTHP {

    public static OTPVerificationFragment getInstance(String from) {
        OTPVerificationFragment fragment = new OTPVerificationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    private PinEntryEditText pinEntry;
    private TextView resend_Txt;

    private String mFrom;


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
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pinEntry = view.findViewById(R.id.pinEntry_ET);
        resend_Txt = view.findViewById(R.id.resend_Txt);

        view.findViewById(R.id.otpParentLayout).setOnTouchListener((v, e)->{
            return true;
        });

        pinEntry.setOnPinEnteredListener(pinEntryValue->{
            if (pinEntryValue.toString().equals("1234")) {
                Toast.makeText(getActivity(), "SUCCESS", Toast.LENGTH_SHORT).show();
            } else {
                pinEntry.setError(true);
                Toast.makeText(getActivity(), "FAIL", Toast.LENGTH_SHORT).show();
                pinEntry.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pinEntry.setText(null);
                    }
                }, 1000);
            }
        });

        // Cross button click listener
        view.findViewById(R.id.backBtn).setOnClickListener(v->{
            FragmentUtil.clearSingleBackStack((AppCompatActivity) getActivity());
        });

        // Verify button click listener
        view.findViewById(R.id.verify_Txt).setOnClickListener(v->{
            SetPasswordFragment fragment = SetPasswordFragment.getInstance("");
            FragmentUtil.pushFragmentAnim((AppCompatActivity) getActivity(),
                    R.id.parentLayout, fragment,
                    FragmentUtil.FRAGMENT_NO_ANIMATION, false);
        });

        // Resend button click listener
        view.findViewById(R.id.resend_Txt).setOnClickListener(v->{

        });


    }
}
