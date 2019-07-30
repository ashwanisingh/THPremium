package com.ns.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ns.contentfragment.BookmarksFragment;
import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;

public class THP_BookmarkActivity extends BaseAcitivityTHP {

    private String mFrom;
    private String mUserId;


    @Override
    public int layoutRes() {
        return R.layout.activity_anonymous_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent().getExtras() != null) {
            mFrom = getIntent().getStringExtra("from");
            mUserId = getIntent().getStringExtra("userId");
        }
        BookmarksFragment fragment = BookmarksFragment.getInstance(mUserId);
        FragmentUtil.pushFragmentAnim(this, R.id.parentLayout, fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, true);
    }
}
