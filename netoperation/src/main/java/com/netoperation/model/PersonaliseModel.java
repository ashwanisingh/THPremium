package com.netoperation.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PersonaliseModel implements Parcelable {
    private String pId;
    private String name;
    private String imageUrl;
    private boolean isSelected;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.pId);
        dest.writeString(this.name);
        dest.writeString(this.imageUrl);
        dest.writeValue(this.isSelected);
    }

    public PersonaliseModel() {
    }

    protected PersonaliseModel(Parcel in) {
        this.pId = in.readString();
        this.name = in.readString();
        this.imageUrl = in.readString();
        this.isSelected = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<PersonaliseModel> CREATOR = new Parcelable.Creator<PersonaliseModel>() {
        @Override
        public PersonaliseModel createFromParcel(Parcel source) {
            return new PersonaliseModel(source);
        }

        @Override
        public PersonaliseModel[] newArray(int size) {
            return new PersonaliseModel[size];
        }
    };
}
