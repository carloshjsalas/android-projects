package com.android.project.data;

import com.android.project.application.Application;
import com.android.project.data.remote.APIService;
import com.android.project.injection.component.DaggerDataManagerComponent;
import com.android.project.injection.module.DataManagerModule;
import com.android.project.model.ArticleResponse;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;

public class DataManager {

    @Inject
    protected APIService mAPIService;
    @Inject
    protected Scheduler mSubscribeScheduler;

    public DataManager() {
        injectDependencies();
    }

    public DataManager(APIService watchTowerService, Scheduler subscribeScheduler) {
        mAPIService = watchTowerService;
        mSubscribeScheduler = subscribeScheduler;
    }

    protected void injectDependencies() {
        DaggerDataManagerComponent.builder()
                .applicationComponent(Application.tdApplication().getComponent())
                .dataManagerModule(new DataManagerModule())
                .build()
                .inject(this);
    }

    public Scheduler getScheduler() {
        return mSubscribeScheduler;
    }

    public Observable<ArticleResponse> getImagesUrl() {
        return mAPIService.getImagesUrl();
    }
}


