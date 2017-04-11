package com.android.project.application;

import android.support.multidex.MultiDexApplication;

import com.android.project.injection.component.ApplicationComponent;
import com.android.project.injection.component.DaggerApplicationComponent;
import com.android.project.injection.module.ApplicationModule;

/**
 * Created by carlossalas on 22/9/16.
 */

public class Application extends MultiDexApplication {

    private static Application context;
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        context = (Application) getApplicationContext();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

    }

    public static Application tdApplication() {
        return context;
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }
}
