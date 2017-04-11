package com.android.project.data.remote;

import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class RetrofitHelper {

    public APIService newAPIService() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(APIService.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(new GsonBuilder().create()))
                .build();
        return restAdapter.create(APIService.class);
    }
}
