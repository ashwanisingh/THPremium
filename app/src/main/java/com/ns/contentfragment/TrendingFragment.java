package com.ns.contentfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.netoperation.util.NetConstants;
import com.ns.activity.BaseRecyclerViewAdapter;
import com.ns.adapter.AppTabContentAdapter;
import com.ns.model.AppTabContentModel;
import com.ns.thpremium.R;
import com.ns.loginfragment.BaseFragmentTHP;
import com.ns.view.RecyclerViewPullToRefresh;

import java.util.ArrayList;
import java.util.List;

public class TrendingFragment extends BaseFragmentTHP implements RecyclerViewPullToRefresh.TryAgainBtnClickListener {

    private RecyclerViewPullToRefresh recyclerView;
    private AppTabContentAdapter adapter;

    public static TrendingFragment getInstance() {
        TrendingFragment fragment = new TrendingFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_trending;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);

        List<AppTabContentModel> models = new ArrayList<>();

        AppTabContentModel model0 = new AppTabContentModel(BaseRecyclerViewAdapter.VT_TRENDING);
        AppTabContentModel model1 = new AppTabContentModel(BaseRecyclerViewAdapter.VT_TRENDING);
        AppTabContentModel model2 = new AppTabContentModel(BaseRecyclerViewAdapter.VT_TRENDING);
        AppTabContentModel model3 = new AppTabContentModel(BaseRecyclerViewAdapter.VT_TRENDING);
        AppTabContentModel model4 = new AppTabContentModel(BaseRecyclerViewAdapter.VT_TRENDING);

        models.add(model0);
        models.add(model1);
        models.add(model2);
        models.add(model3);
        models.add(model4);

        adapter = new AppTabContentAdapter(models, NetConstants.RECO_trending);

        recyclerView.setDataAdapter(adapter);

        recyclerView.setTryAgainBtnClickListener(this);

    }

    @Override
    public void tryAgainBtnClick() {

    }
}
