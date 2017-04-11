package com.android.project.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.android.project.application.Application;
import com.android.project.model.Article;
import com.android.project.view.ArticleAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArticleViewModel extends BaseObservable {

    private Context mContext;
    private List<Article> mArticles = new ArrayList<>();
    private Article mArticle;
    private Boolean mOrderAscendent;

    public ArticleViewModel(Context context) {
        this.mContext = context;
    }

    @BindingAdapter({"app:showImagesInList", "app:orderAscendent"})
    public static void showImagesInList(final RecyclerView recyclerView, List<Article> articles, final Boolean orderAscendent) {
        if (articles == null || articles.isEmpty()) {
            return;
        }

        if (recyclerView.getAdapter() == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            ArticleAdapter adapter = new ArticleAdapter(recyclerView.getContext(), articles);
            recyclerView.setAdapter(adapter);
        } else {

            if (orderAscendent != null) {
                Collections.sort(articles, new Comparator<Article>() {
                    @Override
                    public int compare(Article s1, Article s2) {
                        if (orderAscendent) {
                            return s1.getTitle().toLowerCase().compareTo(s2.getTitle().toLowerCase());
                        } else {
                            return s2.getTitle().toLowerCase().compareTo(s1.getTitle().toLowerCase());
                        }
                    }
                });
            }

            ArticleAdapter adapter = (ArticleAdapter) recyclerView.getAdapter();
            adapter.setArticles(articles);

            if (recyclerView.getLayoutManager() != null) {
                recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, 0);
            }
        }
    }

    public List<Article> getArticles() {
        return mArticles;
    }

    public void setArticles(List<Article> mArticles) {
        this.mArticles = mArticles;
        notifyChange();
    }

    public void setArticle(Article mArticle) {
        this.mArticle = mArticle;
    }

    public String getImageUrl() {
        return mArticle != null && mArticle.getLink() != null ? mArticle.getLink() : null;
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
        return mArticle != null && mArticle.getTitle() != null ? mArticle.getTitle() : "";
    }

    public String getDescription() {
        return mArticle != null && mArticle.getDescription() != null ? mArticle.getDescription() : "";
    }

    public void orderList() {
        if (mOrderAscendent == null) {
            mOrderAscendent = true;
        } else {
            mOrderAscendent = !mOrderAscendent;
        }
        notifyChange();
    }

    public Boolean getOrderAscendent() {
        return mOrderAscendent;
    }
}
