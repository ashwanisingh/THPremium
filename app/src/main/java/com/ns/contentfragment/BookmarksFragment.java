package com.ns.contentfragment;

import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.ns.activity.BaseRecyclerViewAdapter;
import com.ns.adapter.AppTabContentAdapter;
import com.ns.model.AppTabContentModel;
import com.ns.thpremium.R;
import com.ns.userfragment.BaseFragmentTHP;
import com.ns.view.RecyclerViewPullToRefresh;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BookmarksFragment extends BaseFragmentTHP implements RecyclerViewPullToRefresh.TryAgainBtnClickListener {

    private RecyclerViewPullToRefresh recyclerView;
    private AppTabContentAdapter adapter;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);

        List<AppTabContentModel> models = new ArrayList<>();

        AppTabContentModel model0 = new AppTabContentModel(BaseRecyclerViewAdapter.VT_BOOKMARK);
        AppTabContentModel model1 = new AppTabContentModel(BaseRecyclerViewAdapter.VT_BOOKMARK);
        AppTabContentModel model2 = new AppTabContentModel(BaseRecyclerViewAdapter.VT_BOOKMARK);
        AppTabContentModel model3 = new AppTabContentModel(BaseRecyclerViewAdapter.VT_BOOKMARK);
        AppTabContentModel model4 = new AppTabContentModel(BaseRecyclerViewAdapter.VT_BOOKMARK);

        models.add(model0);
        models.add(model1);
        models.add(model2);
        models.add(model3);
        models.add(model4);

        adapter = new AppTabContentAdapter(models);

        recyclerView.setDataAdapter(adapter);

        recyclerView.setTryAgainBtnClickListener(this);


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser && getView() != null) {
            netCheck();
        }
    }

    private void netCheck() {

        ReactiveNetwork
                .observeNetworkConnectivity(getActivity())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(connectivity -> {
                    if(connectivity.state() == NetworkInfo.State.CONNECTED) {

                    }
                    else {

                    }

                    Log.i("", "");
                });
    }

    @Override
    public void tryAgainBtnClick() {

    }
}
