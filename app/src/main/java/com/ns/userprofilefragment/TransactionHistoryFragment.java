package com.ns.userprofilefragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ns.adapter.TransactionHistoryAdapter;
import com.ns.loginfragment.BaseFragmentTHP;
import com.ns.thpremium.R;
import com.ns.utils.FragmentUtil;
import com.ns.view.RecyclerViewPullToRefresh;

import java.util.ArrayList;

public class TransactionHistoryFragment extends BaseFragmentTHP {

    private String mFrom;

    private RecyclerViewPullToRefresh mRecyclerViewPullToRefresh;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_transaction_history;
    }

    public static TransactionHistoryFragment getInstance(String from) {
        TransactionHistoryFragment fragment = new TransactionHistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            mFrom = getArguments().getString("from");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerViewPullToRefresh = view.findViewById(R.id.recyclerViewPullToRefresh);

        // Back button click listener
        view.findViewById(R.id.backBtn).setOnClickListener(v->{
            FragmentUtil.clearSingleBackStack((AppCompatActivity)getActivity());
        });

        TransactionHistoryAdapter adapter = new TransactionHistoryAdapter(new ArrayList<>());
        mRecyclerViewPullToRefresh.setDataAdapter(adapter);


    }


}
