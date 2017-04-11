package com.android.project.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlossalas on 22/9/16.
 */

public class ArticleResponse {
    @SerializedName("data")
    @Expose
    private List<Article> articles = new ArrayList<>();

    /**
     * @return The data
     */
    public List<Article> getArticles() {
        return articles;
    }

    /**
     * @param articles The data
     */
    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
