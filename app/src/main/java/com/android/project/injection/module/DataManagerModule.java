package com.android.project.injection.module;

import com.android.project.data.remote.APIService;
import com.android.project.data.remote.RetrofitHelper;
import com.android.project.injection.scope.PerDataManager;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Provide dependencies to the DataManager, mainly Helper classes and Retrofit services.
 */
@Module
public class DataManagerModule {

    public DataManagerModule() {

    }

    @Provides
    @PerDataManager
    APIService provideAPIService() {
        return new RetrofitHelper().newAPIService();
    }

    @Provides
    @PerDataManager
    Scheduler provideSubscribeScheduler() {
        return Schedulers.io();
    }
}