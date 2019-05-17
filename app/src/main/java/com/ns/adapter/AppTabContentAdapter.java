package com.ns.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.netoperation.model.RecoBean;
import com.netoperation.net.ApiManager;
import com.ns.activity.BaseRecyclerViewAdapter;
import com.ns.model.AppTabContentModel;
import com.ns.thpremium.R;
import com.ns.utils.ContentUtil;
import com.ns.utils.GlideUtil;
import com.ns.viewholder.BookmarkViewHolder;
import com.ns.viewholder.BriefcaseViewHolder;
import com.ns.viewholder.DashboardViewHolder;
import com.ns.viewholder.TrendingViewHolder;

import java.util.List;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

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
        else if(viewType == VT_LOADMORE) {
            return new BookmarkViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_loadmore, viewGroup, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        final RecoBean bean = mContent.get(i).getBean();

        if(viewHolder instanceof DashboardViewHolder) {
            DashboardViewHolder holder = (DashboardViewHolder) viewHolder;
            holder.trendingIcon_Img.setVisibility(View.GONE);

            GlideUtil.loadImage(holder.image.getContext(), holder.image, ContentUtil.getThumbUrl(bean.getThumbnailUrl()));
            holder.authorName_Txt.setText(ContentUtil.getAuthor(bean.getAuthor()));
            holder.title.setText(bean.getArticletitle());
            holder.sectionName.setText(bean.getArticleSection());

            isExistInBookmark(holder.bookmark_Img.getContext(), bean.getArticleId(), holder.bookmark_Img);

            holder.bookmark_Img.setOnClickListener(v->{
                bookmarkClick(i, holder.bookmark_Img.getContext(), bean);
            });

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

    public void addData(List<AppTabContentModel> content) {
        mContent.addAll(content);
        notifyDataSetChanged();
    }

    public void setData(List<AppTabContentModel> content) {
        mContent = content;
        notifyDataSetChanged();
    }


    /**
     * Checks, Visible Article is bookmarked or not.
     *
     * @param aid
     */
    private void isExistInBookmark(Context context, String aid, final ImageView imageView) {
        ApiManager.isExistInBookmark(context, aid)
                .subscribe(bool->{
                    if ((Boolean) bool) {
                        imageView.setImageResource(R.drawable.ic_bookmark_selected);
                    } else {
                        imageView.setImageResource(R.drawable.ic_bookmark_unselected);
                    }
                });

    }

    private void bookmarkClick(int position, Context context, RecoBean bean) {
        ApiManager.isExistInBookmark(context, bean.getArticleId())
                .subscribe(bool->{
                    if ((Boolean) bool) {
                        ApiManager.createUnBookmark(context, bean.getArticleId()).subscribe(boole->{
                            notifyItemChanged(position);
                        });
                    } else {
                        ApiManager.createBookmark(context, bean).subscribe(boole->{
                            notifyItemChanged(position);
                        });
                    }
                });
    }

}
