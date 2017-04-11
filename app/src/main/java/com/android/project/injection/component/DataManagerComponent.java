package com.android.project.injection.component;


import com.android.project.data.DataManager;
import com.android.project.injection.module.DataManagerModule;
import com.android.project.injection.scope.PerDataManager;

import dagger.Component;

@PerDataManager
@Component(dependencies = ApplicationComponent.class, modules = DataManagerModule.class)
public interface DataManagerComponent {
    void inject(DataManager dataManager);
}