package com.bliss.yang.daggerapp.app;

import android.app.Application;

import com.bliss.yang.daggerapp.di.component.APPComponent;
import com.bliss.yang.daggerapp.di.component.DaggerAPPComponent;
import com.bliss.yang.daggerapp.di.module.APPModule;

/**
 * @author YangTianFu
 * @date 2019/5/31  11:18
 * @description
 */
public class APP extends Application {
    private APPComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAPPComponent
                .builder()
                .aPPModule(new APPModule(this))
                .build();
    }
    public APPComponent getAppComponent() {
        return appComponent;
    }
}
