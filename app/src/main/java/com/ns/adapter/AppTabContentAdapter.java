package com.ns.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.netoperation.model.RecoBean;
import com.netoperation.net.ApiManager;
import com.netoperation.net.RequestCallback;
import com.netoperation.util.AppDateUtil;
import com.netoperation.util.NetConstants;
import com.netoperation.util.UserPref;
import com.ns.activity.BaseRecyclerViewAdapter;
import com.ns.model.AppTabContentModel;
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.R;
import com.ns.utils.ContentUtil;
import com.ns.utils.GlideUtil;
import com.ns.utils.IntentUtil;
import com.ns.utils.NetUtils;
import com.ns.utils.WebViewLinkClick;
import com.ns.view.AutoResizeWebview;
import com.ns.viewholder.BookmarkViewHolder;
import com.ns.viewholder.BriefcaseViewHolder;
import com.ns.viewholder.DashboardViewHolder;
import com.ns.viewholder.DetailBannerViewHolder;
import com.ns.viewholder.DetailDescriptionWebViewHolder;
import com.ns.viewholder.TrendingViewHolder;

import java.util.List;
import java.util.Locale;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class AppTabContentAdapter extends BaseRecyclerViewAdapter {

    private List<AppTabContentModel> mContent;
    private String mFrom;

    /** This holds description webview position in recyclerview.
     * This is being used to notify recyclerview on TextSize change. */
    private int mDescriptionItemPosition;
    private int mLastDescriptionTextSize;



    public AppTabContentAdapter(List<AppTabContentModel> content, String from) {
        this.mContent = content;
        this.mFrom = from;
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
        else if(viewType == VT_DETAIL_DESCRIPTION_WEBVIEW) {
            return new DetailDescriptionWebViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_detail_description_webview, viewGroup, false));
        }

        else if(viewType == VT_DETAIL_IMAGE_BANNER) {
            return new DetailBannerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_detail_banner_item, viewGroup, false));
        }
        else if(viewType == VT_DETAIL_VIDEO_PLAYER) {

        }
        else if(viewType == VT_DETAIL_AUDIO_PLAYER) {

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        final RecoBean bean = mContent.get(position).getBean();

        if(viewHolder instanceof DashboardViewHolder) {
            DashboardViewHolder holder = (DashboardViewHolder) viewHolder;
            holder.trendingIcon_Img.setVisibility(View.GONE);

            GlideUtil.loadImage(holder.image.getContext(), holder.image, ContentUtil.getThumbUrl(bean.getThumbnailUrl()));
            holder.authorName_Txt.setText(ContentUtil.getAuthor(bean.getAuthor()));
            holder.title.setText(bean.getArticletitle());
            holder.sectionName.setText(bean.getArticleSection());

            isExistInBookmark(holder.bookmark_Img.getContext(), bean.getArticleId(), holder.bookmark_Img);

            holder.bookmark_Img.setOnClickListener(v->
//                bookmarkClick(i, holder.bookmark_Img.getContext(), bean);
                updateBookmarkFavLike(position, bean, "bookmark")
            );

            holder.like_Img.setOnClickListener(v->
                    updateBookmarkFavLike(position, bean, "favourite")
            );

            holder.toggleBtn_Img.setOnClickListener(v->
                updateBookmarkFavLike(position, bean, "dislike")
            );

            holder.itemView.setOnClickListener(v->
                            IntentUtil.openDetailActivity(holder.itemView.getContext(), mFrom,
                                    bean.getArticleUrl(), position, bean.getArticleId())
                    );

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
        else if (viewHolder instanceof DetailBannerViewHolder) {
            DetailBannerViewHolder holder = (DetailBannerViewHolder) viewHolder;
            // Shows Article Type Image
            articleTypeImage(bean.getArticletype(), bean, holder.articleTypeimageView);

            holder.mTitleTV.setText(bean.getArticletitle());
            holder.timeTxt.setText(AppDateUtil.getDetailScreenPublishDate(AppDateUtil.changeStringToMillisGMT(bean.getPubDateTime()), Locale.ENGLISH));

            if(bean.getThumbnailUrl() == null || TextUtils.isEmpty(ContentUtil.getThumbUrl(bean.getThumbnailUrl()))) {
                holder.imageView.setVisibility(View.GONE);
                holder.captionText.setVisibility(View.GONE);
                holder.shadowOverlay.setVisibility(View.GONE);
            }
            else {
                holder.imageView.setVisibility(View.VISIBLE);
                GlideUtil.loadImage(holder.itemView.getContext(), holder.imageView, ContentUtil.getThumbUrl(bean.getThumbnailUrl()));

                String caption = null;
                if(bean.getIMAGES() != null && bean.getIMAGES().size() > 0) {
                    caption = bean.getIMAGES().get(0).getCa();
                }

                if(caption != null && !TextUtils.isEmpty(caption.trim())) {
                    holder.shadowOverlay.setVisibility(View.VISIBLE);
                    holder.captionText.setVisibility(View.VISIBLE);
                    holder.captionText.setText(caption);
                } else {
                    holder.shadowOverlay.setVisibility(View.GONE);
                    holder.captionText.setVisibility(View.GONE);
                }

            }

        }
        else if(viewHolder instanceof DetailDescriptionWebViewHolder) {
            DetailDescriptionWebViewHolder holder = (DetailDescriptionWebViewHolder) viewHolder;
            mLastDescriptionTextSize = UserPref.getInstance(holder.mLeadTxt.getContext()).getDescriptionSize();

            // Enabling Weblink click on Lead Text
            new WebViewLinkClick().linkClick(holder.mLeadTxt, holder.itemView.getContext(), bean.getArticleId());

            holder.mLeadTxt.loadDataWithBaseURL("https:/", AutoResizeWebview.shoWebTextDescription(holder.webview.getContext(), bean.getArticletitle(), true),
                    "text/html", "UTF-8", null);
            holder.mLeadTxt.setSize(mLastDescriptionTextSize);

            holder.webview.setSize(mLastDescriptionTextSize);

            // Enabling Weblink click on Description
            new WebViewLinkClick().linkClick(holder.webview, holder.itemView.getContext(), bean.getArticleId());

            holder.webview.loadDataWithBaseURL("https:/", AutoResizeWebview.shoWebTextDescription(holder.webview.getContext(), bean.getDescription(), false),
                    "text/html", "UTF-8", null);

            mDescriptionItemPosition = position;
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

    public void addData(AppTabContentModel content) {
        mContent.add(content);
        notifyDataSetChanged();
    }

    public void replaceData(RecoBean recoBean, int position) {
        if(position < mContent.size()) {
            mContent.get(position).setBean(recoBean);
        }
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

    private void updateBookmarkFavLike(int position, RecoBean bean, String from) {
        int bookmark = bean.getBookmark();
        int favourite = bean.getLike();
        if(from.equals("bookmark")) {
            if(bean.getBookmark() == NetConstants.BOOKMARK_YES) {
                bookmark = NetConstants.BOOKMARK_NO;
            }
            else {
                bookmark = NetConstants.BOOKMARK_YES;
            }
        }
        else if(from.equals("favorite")) {
            if(bean.getLike() == NetConstants.LIKE_NEUTRAL) {
                favourite = NetConstants.LIKE_YES;
            }
            else {
                favourite = NetConstants.LIKE_NEUTRAL;
            }
        }
        else if(from.equals("dislike")) {
            favourite = NetConstants.LIKE_NO;
        }

        final int book = bookmark;
        final int fav = favourite;

        ApiManager.createBookmarkFavLike(new RequestCallback<Boolean>() {
            @Override
            public void onNext(Boolean o) {
                if(o){
                    bean.setLike(fav);
                    bean.setBookmark(book);
                    notifyItemChanged(position);
                }
            }

            @Override
            public void onError(Throwable t, String str) {

            }

            @Override
            public void onComplete(String str) {

            }
        }, NetConstants.USER_ID, BuildConfig.SITEID, bean.getArticleId(), bookmark, favourite);
    }

}
