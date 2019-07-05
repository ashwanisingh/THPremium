package com.ns.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.netoperation.net.ApiManager;
import com.netoperation.util.NetConstants;
import com.ns.callbacks.FragmentTools;
import com.ns.contentfragment.AppTabFragment;
import com.ns.contentfragment.THP_DetailPagerFragment;
import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;
import com.ns.utils.THPConstants;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class THP_DetailActivity extends BaseAcitivityTHP {

    String mFrom;
    private int clickedPosition;
    private String articleId;
    private String url;


    @Override
    public int layoutRes() {
        return R.layout.activity_detail_thp;
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent().getExtras() != null) {
            mFrom = getIntent().getStringExtra("from");
            url = getIntent().getStringExtra("url");
            clickedPosition = getIntent().getIntExtra("clickedPosition", 0);
            articleId = getIntent().getStringExtra("articleId");
        }

        if(mFrom != null && ((NetConstants.BREIFING_ALL.equalsIgnoreCase(mFrom))
                || (NetConstants.BREIFING_EVENING.equalsIgnoreCase(mFrom))
                || (NetConstants.BREIFING_NOON.equalsIgnoreCase(mFrom))
                || (NetConstants.BREIFING_MORNING.equalsIgnoreCase(mFrom)))) {
            getToolbar().hideBookmark_Fav_Like();
        }

        ApiManager.getUserProfile(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userProfile -> {
                    THP_DetailPagerFragment fragment = THP_DetailPagerFragment.getInstance(articleId, clickedPosition, mFrom, userProfile.getUserId());

                    FragmentUtil.pushFragmentAnim(this, R.id.parentLayout, fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, true);
                });


    }
}
