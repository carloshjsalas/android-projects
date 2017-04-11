package com.android.project.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoMeta {

    @SerializedName("photo")
    @Expose
    private List<GalleryItem> mGalleryItems;

    @SerializedName("page")
    @Expose
    private int mPage;

    @SerializedName("pages")
    @Expose
    private int mPages;

    @SerializedName("total")
    @Expose
    private int mTotal;

    @SerializedName("perpage")
    @Expose
    private int mPerPage;

    public List<GalleryItem> getGalleryItems() {
        return mGalleryItems;
    }

    public void setGalleryItems(List<GalleryItem> galleryItems) {
        mGalleryItems = galleryItems;
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        mPage = page;
    }

    public int getPages() {
        return mPages;
    }

    public void setPages(int pages) {
        mPages = pages;
    }

    public int getTotal() {
        return mTotal;
    }

    public void setTotal(int total) {
        mTotal = total;
    }

    public int getPerPage() {
        return mPerPage;
    }

    public void setPerPage(int perPage) {
        mPerPage = perPage;
    }
}
