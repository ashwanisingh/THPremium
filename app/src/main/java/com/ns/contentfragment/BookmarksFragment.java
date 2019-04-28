package com.ns.contentfragment;

import android.os.Bundle;

import com.ns.thpremium.R;
import com.ns.userfragment.BaseFragmentTHP;

public class BookmarksFragment extends BaseFragmentTHP {


    public static BookmarksFragment getInstance() {
        BookmarksFragment fragment = new BookmarksFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_bookmark;
    }
}
