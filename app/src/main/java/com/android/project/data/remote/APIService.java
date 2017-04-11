package com.android.project.data.remote;

import com.android.project.model.ArticleResponse;

import retrofit.http.GET;
import retrofit.http.Headers;
import rx.Observable;

public interface APIService {

    String BASE_URL = "https://api.imgur.com";

    @Headers("Authorization: Client-ID 1588a32330b9360")
    @GET("/3/gallery/r/funny/")
    Observable<ArticleResponse> getImagesUrl();
}
