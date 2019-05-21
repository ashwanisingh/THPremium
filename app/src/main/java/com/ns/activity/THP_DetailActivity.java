package com.ns.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ns.callbacks.FragmentTools;
import com.ns.contentfragment.THP_DetailPagerFragment;
import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;

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

        THP_DetailPagerFragment fragment = THP_DetailPagerFragment.getInstance(articleId, clickedPosition, mFrom);

        FragmentUtil.pushFragmentAnim(this, R.id.parentLayout, fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, true);
    }
}
