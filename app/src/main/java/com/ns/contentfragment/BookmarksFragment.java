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

import java.util.ArrayList;
import java.util.List;

public class BookmarksFragment extends BaseFragmentTHP {

    private RecyclerView recyclerView;
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

        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);

        recyclerView.setAdapter(adapter);

    }
}
