package com.bliss.yang.daggerapp.di.component;

import com.bliss.yang.daggerapp.di.annotation.ActivityScoped;
import com.bliss.yang.daggerapp.di.module.SecondModule;
import com.bliss.yang.daggerapp.second.SecondActivity;

import dagger.Subcomponent;

/**
 * @author YangTianFu
 * @date 2019/5/31  11:25
 * @description
 */
@ActivityScoped
@Subcomponent(modules = SecondModule.class)
public interface SecondCompoent {
    void inject(SecondActivity activity);
}
