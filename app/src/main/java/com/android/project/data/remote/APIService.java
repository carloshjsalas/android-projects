package com.android.project.data.remote;

import com.android.project.model.GetPhotosResponse;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface APIService {

    String FLICKR_PARAM_METHOD = "flickr.photos.search";
    String FLICKR_PARAM_API_KEY = "ece2e8dfc3b0dcb883577e3d6bad6290";
    String FLICKR_PARAM_FORMAT = "json";
    Integer FLICKR_PARAM_NOJSCALLBACK = 1;
    String FLICKR_PARAM_EXTRAS = "url_s";

    String BASE_URL = "https://api.flickr.com";

    @GET("/services/rest/")
    Observable<GetPhotosResponse> getFlickrImagesUrl(
            @Query("method") String method,
            @Query("api_key") String apiKey,
            @Query("format") String format,
            @Query("nojsoncallback") Integer noJsonCallback,
            @Query("extras") String extras,
            @Query("text") String searchQuery
    );
}
