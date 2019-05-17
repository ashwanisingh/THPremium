package com.netoperation.model;

import java.util.List;

public class RecoBean {
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
}
