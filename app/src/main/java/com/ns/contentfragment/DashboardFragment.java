package com.ns.contentfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ns.activity.BaseRecyclerViewAdapter;
import com.ns.adapter.AppTabContentAdapter;
import com.ns.model.AppTabContentModel;
import com.ns.thpremium.R;
import com.ns.userfragment.BaseFragmentTHP;
import com.ns.view.CustomTextView;
import com.ns.view.RecyclerViewPullToRefresh;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends BaseFragmentTHP implements RecyclerViewPullToRefresh.TryAgainBtnClickListener {

    private CustomTextView recentStoriesCount_Txt;
    private CustomTextView userName_Txt;
    private CustomTextView recentBtn_Txt;
    private RecyclerViewPullToRefresh recyclerView;
    private AppTabContentAdapter adapter;


    public static DashboardFragment getInstance() {
        DashboardFragment fragment = new DashboardFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_dashboard;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userName_Txt = view.findViewById(R.id.userName_Txt);
        recentStoriesCount_Txt = view.findViewById(R.id.recentStoriesCount_Txt);
        recentBtn_Txt = view.findViewById(R.id.recentBtn_Txt);
        recyclerView = view.findViewById(R.id.recyclerView);


        List<AppTabContentModel> models = new ArrayList<>();

        AppTabContentModel model0 = new AppTabContentModel(BaseRecyclerViewAdapter.VT_DASHBOARD);
        AppTabContentModel model1 = new AppTabContentModel(BaseRecyclerViewAdapter.VT_DASHBOARD);
        AppTabContentModel model2 = new AppTabContentModel(BaseRecyclerViewAdapter.VT_DASHBOARD);
        AppTabContentModel model3 = new AppTabContentModel(BaseRecyclerViewAdapter.VT_DASHBOARD);
        AppTabContentModel model4 = new AppTabContentModel(BaseRecyclerViewAdapter.VT_DASHBOARD);

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
    public void tryAgainBtnClick() {

    }
}
