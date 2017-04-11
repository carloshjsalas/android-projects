package com.android.project.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.project.R;
import com.android.project.databinding.ItemImageBinding;
import com.android.project.model.GalleryItem;
import com.android.project.viewmodel.ImageViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlossalas on 10/6/16.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.BindingHolder> {

    private final Context mContext;
    private List<GalleryItem> mImages = new ArrayList<>();
    private ItemImageBinding mItemBinding;
    private ImageViewModel mViewModel;

    public ImageAdapter(Context context, List<GalleryItem> images) {
        this.mContext = context;
        this.mImages = images;
    }

    @Override
    public ImageAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemImageBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_image,
                parent,
                false
        );
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        mItemBinding = holder.binding;
        mViewModel = new ImageViewModel();
        mViewModel.setImage(mImages.get(position));
        mItemBinding.setViewModel(mViewModel);
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    @Override
    public long getItemId(int position) {
        return mImages.get(position).getId().hashCode();
    }

    public void setArticles(List<GalleryItem> articles) {
        this.mImages = articles;
        notifyDataSetChanged();
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ItemImageBinding binding;

        public BindingHolder(ItemImageBinding binding) {
            super(binding.containerImage);
            this.binding = binding;
        }
    }
}
