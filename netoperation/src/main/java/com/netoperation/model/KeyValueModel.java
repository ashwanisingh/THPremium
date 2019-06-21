package com.netoperation.model;

import android.os.Parcel;
import android.os.Parcelable;

public class KeyValueModel implements Parcelable {


    /**
     * name : Afghanistan
     * code : AF
     */

    private String name;
    private String code;
    private String State;
    private String Code;

    public String getState() {
        return State;
    }

    public String getStateCode() {
        return Code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.code);
        dest.writeString(this.State);
        dest.writeString(this.Code);
    }

    public KeyValueModel() {
    }

    protected KeyValueModel(Parcel in) {
        this.name = in.readString();
        this.code = in.readString();
        this.State = in.readString();
        this.Code = in.readString();
    }

    public static final Creator<KeyValueModel> CREATOR = new Creator<KeyValueModel>() {
        @Override
        public KeyValueModel createFromParcel(Parcel source) {
            return new KeyValueModel(source);
        }

        @Override
        public KeyValueModel[] newArray(int size) {
            return new KeyValueModel[size];
        }
    };
}
