package com.android.project.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.project.R;
import com.android.project.databinding.ItemArticleBinding;
import com.android.project.model.Article;
import com.android.project.viewmodel.ArticleViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlossalas on 10/6/16.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.BindingHolder> {

    private final Context mContext;
    private List<Article> mArticles = new ArrayList<>();
    private ItemArticleBinding mItemBinding;
    private ArticleViewModel mViewModel;

    public ArticleAdapter(Context context, List<Article> articles) {
        this.mContext = context;
        this.mArticles = articles;
    }

    @Override
    public ArticleAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemArticleBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_article,
                parent,
                false
        );
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        mItemBinding = holder.binding;
        mViewModel = new ArticleViewModel(mContext);
        mViewModel.setArticle(mArticles.get(position));
        mItemBinding.setViewModel(mViewModel);
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    @Override
    public long getItemId(int position) {
        return mArticles.get(position).getId().hashCode();
    }

    public void setArticles(List<Article> articles) {
        this.mArticles = articles;
        notifyDataSetChanged();
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ItemArticleBinding binding;

        public BindingHolder(ItemArticleBinding binding) {
            super(binding.containerImage);
            this.binding = binding;
        }
    }
}
