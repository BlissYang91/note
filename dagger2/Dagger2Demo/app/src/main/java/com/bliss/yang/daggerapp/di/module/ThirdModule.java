package com.bliss.yang.daggerapp.di.module;

import com.bliss.yang.daggerapp.di.annotation.Age;
import com.bliss.yang.daggerapp.di.annotation.Height;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * @author YangTianFu
 * @date 2019/5/31  10:15
 * @description
 */
@Module
public class ThirdModule {
    /*通过 name 区分返回值相同的不同属性值*/
    @Provides
    @Named("name")
    public String provideName() {
        return "BlissYang";
    }

    @Provides
    @Named("address")
    public String provideAddress() {
        return "北京";
    }

    /*通过 自定义注解 区分返回值相同的不同属性值*/
    @Age
    @Provides
    public int provideAge() {
        return 24;
    }

    @Height
    @Provides
    public int provideHeight() {
        return 175;
    }


    @Provides
    public OkHttpClient provideOkHttpClient() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        return client;
    }
}
