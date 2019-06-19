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
    private String articleId;
    private String leadText;

    private String commentCount;
    private String AUDIO_URL;
    private String VIDEO_URL;
    private ArrayList<MeBean> IMAGES;

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




    private String sectionName;
    private String publishedDate;
    private String originalDate;
    private String location;
    private String title;
    private String articleLink;
    private String gmt;
    private String youtubeVideoId;
    private String shortDescription;
    private String videoId;
    private String articleType;
    private String timeToRead;
    private List<BriefingMediaBean> media;




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


    public RecoBean() {
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getOriginalDate() {
        return originalDate;
    }

    public void setOriginalDate(String originalDate) {
        this.originalDate = originalDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticleLink() {
        return articleLink;
    }

    public void setArticleLink(String articleLink) {
        this.articleLink = articleLink;
    }

    public String getGmt() {
        return gmt;
    }

    public void setGmt(String gmt) {
        this.gmt = gmt;
    }

    public String getYoutubeVideoId() {
        return youtubeVideoId;
    }

    public void setYoutubeVideoId(String youtubeVideoId) {
        this.youtubeVideoId = youtubeVideoId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public String getTimeToRead() {
        return timeToRead;
    }

    public void setTimeToRead(String timeToRead) {
        this.timeToRead = timeToRead;
    }

    public List<BriefingMediaBean> getMedia() {
        return media;
    }

    public void setMedia(List<BriefingMediaBean> media) {
        this.media = media;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.description);
        dest.writeString(this.articleId);
        dest.writeString(this.leadText);
        dest.writeString(this.commentCount);
        dest.writeString(this.AUDIO_URL);
        dest.writeString(this.VIDEO_URL);
        dest.writeTypedList(this.IMAGES);
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
        dest.writeString(this.sectionName);
        dest.writeString(this.publishedDate);
        dest.writeString(this.originalDate);
        dest.writeString(this.location);
        dest.writeString(this.title);
        dest.writeString(this.articleLink);
        dest.writeString(this.gmt);
        dest.writeString(this.youtubeVideoId);
        dest.writeString(this.shortDescription);
        dest.writeString(this.videoId);
        dest.writeString(this.articleType);
        dest.writeString(this.timeToRead);
        dest.writeList(this.media);
    }

    protected RecoBean(Parcel in) {
        this.description = in.readString();
        this.articleId = in.readString();
        this.leadText = in.readString();
        this.commentCount = in.readString();
        this.AUDIO_URL = in.readString();
        this.VIDEO_URL = in.readString();
        this.IMAGES = in.createTypedArrayList(MeBean.CREATOR);
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
        this.sectionName = in.readString();
        this.publishedDate = in.readString();
        this.originalDate = in.readString();
        this.location = in.readString();
        this.title = in.readString();
        this.articleLink = in.readString();
        this.gmt = in.readString();
        this.youtubeVideoId = in.readString();
        this.shortDescription = in.readString();
        this.videoId = in.readString();
        this.articleType = in.readString();
        this.timeToRead = in.readString();
        this.media = new ArrayList<BriefingMediaBean>();
        in.readList(this.media, BriefingMediaBean.class.getClassLoader());
    }

    public static final Creator<RecoBean> CREATOR = new Creator<RecoBean>() {
        @Override
        public RecoBean createFromParcel(Parcel source) {
            return new RecoBean(source);
        }

        @Override
        public RecoBean[] newArray(int size) {
            return new RecoBean[size];
        }
    };
}
