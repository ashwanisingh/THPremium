package com.ns.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ns.thpremium.R;
import com.ns.utils.ResUtil;
import com.ns.utils.THPConstants;

public class BecomeMemberPagerAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private Context context;
    private String mFrom;

    public BecomeMemberPagerAdapter(Context context, String from) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        mFrom = from;
    }

    @Override
    public int getCount() {
        if(mFrom != null && mFrom.equalsIgnoreCase(THPConstants.FROM_SubscriptionOptionFragment)) {
            return 1;
        }
        return 2;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int contentLayoutResId ;

        if(position == 0) {
            contentLayoutResId = R.layout.fragment_introcontent_1;
        } else {
            contentLayoutResId = R.layout.fragment_introcontent_2;
        }

        final View layout = inflater.inflate(contentLayoutResId, container, false);
        container.addView(layout, 0);

        if(mFrom != null && position == 0 && mFrom.equalsIgnoreCase(THPConstants.FROM_SubscriptionOptionFragment)) {
            TextView becomeMember_Txt = layout.findViewById(R.id.becomeMember_Txt);
            TextView benefitsIncludes_Txt = layout.findViewById(R.id.benefitsIncludes_Txt);
            becomeMember_Txt.setText("Subscription Benefits");
            benefitsIncludes_Txt.setText("Subscribe to get these features free for");
            benefitsIncludes_Txt.setTextColor(ResUtil.getColor(context.getResources(), R.color.boldBlackColor));
            benefitsIncludes_Txt.setAlpha(0.6f);
        }

        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
