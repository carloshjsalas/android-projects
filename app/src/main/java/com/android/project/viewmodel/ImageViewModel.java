package com.android.project.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.android.project.application.Application;
import com.android.project.model.GalleryItem;
import com.android.project.view.ImageAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

public class ImageViewModel extends BaseObservable {

    private List<GalleryItem> mImages = new ArrayList<>();
    private GalleryItem mImage;

    public ImageViewModel() {
    }

    @BindingAdapter("app:showImagesInList")
    public static void showImagesInList(final RecyclerView recyclerView, List<GalleryItem> images) {
        if (images == null) {
            recyclerView.setAdapter(null);
            return;
        }

        if (recyclerView.getAdapter() == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            ImageAdapter adapter = new ImageAdapter(recyclerView.getContext(), images);
            recyclerView.setAdapter(adapter);
        } else {

            ImageAdapter adapter = (ImageAdapter) recyclerView.getAdapter();
            adapter.setArticles(images);

            if (recyclerView.getLayoutManager() != null) {
                recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, 0);
            }
        }
    }

    public List<GalleryItem> getImages() {
        return mImages;
    }

    public void setImages(List<GalleryItem> images) {
        this.mImages = images;
        notifyChange();
    }

    public void setImage(GalleryItem image) {
        this.mImage = image;
        notifyChange();
    }

    public String getImageUrl() {
        return mImage != null && mImage.getUrlS() != null ? mImage.getUrlS() : null;
    }

    @BindingAdapter("app:loadImageUrl")
    public static void loadImageUrl(final ImageView view, String url) {
        if (url != null) {
            Glide.with(Application.tdApplication())
                    .load(url)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(false)
                    .into(view);
        }
    }

    public String getTitle() {
        return mImage != null && mImage.getTitle() != null ? mImage.getTitle() : "";
    }

    public Boolean getHasImages() {
        return mImages != null && !mImages.isEmpty();
    }
}
