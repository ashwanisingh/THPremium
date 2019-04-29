package com.ns.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ns.activity.BaseRecyclerViewAdapter;
import com.ns.model.AppTabContentModel;
import com.ns.thpremium.R;
import com.ns.viewholder.BookmarkViewHolder;
import com.ns.viewholder.BriefcaseViewHolder;
import com.ns.viewholder.DashboardViewHolder;
import com.ns.viewholder.TrendingViewHolder;

import java.util.List;

public class AppTabContentAdapter extends BaseRecyclerViewAdapter {

    private List<AppTabContentModel> mContent;

    public AppTabContentAdapter(List<AppTabContentModel> content) {
        this.mContent = content;
    }

    @Override
    public int getItemCount() {
        return mContent.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mContent.get(position).getViewType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        if(viewType == VT_DASHBOARD) {
            return new DashboardViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.apptab_item_dashboard, viewGroup, false));
        }
        else if(viewType == VT_BRIEFCASE) {
            return new BriefcaseViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.apptab_item_briefcase, viewGroup, false));
        }
        else if(viewType == VT_TRENDING) {
            return new TrendingViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.apptab_item_dashboard, viewGroup, false));
        }
        else if(viewType == VT_BOOKMARK) {
            return new BookmarkViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.apptab_item_dashboard, viewGroup, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        final AppTabContentModel model = mContent.get(i);

        if(viewHolder instanceof DashboardViewHolder) {
            DashboardViewHolder holder = (DashboardViewHolder) viewHolder;
            holder.trendingIcon_Img.setVisibility(View.GONE);
        }
        else if(viewHolder instanceof BriefcaseViewHolder) {
            BriefcaseViewHolder holder = (BriefcaseViewHolder) viewHolder;

        }
        else if(viewHolder instanceof TrendingViewHolder) {
            TrendingViewHolder holder = (TrendingViewHolder) viewHolder;
            holder.trendingIcon_Img.setVisibility(View.VISIBLE);
        }
        else if(viewHolder instanceof BookmarkViewHolder) {
            BookmarkViewHolder holder = (BookmarkViewHolder) viewHolder;
            holder.trendingIcon_Img.setVisibility(View.VISIBLE);

        }

    }

}
