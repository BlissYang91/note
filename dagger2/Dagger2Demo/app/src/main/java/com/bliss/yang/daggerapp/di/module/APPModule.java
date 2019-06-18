package com.bliss.yang.daggerapp.di.module;

import android.app.Application;
import android.content.Context;

import com.bliss.yang.daggerapp.db.RemoteManager;
import com.bliss.yang.daggerapp.di.annotation.APPScoped;

import dagger.Module;
import dagger.Provides;

/**
 * @author YangTianFu
 * @date 2019/5/31  11:26
 * @description
 */
@Module
public class APPModule {
    private Application context;

    public APPModule(Application context) {
        this.context = context;
    }
    @Provides
    public Context provideContext(){
        return context;
    }

    @APPScoped
    @Provides
    public RemoteManager provideRemoteManager(){
        return new RemoteManager();
    }
}
