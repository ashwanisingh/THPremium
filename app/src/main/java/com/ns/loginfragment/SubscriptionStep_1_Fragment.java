package com.ns.loginfragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;
import com.ns.utils.THPConstants;

import java.util.List;

public class SubscriptionStep_1_Fragment extends BaseFragmentTHP {

    private String mFrom;

    public static SubscriptionStep_1_Fragment getInstance(String from) {
        SubscriptionStep_1_Fragment fragment = new SubscriptionStep_1_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_subscription_step_1;
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

        SubscriptionPackFragment fragment = SubscriptionPackFragment.getInstance(mFrom);
        FragmentUtil.pushFragmentFromFragment(this, R.id.subscriptionPlansLayout, fragment);

       // BecomeMemberIntroFragment benefitsFragment = BecomeMemberIntroFragment.getInstance(THPConstants.FROM_SubscriptionStep_1_Fragment);
       // FragmentUtil.pushFragmentFromFragment(this, R.id.benefitsLayout, benefitsFragment);

        // Back button click listener
        view.findViewById(R.id.backBtn).setOnClickListener(v->{
            getActivity().finish();
        });

        // mail id click listener
        view.findViewById(R.id.tv_mail).setOnClickListener(v->{
            sendEmail();

        });

        // frequently asked questions click listener
        view.findViewById(R.id.tv_frequent_questions).setOnClickListener(v->{
            TCFragment tcFragment = TCFragment.getInstance(THPConstants.FAQ_URL, "arrowBackImg");
            FragmentUtil.pushFragmentAnim((AppCompatActivity) getActivity(), R.id.parentLayout,
                    tcFragment, FragmentUtil.FRAGMENT_NO_ANIMATION, false);
        });

        // terms and conditions click listener
        view.findViewById(R.id.tv_terms).setOnClickListener(v->{
            TCFragment tcFragment = TCFragment.getInstance(THPConstants.TnC_URL, "arrowBackImg");
            FragmentUtil.pushFragmentAnim((AppCompatActivity) getActivity(), R.id.parentLayout,
                    tcFragment, FragmentUtil.FRAGMENT_NO_ANIMATION, false);
        });



    }

    private void sendEmail() {
        try{
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"customersupport@thehindu.co.in"});
            emailIntent.setType("message/rfc822");
            final PackageManager pm = getActivity().getPackageManager();
            final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
            ResolveInfo best = null;
            for(final ResolveInfo info : matches)
                if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
                    best = info;
            if (best != null)
                emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
            getActivity().startActivity(Intent.createChooser(emailIntent, "Send an Email:"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
