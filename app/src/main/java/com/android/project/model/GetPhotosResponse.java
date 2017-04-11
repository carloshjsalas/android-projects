package com.android.project.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPhotosResponse {

    @SerializedName("photos")
    @Expose
    private PhotoMeta mPhotoMeta;

    @SerializedName("stat")
    @Expose
    private String mStat;

    public PhotoMeta getPhotoMeta() {
        return mPhotoMeta;
    }

    public void setPhotoMeta(PhotoMeta photoMeta) {
        mPhotoMeta = photoMeta;
    }

    public String getStat() {
        return mStat;
    }

    public void setStat(String stat) {
        mStat = stat;
    }
}
