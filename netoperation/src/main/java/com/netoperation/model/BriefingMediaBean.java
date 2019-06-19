package com.netoperation.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BriefingMediaBean implements Parcelable {
    /**
     * image : https://alphath.thehindu.co.in/todays-paper/tp-features/tp-weekend/rpk5h1/article10397555.ece/ALTERNATES/FREE_660/newMPTemplateGOA5HF84J4jpgjpg
     * caption : Caption for picture
     */

    private String image;
    private String caption;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.image);
        dest.writeString(this.caption);
    }

    public BriefingMediaBean() {
    }

    protected BriefingMediaBean(Parcel in) {
        this.image = in.readString();
        this.caption = in.readString();
    }

    public static final Parcelable.Creator<BriefingMediaBean> CREATOR = new Parcelable.Creator<BriefingMediaBean>() {
        @Override
        public BriefingMediaBean createFromParcel(Parcel source) {
            return new BriefingMediaBean(source);
        }

        @Override
        public BriefingMediaBean[] newArray(int size) {
            return new BriefingMediaBean[size];
        }
    };
}
