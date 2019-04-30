package com.ns.contentfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ns.activity.BaseRecyclerViewAdapter;
import com.ns.adapter.AppTabContentAdapter;
import com.ns.model.AppTabContentModel;
import com.ns.thpremium.R;
import com.ns.loginfragment.BaseFragmentTHP;
import com.ns.utils.FragmentUtil;
import com.ns.view.CustomTextView;
import com.ns.view.RecyclerViewPullToRefresh;

import java.util.ArrayList;
import java.util.List;

public class BriefcaseFragment extends BaseFragmentTHP implements RecyclerViewPullToRefresh.TryAgainBtnClickListener {

    private RecyclerViewPullToRefresh recyclerView;
    private AppTabContentAdapter adapter;
    private CustomTextView yourEditionFor_Txt;
    private CustomTextView dateBtn_Txt;
    private CustomTextView userName_Txt;
    private CustomTextView editionBtn_Txt;

    public static BriefcaseFragment getInstance() {
        BriefcaseFragment fragment = new BriefcaseFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_briefcase;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        yourEditionFor_Txt = view.findViewById(R.id.yourEditionFor_Txt);
        dateBtn_Txt = view.findViewById(R.id.dateBtn_Txt);
        userName_Txt = view.findViewById(R.id.userName_Txt);
        editionBtn_Txt = view.findViewById(R.id.editionBtn_Txt);


        List<AppTabContentModel> models = new ArrayList<>();

        AppTabContentModel model0 = new AppTabContentModel(BaseRecyclerViewAdapter.VT_BRIEFCASE);
        AppTabContentModel model1 = new AppTabContentModel(BaseRecyclerViewAdapter.VT_BRIEFCASE);
        AppTabContentModel model2 = new AppTabContentModel(BaseRecyclerViewAdapter.VT_BRIEFCASE);
        AppTabContentModel model3 = new AppTabContentModel(BaseRecyclerViewAdapter.VT_BRIEFCASE);
        AppTabContentModel model4 = new AppTabContentModel(BaseRecyclerViewAdapter.VT_BRIEFCASE);

        models.add(model0);
        models.add(model1);
        models.add(model2);
        models.add(model3);
        models.add(model4);

        adapter = new AppTabContentAdapter(models);

        recyclerView.setDataAdapter(adapter);

        recyclerView.setTryAgainBtnClickListener(this);


        // Edition Btn Click Listener
        editionBtn_Txt.setOnClickListener(v->{
            EditionOptionFragment fragment = EditionOptionFragment.getInstance();
            FragmentUtil.addFragmentAnim((AppCompatActivity) getActivity(),
                    R.id.parentLayout, fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, false);

            fragment.setOnEditionOptionClickListener(value -> {
                editionBtn_Txt.setText(value);

                // Clearing Edition option Fragment
                FragmentUtil.clearSingleBackStack((AppCompatActivity) getActivity());

            });
        });

        dateBtn_Txt.setOnClickListener(v->{
            CalendarFragment fragment = CalendarFragment.getInstance();

            FragmentUtil.addFragmentAnim((AppCompatActivity) getActivity(),
                    R.id.parentLayout, fragment, FragmentUtil.FRAGMENT_NO_ANIMATION, false);

            fragment.setOnCalendarDateClickListener(value -> {
                editionBtn_Txt.setText(value);

                // Clearing Calendar Fragment
                FragmentUtil.clearSingleBackStack((AppCompatActivity) getActivity());

            });
        });

    }

    @Override
    public void tryAgainBtnClick() {

    }
}
