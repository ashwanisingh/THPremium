package com.ns.userfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;

public class SetPasswordFragment extends BaseFragmentTHP {

    private String mFrom;

    public static SetPasswordFragment getInstance(String from) {
        SetPasswordFragment fragment = new SetPasswordFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    private EditText password_Et;
    private ImageButton passwordVisible_Btn;
    private boolean mIsPasswdVisible;
    private TextView setPasswdTitle_Txt;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_set_password;
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

        password_Et = view.findViewById(R.id.password_Et);
        passwordVisible_Btn = view.findViewById(R.id.passwordVisible_Btn);
        setPasswdTitle_Txt = view.findViewById(R.id.setPasswdTitle_Txt);

        if(mFrom != null && mFrom.equalsIgnoreCase("resetPassword")) {
            setPasswdTitle_Txt.setText("Reset Password");
            password_Et.setText("New Password");
        } else {
            setPasswdTitle_Txt.setText("Set Password");
            password_Et.setText("Password");
        }

        // Cross button click listener
        view.findViewById(R.id.backBtn).setOnClickListener(v->{
            FragmentUtil.clearSingleBackStack((AppCompatActivity) getActivity());
        });

        // Password show / hide button click listener
        passwordVisible_Btn.setOnClickListener(v-> {
            if(mIsPasswdVisible) {
                password_Et.setTransformationMethod(new PasswordTransformationMethod());
                passwordVisible_Btn.setImageResource(R.drawable.ic_show_password);
                mIsPasswdVisible = false;
            } else {
                password_Et.setTransformationMethod(null);
                passwordVisible_Btn.setImageResource(R.drawable.ic_back_copy_42);
                mIsPasswdVisible = true;
            }
        });

        // Submit button click listener
        view.findViewById(R.id.submit_Txt).setOnClickListener(v->{

        });


    }
}
