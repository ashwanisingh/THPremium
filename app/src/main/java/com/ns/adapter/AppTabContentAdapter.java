package com.ns.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.netoperation.model.RecoBean;
import com.netoperation.net.ApiManager;
import com.netoperation.util.AppDateUtil;
import com.netoperation.util.NetConstants;
import com.netoperation.util.UserPref;
import com.ns.activity.BaseRecyclerViewAdapter;
import com.ns.alerts.Alerts;
import com.ns.model.AppTabContentModel;
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.R;
import com.ns.utils.CommonUtil;
import com.ns.utils.ContentUtil;
import com.ns.utils.GlideUtil;
import com.ns.utils.IntentUtil;
import com.ns.utils.WebViewLinkClick;
import com.ns.view.AutoResizeWebview;
import com.ns.viewholder.BookmarkViewHolder;
import com.ns.viewholder.BriefcaseViewHolder;
import com.ns.viewholder.DashboardViewHolder;
import com.ns.viewholder.DetailBannerViewHolder;
import com.ns.viewholder.DetailDescriptionWebViewHolder;

import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class AppTabContentAdapter extends BaseRecyclerViewAdapter {
    private List<AppTabContentModel> mContent;
    private String mFrom;
    private String mUserId;

    /** This holds description webview position in recyclerview.
     * This is being used to notify recyclerview on TextSize change. */
    private int mDescriptionItemPosition;
    private int mLastDescriptionTextSize;

    public void setFrom(String from) {
        mFrom = from;
    }

    public AppTabContentAdapter(List<AppTabContentModel> content, String from, String userId) {
        this.mContent = content;
        this.mFrom = from;
        this.mUserId = userId;
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
        else if(viewType == VT_TRENDING) {
            return new DashboardViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.apptab_item_dashboard, viewGroup, false));
        }
        else if(viewType == VT_BOOKMARK) {
            return new DashboardViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.apptab_item_dashboard, viewGroup, false));
        }
        else if(viewType == VT_BRIEFCASE) {
            return new BriefcaseViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.apptab_item_briefcase, viewGroup, false));
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
            ui_Dash_Tren_Book_Populate(viewHolder, bean, position);
        }
        else if(viewHolder instanceof BriefcaseViewHolder) {
            ui_Briefing_Populate(viewHolder, bean, position);
        }
        else if (viewHolder instanceof DetailBannerViewHolder) {
            ui_detail_banner(viewHolder, bean);
        }
        else if(viewHolder instanceof DetailDescriptionWebViewHolder) {
            ui_detail_description(viewHolder, bean, position);
        }

    }

    private void ui_Dash_Tren_Book_Populate(RecyclerView.ViewHolder viewHolder, RecoBean bean, int position) {
        DashboardViewHolder holder = (DashboardViewHolder) viewHolder;
        holder.trendingIcon_Img.setVisibility(View.GONE);

        GlideUtil.loadImage(holder.image.getContext(), holder.image, ContentUtil.getThumbUrl(bean.getThumbnailUrl()), R.drawable.th_ph_01);
        holder.authorName_Txt.setText(ContentUtil.getAuthor(bean.getAuthor()));
        holder.title.setText(bean.getArticletitle());
        holder.sectionName.setText(bean.getArticleSection());
        // Publish Date
        String formatedPubDt = CommonUtil.fomatedDate(bean.getPubDateTime(), mFrom);
        holder.time_Txt.setText(formatedPubDt);

        isExistInBookmark(holder.bookmark_Img.getContext(), bean, holder.bookmark_Img);
        isFavOrLike(holder.like_Img.getContext(), bean, holder.like_Img, holder.toggleBtn_Img);

        holder.likeProgressBar.setVisibility(View.GONE);
        holder.bookmarkProgressBar.setVisibility(View.GONE);
        holder.toggleBtnProgressBar.setVisibility(View.GONE);

        holder.bookmark_Img.setOnClickListener(v->
                updateBookmarkFavLike(holder.bookmarkProgressBar, holder.bookmark_Img, holder.bookmark_Img.getContext(), position, bean, "bookmark")
        );

        holder.like_Img.setOnClickListener(v->
                updateBookmarkFavLike(holder.likeProgressBar, holder.like_Img, holder.like_Img.getContext(), position, bean, "favourite")
        );

        holder.toggleBtn_Img.setOnClickListener(v->
                updateBookmarkFavLike(holder.toggleBtnProgressBar, holder.toggleBtn_Img, holder.toggleBtn_Img.getContext(), position, bean, "dislike")
        );

        holder.itemView.setOnClickListener(v->
                IntentUtil.openDetailActivity(holder.itemView.getContext(), mFrom,
                        bean.getArticleUrl(), position, bean.getArticleId())
        );
    }

    /**
     * Briefing Listing Row UI
     * @param viewHolder
     * @param bean
     * @param position
     */
    private void ui_Briefing_Populate(RecyclerView.ViewHolder viewHolder, RecoBean bean, int position) {
        BriefcaseViewHolder holder = (BriefcaseViewHolder) viewHolder;
        GlideUtil.loadImage(holder.image.getContext(), holder.image, ContentUtil.getThumbUrl(bean.getThumbnailUrl()), R.drawable.th_ph_02);
        holder.authorName_Txt.setText(ContentUtil.getAuthor(bean.getAuthor()));
        holder.title.setText(bean.getArticletitle());
        holder.sectionName.setText(bean.getArticleSection());
        // Publish Date
        String formatedPubDt = CommonUtil.fomatedDate(bean.getPubDateTime(), mFrom);
        holder.time_Txt.setText(formatedPubDt);
        holder.description_Txt.setText(CommonUtil.htmlText(bean.getDescription()));

        holder.itemView.setOnClickListener(v->
                IntentUtil.openDetailActivity(holder.itemView.getContext(), mFrom,
                        bean.getArticleUrl(), position, bean.getArticleId())
        );
    }

    /**
     * Detail Page Banner UI
     * @param viewHolder
     * @param bean
     */
    private void ui_detail_banner(RecyclerView.ViewHolder viewHolder, RecoBean bean) {
        DetailBannerViewHolder holder = (DetailBannerViewHolder) viewHolder;
        final String articleType = bean.getArticletype();
        // To shows Article Type Image
        articleTypeImage(articleType, bean, holder.articleTypeimageView);

        String authors = CommonUtil.getAutors(bean.getAuthor());
        if(authors == null) {
            holder.tv_author_name.setVisibility(View.GONE);
        } else {
            holder.tv_author_name.setText(authors);
            holder.tv_author_name.setVisibility(View.VISIBLE);
        }

        String location = bean.getLocation();
        if(location == null || TextUtils.isEmpty(location)) {
            holder.tv_city_name.setVisibility(View.GONE);
        }
        else {
            holder.tv_city_name.setVisibility(View.VISIBLE);
            holder.tv_city_name.setText(location);

        }

        String timeToRead = bean.getTimeToRead();
        if(timeToRead == null || timeToRead.equalsIgnoreCase("0") || TextUtils.isEmpty(timeToRead)) {
            holder.tv_time.setVisibility(View.GONE);
        }else {
            holder.tv_time.setText(timeToRead);
            holder.tv_time.setVisibility(View.VISIBLE);
        }

        // Publish Date
        String formatedPubDt = CommonUtil.fomatedDate(bean.getPubDateTime(), mFrom);
        if(formatedPubDt == null || TextUtils.isEmpty(formatedPubDt)) {
            holder.tv_updated_time.setVisibility(View.GONE);
        }
        else {
            holder.tv_updated_time.setText(formatedPubDt);
            holder.tv_updated_time.setVisibility(View.VISIBLE);
        }

        holder.tv_title.setText(bean.getArticletitle());

        if(ContentUtil.getBannerUrl(bean.getIMAGES(), bean.getMedia(), bean.getThumbnailUrl()).equalsIgnoreCase("http://") ) {
            holder.imageView.setVisibility(View.GONE);
            holder.tv_caption.setVisibility(View.GONE);
            holder.shadowOverlay.setVisibility(View.GONE);
        }
        else {
            holder.imageView.setVisibility(View.VISIBLE);
            GlideUtil.loadImage(holder.itemView.getContext(), holder.imageView, ContentUtil.getBannerUrl(bean.getIMAGES(),
                    bean.getMedia(), bean.getThumbnailUrl()), R.drawable.th_ph_02);

            String caption = null;
            if(bean.getIMAGES() != null && bean.getIMAGES().size() > 0) {
                caption = bean.getIMAGES().get(0).getCa();
            }

            if(caption != null && !TextUtils.isEmpty(caption.trim())) {
                holder.shadowOverlay.setVisibility(View.VISIBLE);
                holder.tv_caption.setVisibility(View.VISIBLE);
                holder.tv_caption.setText(caption);
            } else {
                holder.shadowOverlay.setVisibility(View.GONE);
                holder.tv_caption.setVisibility(View.GONE);
            }


        }

        // Banner Image Click Listener
        holder.imageView.setOnClickListener(v-> {
            if (isVideo(articleType, bean)) {
                if (isYoutubeVideo(articleType)) {
                    IntentUtil.openYoutubeActivity(holder.itemView.getContext(), bean.getYoutubeVideoId());
                    return;
                }
            }

            // Opens Gallery
            //IntentUtils.openVerticleGalleryActivity(holder.itemView.getContext(), articleBean.getIMAGES(), articleBean.getTITLE());
            if (bean.getIMAGES() != null && bean.getIMAGES().size() > 0) {
                IntentUtil.openHorizontalGalleryActivity(holder.itemView.getContext(), null, bean.getIMAGES(), 0);
            } else {
                IntentUtil.openHorizontalGalleryActivity(holder.itemView.getContext(), null, bean.getMedia(), 0);
            }
        });
    }

    private void ui_detail_description(RecyclerView.ViewHolder viewHolder, RecoBean bean, int position) {
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

    public void clearData() {
        mContent.clear();
    }


    /**
     * Checks, Visible Article is bookmarked or not.
     * @param context
     * @param recoBean
     * @param imageView1
     */
    private void isExistInBookmark(Context context, RecoBean recoBean, final ImageView imageView1) {
        ApiManager.isExistInBookmark(context, recoBean.getArticleId())
                .subscribe(bean-> {
                    RecoBean bean1 = (RecoBean) bean;
                    if(recoBean != null) {
                        recoBean.setIsBookmark(bean1.getIsBookmark());
                    }
                    imageView1.setVisibility(View.VISIBLE);
                    imageView1.setEnabled(true);
                    if (bean1.getArticleId() != null && bean1.getArticleId().equals(recoBean.getArticleId())) {
                        imageView1.setImageResource(R.drawable.ic_bookmark_selected);
                    } else {
                        imageView1.setImageResource(R.drawable.ic_bookmark_unselected);

                    }

                }, val->{
                    Log.i("", "");
                });
    }

    private void isFavOrLike(Context context, RecoBean recoBean, final ImageView favStartImg, final ImageView toggleLikeDisLikeImg) {
        ApiManager.isExistFavNdLike(context, recoBean.getArticleId())
                .subscribe(likeVal-> {
                    int like = (int)likeVal;
                    if(recoBean != null) {
                        recoBean.setIsFavourite(like);
                    }
                    favStartImg.setVisibility(View.VISIBLE);
                    toggleLikeDisLikeImg.setVisibility(View.VISIBLE);
                    favStartImg.setEnabled(true);
                    toggleLikeDisLikeImg.setEnabled(true);
                    if(like == NetConstants.LIKE_NEUTRAL) {
                        favStartImg.setImageResource(R.drawable.ic_like_unselected);
                        toggleLikeDisLikeImg.setImageResource(R.drawable.ic_switch_on_copy);
                    }
                    else if(like == NetConstants.LIKE_YES) {
                        favStartImg.setImageResource(R.drawable.ic_like_selected);
                        toggleLikeDisLikeImg.setImageResource(R.drawable.ic_switch_on_copy);
                    }
                    else if(like == NetConstants.LIKE_NO) {
                        favStartImg.setImageResource(R.drawable.ic_like_unselected);
                        toggleLikeDisLikeImg.setImageResource(R.drawable.ic_switch_off_copy);
                    }

                }, val->{
                    Log.i("", "");
                });
    }


    private void updateBookmarkFavLike(ProgressBar bar, ImageView imageView, final Context context, int position, RecoBean bean
            , String from) {
        bar.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.INVISIBLE);
        imageView.setEnabled(false);
        int bookmark = bean.getIsBookmark();
        int favourite = bean.getIsFavourite();
        if(from.equals("bookmark")) {
            if(bean.getIsBookmark() == NetConstants.BOOKMARK_YES) {
                bookmark = NetConstants.BOOKMARK_NO;
            }
            else {
                bookmark = NetConstants.BOOKMARK_YES;
            }
        }
        else if(from.equals("favourite")) {
            if(bean.getIsFavourite() == NetConstants.LIKE_NEUTRAL) {
                favourite = NetConstants.LIKE_YES;
            }
            else if(bean.getIsFavourite() == NetConstants.LIKE_NO) {
                favourite = NetConstants.LIKE_YES;
            }
            else {
                favourite = NetConstants.LIKE_NEUTRAL;
            }
        }
        else if(from.equals("dislike")) {
            if(bean.getIsFavourite() == NetConstants.LIKE_NO) {
                favourite = NetConstants.LIKE_NEUTRAL;
            }
            else if(bean.getIsFavourite() == NetConstants.LIKE_NEUTRAL) {
                favourite = NetConstants.LIKE_NO;
            }

        }

        final int book = bookmark;
        final int fav = favourite;

        // To Create and Remove at server end
        ApiManager.createBookmarkFavLike(mUserId, BuildConfig.SITEID, bean.getArticleId(), bookmark, favourite)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(val-> {
                    if (val) {
                        bean.setIsFavourite(fav);
                        bean.setIsBookmark(book);
                        if(from.equals("bookmark")) {
                            if(book == NetConstants.BOOKMARK_YES) {
                                // To Create at App end
                                ApiManager.createBookmark(context, bean).subscribe(boole -> {
                                    bar.setVisibility(View.GONE);
                                    imageView.setVisibility(View.VISIBLE);
                                    imageView.setEnabled(true);
                                    notifyItemChanged(position);
                                });
                            }
                            else if(book == NetConstants.BOOKMARK_NO) {
                                // To Remove at App end
                                ApiManager.createUnBookmark(context, bean.getArticleId()).subscribe(boole -> {
                                    bar.setVisibility(View.GONE);
                                    imageView.setVisibility(View.VISIBLE);
                                    imageView.setEnabled(true);
                                    notifyItemChanged(position);
                                });
                            }
                        }
                        else if(from.equals("dislike") || from.equals("favourite")) {
                            if(book == NetConstants.BOOKMARK_YES) {
                                // To Update at App end
                                ApiManager.updateBookmark(context, bean.getArticleId(), fav).subscribe(boole ->
                                        Log.i("updateBookmark", "true")
                                );
                            }
                            // To Update at App end
                            ApiManager.updateLike(context, bean.getArticleId(), fav).subscribe(boole -> {
                                notifyItemChanged(position);
                            });
                        }

                    }
                    else {
                        notifyItemChanged(position);
                    }
                },
                val-> {
                    notifyItemChanged(position);
                    Alerts.showAlertDialogOKBtn(imageView.getContext(), imageView.getContext().getResources().getString(R.string.failed_to_connect), imageView.getContext().getResources().getString(R.string.please_check_ur_connectivity));
                }
                );
    }

    public int getDescriptionItemPosition() {
        return mDescriptionItemPosition;
    }

    public int getLastDescriptionTextSize() {
        return mLastDescriptionTextSize;
    }

}
