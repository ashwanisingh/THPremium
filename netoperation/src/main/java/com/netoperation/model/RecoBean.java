package com.netoperation.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class RecoBean implements Parcelable {
    /**
     * articleId : 27130581
     * articletitle : In 2018, 207 Indians gave up citizenship
     * articleSection : national
     * articleUrl : https://www.thehindu.com/news/national/in-2018-207-indians-gave-up-citizenship/article27130581.ece
     * thumbnailUrl : ["https://www.thehindu.com/news/national/65tzh9/article25705034.ece/BINARY/thumbnail/TH10PASSPORT"]
     * pubDate : 2019-05-14
     * pubDateTime : May 14, 2019 10:45:39 PM
     * author : ["Vijaita Singh"]
     * rank : 1
     * recotype : trending
     * articletype : story
     */

    private String description;
    private String leadText;
    private String commentCount;
    private String AUDIO_URL;
    private String VIDEO_URL;
    private ArrayList<MeBean> IMAGES;

    private String articleId;
    private String articletitle;
    private String articleSection;
    private String articleUrl;
    private String pubDate;
    private String pubDateTime;
    private int rank;
    private String recotype;
    private String articletype;
    private List<String> thumbnailUrl;
    private List<String> author;
    private int like;
    private int bookmark;

    public ArrayList<MeBean> getIMAGES() {
        return IMAGES;
    }

    public void setIMAGES(ArrayList<MeBean> IMAGES) {
        this.IMAGES = IMAGES;
    }

    public String getAUDIO_URL() {
        return AUDIO_URL;
    }

    public void setAUDIO_URL(String AUDIO_URL) {
        this.AUDIO_URL = AUDIO_URL;
    }

    public String getVIDEO_URL() {
        return VIDEO_URL;
    }

    public void setVIDEO_URL(String VIDEO_URL) {
        this.VIDEO_URL = VIDEO_URL;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getLeadText() {
        return leadText;
    }

    public void setLeadText(String leadText) {
        this.leadText = leadText;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getBookmark() {
        return bookmark;
    }

    public void setBookmark(int bookmark) {
        this.bookmark = bookmark;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getArticletitle() {
        return articletitle;
    }

    public void setArticletitle(String articletitle) {
        this.articletitle = articletitle;
    }

    public String getArticleSection() {
        return articleSection;
    }

    public void setArticleSection(String articleSection) {
        this.articleSection = articleSection;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getPubDateTime() {
        return pubDateTime;
    }

    public void setPubDateTime(String pubDateTime) {
        this.pubDateTime = pubDateTime;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getRecotype() {
        return recotype;
    }

    public void setRecotype(String recotype) {
        this.recotype = recotype;
    }

    public String getArticletype() {
        return articletype;
    }

    public void setArticletype(String articletype) {
        this.articletype = articletype;
    }

    public List<String> getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(List<String> thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.description);
        dest.writeString(this.leadText);
        dest.writeString(this.commentCount);
        dest.writeString(this.AUDIO_URL);
        dest.writeString(this.VIDEO_URL);
        dest.writeString(this.articleId);
        dest.writeString(this.articletitle);
        dest.writeString(this.articleSection);
        dest.writeString(this.articleUrl);
        dest.writeString(this.pubDate);
        dest.writeString(this.pubDateTime);
        dest.writeInt(this.rank);
        dest.writeString(this.recotype);
        dest.writeString(this.articletype);
        dest.writeStringList(this.thumbnailUrl);
        dest.writeStringList(this.author);
        dest.writeInt(this.like);
        dest.writeInt(this.bookmark);
    }

    public RecoBean() {
    }

    protected RecoBean(Parcel in) {
        this.description = in.readString();
        this.leadText = in.readString();
        this.commentCount = in.readString();
        this.AUDIO_URL = in.readString();
        this.VIDEO_URL = in.readString();
        this.articleId = in.readString();
        this.articletitle = in.readString();
        this.articleSection = in.readString();
        this.articleUrl = in.readString();
        this.pubDate = in.readString();
        this.pubDateTime = in.readString();
        this.rank = in.readInt();
        this.recotype = in.readString();
        this.articletype = in.readString();
        this.thumbnailUrl = in.createStringArrayList();
        this.author = in.createStringArrayList();
        this.like = in.readInt();
        this.bookmark = in.readInt();
    }

    public static final Parcelable.Creator<RecoBean> CREATOR = new Parcelable.Creator<RecoBean>() {
        @Override
        public RecoBean createFromParcel(Parcel source) {
            return new RecoBean(source);
        }

        @Override
        public RecoBean[] newArray(int size) {
            return new RecoBean[size];
        }
    };


    @Override
    public boolean equals(Object obj) {
        super.equals(obj);

        if(obj instanceof RecoBean) {
            RecoBean bean = (RecoBean) obj;
            return bean.getArticleId().equals(getArticleId());
        }

        return false;
    }

    @Override
    public int hashCode() {
        super.hashCode();
        return getArticleId().hashCode();
    }
}
