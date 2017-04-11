package com.android.project.injection.component;

import android.app.Application;

import com.android.project.data.DataManager;
import com.android.project.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Application application();

    DataManager dataManager();
}