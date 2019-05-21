package com.netoperation.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MeBean implements Parcelable {

    @SerializedName("im")
    private String im;

    @SerializedName("im_v2")
    private String im_v2;

    @SerializedName("ca")
    private String ca;

    public String getIm() {
        return im;
    }

    private String listingImgUrl;
    private String bigImgUrl;

    public String getListingImgUrl() {
        return listingImgUrl;
    }

    public void setListingImgUrl(String listingImgUrl) {
        this.listingImgUrl = listingImgUrl;
    }

    public String getBigImgUrl() {
        return bigImgUrl;
    }

    public void setBigImgUrl(String bigImgUrl) {
        this.bigImgUrl = bigImgUrl;
    }

    public void setIm(String im) {
        this.im = im;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getIm_v2() {
        return im_v2;
    }

    public void setIm_v2(String im_v2) {
        this.im_v2 = im_v2;
    }

    public MeBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.im);
        dest.writeString(this.im_v2);
        dest.writeString(this.ca);
        dest.writeString(this.listingImgUrl);
        dest.writeString(this.bigImgUrl);
    }

    protected MeBean(Parcel in) {
        this.im = in.readString();
        this.im_v2 = in.readString();
        this.ca = in.readString();
        this.listingImgUrl = in.readString();
        this.bigImgUrl = in.readString();
    }

    public static final Creator<MeBean> CREATOR = new Creator<MeBean>() {
        @Override
        public MeBean createFromParcel(Parcel source) {
            return new MeBean(source);
        }

        @Override
        public MeBean[] newArray(int size) {
            return new MeBean[size];
        }
    };
}
