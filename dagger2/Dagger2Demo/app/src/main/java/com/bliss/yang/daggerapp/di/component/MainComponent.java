package com.bliss.yang.daggerapp.di.component;

import com.bliss.yang.daggerapp.di.annotation.ActivityScoped;
import com.bliss.yang.daggerapp.di.module.MainModule;
import com.bliss.yang.daggerapp.main.MainActivity;
import com.bliss.yang.daggerapp.db.DBManager;

import dagger.Component;
import dagger.Subcomponent;

/**
 * @author YangTianFu
 * @date 2019/5/31  9:51
 * @description
 */
@ActivityScoped
@Subcomponent(modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
